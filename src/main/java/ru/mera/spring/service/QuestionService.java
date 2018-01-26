package ru.mera.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mera.spring.entity.Answer;
import ru.mera.spring.entity.DifficultyLevel;
import ru.mera.spring.entity.Question;
import ru.mera.spring.repository.AnswerRepository;
import ru.mera.spring.repository.DifficultyLevelRepository;
import ru.mera.spring.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private DifficultyLevelRepository difficultyLevelRepository;

    public void createQuestion(Question question, List<Answer> answers){

        Assert.notNull(question, "Question can't be null!");
        Assert.hasText(question.getTextOfQuestion(), "Text is empty!");

        DifficultyLevel difficultyLevel = difficultyLevelRepository.findOne(question.getDifficultyLevelId());
        Assert.notNull(difficultyLevel, "Difficulty level not found!");

        Assert.notNull(answers, "Answers can't be null!");
        Assert.notEmpty(answers, "Answers can't be empty!");

        boolean correctAnswer = answers.stream().allMatch(answer -> answer.getTextOfAnswer() != null && answer.getTextOfAnswer().length() > 0);
        Assert.isTrue(correctAnswer, "Text of answer is empty!");

        boolean isCorrect = false;
        for (Answer answer : answers){
            isCorrect = answer.isCorrect() || isCorrect;
        }
        Assert.isTrue(isCorrect, "All answers are incorrect!");

        int questionId = questionRepository.save(question).getId();

        answers.forEach(answer -> answer.setQuestionId(questionId));
        answers.forEach(answer -> answerRepository.save(answer));

    }

    public void updateQuestion(Question question){

        Assert.notNull(question, "Question can't be null!");
        Assert.hasText(question.getTextOfQuestion(), "Text is empty!");
        Assert.notNull(difficultyLevelRepository.findOne(question.getDifficultyLevelId()), "Difficulty level not found!");

        Assert.notNull(questionRepository.findOne(question.getId()), "Question not found!");

        questionRepository.save(question);
    }

    public void updateAnswers(Question question, List<Answer> answers){

        Assert.notEmpty(answers, "Answers can't be empty!");

        boolean correctAnswer = answers.stream().allMatch(answer -> answer.getTextOfAnswer() != null && answer.getTextOfAnswer().length() > 0);
        Assert.isTrue(correctAnswer, "Text of answer is empty!");

        boolean isCorrect = false;
        for (Answer answer : answers){
            isCorrect = answer.isCorrect() || isCorrect;
        }
        Assert.isTrue(isCorrect, "All answers are incorrect!");

        boolean matchAnswers = answers.stream().allMatch(answer -> answer.getQuestionId() == question.getId());
        Assert.isTrue(matchAnswers, "Answers are not the question!");

        List<Answer> oldAnswers = answerRepository.findByQuestionId(question.getId());
        oldAnswers.forEach(answer -> answerRepository.delete(answer));
        answers.forEach(answer -> answerRepository.save(answer));
    }

    public void addAnswers(Question question, List<Answer> answers){

        Assert.notEmpty(answers, "Answers can't be empty!");

        boolean correctAnswer = answers.stream().allMatch(answer -> answer.getTextOfAnswer() != null && answer.getTextOfAnswer().length() > 0);
        Assert.isTrue(correctAnswer, "Text of answer is empty!");

        boolean isCorrect = false;
        for (Answer answer : answers){
            isCorrect = answer.isCorrect() || isCorrect;
        }
        Assert.isTrue(isCorrect, "All answers are incorrect!");

        boolean matchAnswers = answers.stream().allMatch(answer -> answer.getQuestionId() == question.getId());
        Assert.isTrue(matchAnswers, "Answers are not the question ");

        answers.forEach(answer -> answerRepository.save(answer));

    }
}
