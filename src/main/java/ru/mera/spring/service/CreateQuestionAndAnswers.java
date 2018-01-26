package ru.mera.spring.service;

import ru.mera.spring.entity.Answer;
import ru.mera.spring.entity.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CreateQuestionAndAnswers {

    private Question question = null;
    private List<Answer> answers = null;

    public Question getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Question setQuestion(){

        StringBuilder textOfQuestion = new StringBuilder();
        int difficultyLevel;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Наберите текст вопроса. После окончания наберите 'end'.");
            while (true){
                String input = reader.readLine();
                if (input.equals("")) return null;
                if (input.equals("end")) break;
                else textOfQuestion.append(input).append("\n");
            }

            while (true){
                System.out.println("Введите уровень сложности вопроса от 1 до 3.");
                String input = reader.readLine();
                if (input.length() > 1 || input.equals("")) System.out.println("Неверный ввод");
                else {
                    difficultyLevel = Integer.parseInt(input);
                    if (difficultyLevel >= 1 && difficultyLevel  <= 3) break;
                    System.out.println("Неверный ввод.");
                }
            }

            Question question =new Question();
            question.setTextOfQuestion(textOfQuestion.toString());
            question.setDifficultyLevelId(difficultyLevel);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static void main(String[] args) {
        CreateQuestionAndAnswers createQuestionAndAnswers = new CreateQuestionAndAnswers();
        createQuestionAndAnswers.setQuestion();

    }
}
