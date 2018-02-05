package ru.mera.spring.form;

public class AnswerModel {

    private String textOfAnswer;
    private boolean correct;
 
    public String getTextOfAnswer() { return textOfAnswer; }

    public void setTextOfAnswer(String textOfAnswer) {
        this.textOfAnswer = textOfAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
