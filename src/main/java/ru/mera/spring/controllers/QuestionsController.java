package ru.mera.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mera.spring.entity.Answer;
import ru.mera.spring.entity.Question;
import ru.mera.spring.form.AnswerModel;
import ru.mera.spring.form.DifficultyLevelModel;
import ru.mera.spring.form.QuestionForm;
import ru.mera.spring.repository.AnswerRepository;
import ru.mera.spring.repository.DifficultyLevelRepository;
import ru.mera.spring.repository.QuestionRepository;
import ru.mera.spring.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionsController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(path = "/questions", method = RequestMethod.GET)
    public String questions(Model model){
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(question -> questions.add(question));
        model.addAttribute("questions", questions);
        return "questions";
    }

    @RequestMapping(path = "/answers", method = RequestMethod.GET)
    public String answers(@RequestParam int questionId, Model model){

        Question question = questionRepository.findOne(questionId);
        List<Answer> answers = new ArrayList<>();
        answerRepository.findByQuestionId(question.getId()).forEach(answer -> answers.add(answer));
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "answers";
    }

    @RequestMapping(path = "/questions/add", method = RequestMethod.GET)
    public String addQuestionForm(Model model){

        addDifLevels(model);

        QuestionForm questionForm = new QuestionForm();
        questionForm.getAnswers().add(new AnswerModel());
        model.addAttribute("questionForm", questionForm);

        return "addQuestion";
    }

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST, params = "action=addAnswer")
    public String addAnswer(@ModelAttribute("questionForm") QuestionForm questionForm, Model model){

        addDifLevels(model);
        questionForm.getAnswers().add(new AnswerModel());
        return "addQuestion";
    }

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST)
    public String removeAnswer(@ModelAttribute("questionForm") QuestionForm questionForm, Model model,
                               @RequestParam(value = "remove", required = true) String remove){

        int index = Integer.valueOf(remove);
        questionForm.getAnswers().remove(index);
        addDifLevels(model);
        return "addQuestion";
    }

    @RequestMapping(path = "/addQuestion", method = RequestMethod.POST, params = "action=save")
    public String saveQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, Model model){

        Question question = new Question();
        question.setTextOfQuestion(questionForm.getTextOfQuestion());
        question.setDifficultyLevelId(questionForm.getDifficultyLevel());

        List<Answer> answers = new ArrayList<>();
        questionForm.getAnswers().forEach(answerModel -> {
            Answer answer = new Answer();
            answer.setTextOfAnswer(answerModel.getTextOfAnswer());
            answer.setCorrect(answerModel.isCorrect());
            answers.add(answer);
        });

        try {
            questionService.createQuestion(question, answers);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            addDifLevels(model);
            return "addQuestion";
        }

        return "redirect:/questions";
    }

    @RequestMapping(path = "/question/update", method = RequestMethod.GET)
    public String updateQuestionForm(@RequestParam int questionId, Model model){

        Question question = questionRepository.findOne(questionId);
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        addDifLevels(model);

        QuestionForm questionForm = new QuestionForm();
        questionForm.setTextOfQuestion(question.getTextOfQuestion());
        questionForm.setDifficultyLevel(question.getDifficultyLevelId());
        questionForm.setQuestionId(questionId);

        answers.forEach(answer -> questionForm.getAnswers().add(buildAnswerModel(answer)));
        model.addAttribute("questionForm", questionForm);

        return "updateQuestion";
    }

    @RequestMapping(path = "/updateQuestion", method = RequestMethod.POST, params = "action=save")
    public String updateQuestion(@ModelAttribute("questionForm") QuestionForm questionForm, Model model){

        Question question = new Question();
        question.setTextOfQuestion(questionForm.getTextOfQuestion());
        question.setDifficultyLevelId(questionForm.getDifficultyLevel());
        question.setId(questionForm.getQuestionId());

        List<Answer> answers = new ArrayList<>();
        questionForm.getAnswers().forEach(answerModel -> {
            Answer answer = new Answer();
            answer.setTextOfAnswer(answerModel.getTextOfAnswer());
            answer.setCorrect(answerModel.isCorrect());
            answer.setQuestionId(questionForm.getQuestionId());
            answers.add(answer);
        });

        try {
            questionService.updateQuestion(question);
            questionService.updateAnswers(question, answers);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            addDifLevels(model);
            return "updateQuestion";
        }

        return "redirect:/answers?questionId=" + questionForm.getQuestionId();
    }

    @RequestMapping(path = "/updateQuestion", method = RequestMethod.POST, params = "action=addAnswer")
    public String addAnswerWhenUpdate(@ModelAttribute("questionForm") QuestionForm questionForm, Model model){

        addDifLevels(model);
        questionForm.getAnswers().add(new AnswerModel());
        return "updateQuestion";
    }

    @RequestMapping(path = "/updateQuestion", method = RequestMethod.POST)
    public String removeAnswerWhenUpdate(@ModelAttribute("questionForm") QuestionForm questionForm, Model model,
                               @RequestParam(value = "remove", required = true) String remove){

        int index = Integer.valueOf(remove);
        questionForm.getAnswers().remove(index);
        addDifLevels(model);
        return "updateQuestion";
    }



    private AnswerModel buildAnswerModel(Answer answer){

        AnswerModel answerModel = new AnswerModel();
        answerModel.setTextOfAnswer(answer.getTextOfAnswer());
        answerModel.setCorrect(answer.isCorrect());

        return answerModel;
    }

    private Model addDifLevels(Model model){

        List<DifficultyLevelModel> difficultyLevelModels = new ArrayList<>();
        difficultyLevelRepository.findAll().forEach(difficultyLevel -> {
            DifficultyLevelModel difficultyLevelModel = new DifficultyLevelModel();
            difficultyLevelModel.setId(difficultyLevel.getId());
            difficultyLevelModel.setName(difficultyLevel.getName());
            difficultyLevelModels.add(difficultyLevelModel);
        });

        model.addAttribute("difficultyLevels", difficultyLevelModels);

        return model;
    }

}
