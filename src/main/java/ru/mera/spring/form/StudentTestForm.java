package ru.mera.spring.form;

import ru.mera.spring.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentTestForm {

    private String textOfQuestion;
    private int difficultyLevel;
    private int questionId;
    private int numberOfQuestion;
    private int countOfCorrect;
    private int studentTestId;
    private int countOfQuestions;
    private int result;
    private Student student;
    private List<AnswerTestModel> answers = new ArrayList<>();
    private List<DifficultyLevelModel> difficultyLevelModels = new ArrayList<>();

    public String getTextOfQuestion() { return textOfQuestion; }

    public void setTextOfQuestion(String textOfQuestion) {
        this.textOfQuestion = textOfQuestion;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<AnswerTestModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerTestModel> answers) {
        this.answers = answers;
    }

    public int getStudentTestId() {
        return studentTestId;
    }

    public void setStudentTestId(int studentTestId) {
        this.studentTestId = studentTestId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(int countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public int getCountOfCorrect() {
        return countOfCorrect;
    }

    public void setCountOfCorrect(int countOfCorrect) {
        this.countOfCorrect = countOfCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }


    public List<DifficultyLevelModel> getDifficultyLevelModels() {
        return difficultyLevelModels;
    }

    public void setDifficultyLevelModels(List<DifficultyLevelModel> difficultyLevelModels) {
        this.difficultyLevelModels = difficultyLevelModels;
    }
}
