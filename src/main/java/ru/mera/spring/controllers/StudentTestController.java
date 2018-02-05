package ru.mera.spring.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mera.spring.entity.*;
import ru.mera.spring.form.*;
import ru.mera.spring.repository.*;
import ru.mera.spring.service.StudentTestService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StudentTestController {

    @Autowired
    private StudentTestService studentTestService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    private StudentTestQuestionRepository studentTestQuestionRepository;

    @Autowired
    private StudentTestAnswerRepository studentTestAnswerRepository;

    private AnswerTestModel buildAnswerModel(Answer answer) {

        AnswerTestModel answerModel = new AnswerTestModel();
        answerModel.setTextOfAnswer(answer.getTextOfAnswer());
        answerModel.setAnswerId(answer.getId());
        //answerModel.setChecked(answer.isChecked());

        return answerModel;
    }

    private int countOfCorrect(List<Answer> answers){

        int count = 0;
        for (Answer answer : answers){
            if (answer.isCorrect()) count++;
        }

        return count;
    }

    private int resultAnswersOfQuestion(List<AnswerModel> answerModels, List<Answer> answers){

        boolean correct = true;

        for (int i = 0; i < answers.size(); i++){
            correct = correct && answerModels.get(i).isCorrect() && answers.get(i).isCorrect();
        }

        return correct ? 1 : 0;
    }

    @RequestMapping(path = "/studentTests", method = RequestMethod.GET)
    public String startTest(@RequestParam int studentId , @RequestParam int countOfQuestions,  Model model){

        Student student = studentRepository.findOne(studentId);
        int studentTestId;

        try {
            studentTestId = studentTestService.createStudentTest(student, countOfQuestions);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "studentTests";
        }


        return "redirect:/testing?studentTestId=" + studentTestId +
                "&numberOfQuestion=1" +
                "&countOfQuestions=" + countOfQuestions +
                "&result=0";
    }

    @RequestMapping(path = "/testing", method = RequestMethod.GET)
    public String testing(@RequestParam(required = false) int studentTestId,
                          @RequestParam(required = false) int numberOfQuestion,
                          @RequestParam(required = false) int countOfQuestions, Model model){

        StudentTest studentTest = studentTestRepository.findOne(studentTestId);
        Student student = studentRepository.findOne(studentTest.getStudentId());
        List<StudentTestQuestion> studentTestQuestions = studentTestQuestionRepository.findByStudentTestId(studentTestId);

        Question question = questionRepository.findOne(studentTestQuestions.get(numberOfQuestion - 1).getQuestionId());
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        StudentTestForm studentTestForm = new StudentTestForm();
        studentTestForm.setQuestionId(question.getId());
        studentTestForm.setNumberOfQuestion(numberOfQuestion);
        studentTestForm.setTextOfQuestion(question.getTextOfQuestion());
        answers.forEach(answer -> studentTestForm.getAnswers().add(buildAnswerModel(answer)));
        studentTestForm.setCountOfCorrect(countOfCorrect(answers));
        studentTestForm.setStudentTestId(studentTestId);
        studentTestForm.setCountOfQuestions(countOfQuestions);
        studentTestForm.setStudent(student);

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        studentTest.setTestDate(timestamp);
        studentTest.setId(studentTestId);
        studentTestService.updateStudentTest(studentTest);

        //model.addAttribute("student", student);
        model.addAttribute("studentTestForm", studentTestForm);

        return "testing";
    }

    @RequestMapping(path = "/testing", method = RequestMethod.POST, params = "action=next")
    public String checkAnswers(@ModelAttribute("questionForm") StudentTestForm studentTestForm){

        studentTestForm.setNumberOfQuestion(studentTestForm.getNumberOfQuestion() + 1);

        List<Integer> answerIds = new ArrayList<>();
        studentTestForm.getAnswers().stream().filter(answerTestModel -> answerTestModel.isChecked()).
                forEach(answerTestModel -> answerIds.add(answerTestModel.getAnswerId()));

        StudentTestQuestion studentTestQuestion = studentTestQuestionRepository.
                findByStudentTestIdAndQuestionId(studentTestForm.getStudentTestId(), studentTestForm.getQuestionId());

        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);

        return "redirect:/testing?studentTestId=" + studentTestForm.getStudentTestId() +
                "&numberOfQuestion=" + studentTestForm.getNumberOfQuestion() +
                "&countOfQuestions=" + studentTestForm.getCountOfQuestions();
    }

    @RequestMapping(path = "/testing", method = RequestMethod.POST, params = "action=end")
    public String endTest(@ModelAttribute("questionForm") StudentTestForm studentTestForm, Model model){


        //save last question  ---------------------
        List<Integer> answerIds = new ArrayList<>();
        studentTestForm.getAnswers().stream().filter(answerTestModel -> answerTestModel.isChecked()).
                forEach(answerTestModel -> answerIds.add(answerTestModel.getAnswerId()));
        StudentTestQuestion studentTestQuestion = studentTestQuestionRepository.
                findByStudentTestIdAndQuestionId(studentTestForm.getStudentTestId(), studentTestForm.getQuestionId());
        studentTestService.createStudentTestAnswers(answerIds, studentTestQuestion);
        //-----------------------------------------

        int studentTestId = studentTestForm.getStudentTestId();
        StudentTest studentTest = studentTestRepository.findOne(studentTestId);

        int result = calculateResult( studentTestForm.getStudentTestId(), studentTestForm.getCountOfQuestions());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        studentTest.setEndDate(timestamp);
        studentTest.setId(studentTestId);
        studentTest.setTestResult(result);
        studentTestService.updateStudentTest(studentTest);

        Student student = studentTestForm.getStudent();
        List<StudentTest> studentTests = studentTestRepository.findByStudentId(student.getId());
        model.addAttribute("student", student);
        model.addAttribute("studentTests", studentTests);

        return "studentTests";
//        return "redirect:/studentTests?studentId=" + studentTestForm.getStudent().getId()+
//                "&countOfQuestions=" + studentTestForm.getCountOfQuestions();
    }

    private int calculateResult(int studentTestId, int countOfQuestions){

        int result = 0;

        List<StudentTestQuestion> studentTestQuestions = studentTestQuestionRepository.findByStudentTestId(studentTestId);

        for (StudentTestQuestion studentTestQuestion : studentTestQuestions){

            Question question = questionRepository.findOne(studentTestQuestion.getQuestionId());
            List<Answer> correctAnswers = new ArrayList<>();
            answerRepository.findByQuestionId(question.getId()).stream().filter(answer -> answer.isCorrect()).
                    forEach(answer -> correctAnswers.add(answer));

            List<StudentTestAnswers> studentTestAnswers = studentTestAnswerRepository.
                    findByStudentTestQuestionId(studentTestQuestion.getId());

            List<Answer> receivedAnswers = new ArrayList<>();
            studentTestAnswers.forEach(studentTestAnswer -> receivedAnswers.add(answerRepository.
                    findOne(studentTestAnswer.getAnswerId())));

            if(correctAnswers.size() == receivedAnswers.size()){
                boolean correct = true;
                for (int i = 0; i < correctAnswers.size(); i++){
                    if (correctAnswers.get(i).getId() == receivedAnswers.get(i).getId()){
                        correct = true;
                    } else correct = false;
                }

                if (correct) result += 1;
            }
        }

        return calculatePercent(result, countOfQuestions);
    }

    int calculatePercent(int resultOfTest, int countOfQuestions) {

        return Math.round((float) resultOfTest/countOfQuestions*100);
    }
}
