package ru.mera.spring.form;

import java.util.ArrayList;
import java.util.List;

public class QuestionForm {

    private String textOfQuestion;
    private int difficultyLevel;
    private int questionId;
    private List<AnswerModel> answers = new ArrayList<>();
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

    public List<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerModel> answers) {
        this.answers = answers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<DifficultyLevelModel> getDifficultyLevelModels() {
        return difficultyLevelModels;
    }

    public void setDifficultyLevelModels(List<DifficultyLevelModel> difficultyLevelModels) {
        this.difficultyLevelModels = difficultyLevelModels;
    }
}
