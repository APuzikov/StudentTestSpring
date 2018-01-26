package ru.mera.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mera.spring.entity.Answer;
import ru.mera.spring.entity.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void createQuestion() throws Exception {

        Question question = new Question();

        question.setTextOfQuestion("text of question" + "\n" + "next text of question");
        question.setDifficultyLevelId(2);

        List<Answer> answers = new ArrayList<>();

        Answer answer1 = new Answer();
        answer1.setTextOfAnswer("text of answer 1");
        answer1.setCorrect(true);
        answers.add(answer1);

        Answer answer2 = new Answer();
        answer2.setTextOfAnswer("text of answer 2");
        answer2.setCorrect(false);
        answers.add(answer2);

        Answer answer3 = new Answer();
        answer3.setTextOfAnswer("text of answer 3");
        answer3.setCorrect(false);
        answers.add(answer3);

        Answer answer4 = new Answer();
        answer4.setTextOfAnswer("text of answer 4");
        answer4.setCorrect(true);
        answers.add(answer4);

        questionService.createQuestion(question, answers);
        assertEquals(question.getId(), answer1.getQuestionId());
    }

    @Test
    public void updateQuestion(){
        Question question = new Question();

        question.setId(15);
        question.setDifficultyLevelId(2);
        question.setTextOfQuestion("text text text text text" + "\n" + "text text text text text" + "\n"
        + "text text text text text" + "\n" + "text text text text text" + "\n" + "text text text text text");

        questionService.updateQuestion(question);
    }

    @Test
    public void updateAnswers(){

        Question question = new Question();

        question.setId(15);
        question.setDifficultyLevelId(2);
        question.setTextOfQuestion("text text text text text" + "\n" + "text text text text text" + "\n"
                + "text text text text text" + "\n" + "text text text text text" + "\n" + "text text text text text");

        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();


        answer.setTextOfAnswer("dfdfsdfdfgdfghg");
        answer1.setTextOfAnswer("dfdfsdfb,,mnnm,");


        answer.setCorrect(true);
        answer1.setCorrect(false);


        answer.setQuestionId(15);
        answer1.setQuestionId(15);

        answers.add(answer);
        answers.add(answer1);

        questionService.updateAnswers(question, answers);
    }

    @Test
    public void addAnswer(){
        Question question = new Question();
        question.setId(10);

        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();

        answer.setTextOfAnswer("--------- text of answer ---------");

        answer.setCorrect(true);

        answer.setQuestionId(10);

        answers.add(answer);

        questionService.addAnswers(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionEmptyText(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();

        questionService.createQuestion(question, answers);

    }

    @Test(expected = Exception.class)
    public void createQuestionNullQuestion(){
        Question question = null;
        List<Answer> answers = new ArrayList<>();
        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionInvalidDifficultyLevel(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(500);
        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionNullAnswers(){
        Question question = new Question();
        List<Answer> answers = null;
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);
        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionEmptyAnswers(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);
        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionEmptyAnswersText(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();

        answer.setTextOfAnswer("dfdfsdf");
        answers.add(answer);
        answers.add(answer1);

        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);


        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void createQuestionAllAnswersIncorrect(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();

        answer.setTextOfAnswer("dfdfsdf");
        answer1.setTextOfAnswer("dfdfsdf");
        answer.setCorrect(false);
        answer1.setCorrect(false);

        answers.add(answer);
        answers.add(answer1);

        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);


        questionService.createQuestion(question, answers);
    }

    @Test(expected = Exception.class)
    public void updateQuestionNullQuestion(){
        Question question = null;
        questionService.updateQuestion(question);
    }

    @Test(expected = Exception.class)
    public void updateQuestionEmptyText() {
        Question question = new Question();
        questionService.updateQuestion(question);
    }

    @Test(expected = Exception.class)
    public void updateQuestionInvalidDifficultyLevel() {
        Question question = new Question();
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(500);
        questionService.updateQuestion(question);
    }

    @Test(expected = Exception.class)
    public void updateQuestionInvalidQuestionId() {
        Question question = new Question();
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);
        question.setId(50);
        questionService.updateQuestion(question);

    }

    @Test(expected = Exception.class)
    public void updateAnswersNullAnswers(){
        Question question = new Question();
        List<Answer> answers = null;
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);

        questionService.updateAnswers(question, answers);
    }

    @Test(expected = Exception.class)
    public void updateAnswersEmptyAnswers(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);
        questionService.updateAnswers(question, answers);
    }

    @Test(expected = Exception.class)
    public void updateAnswersEmptyAnswersText(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();

        answer.setTextOfAnswer("dfdfsdf");
        answers.add(answer);
        answers.add(answer1);

        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);


        questionService.updateAnswers(question, answers);
    }

    @Test(expected = Exception.class)
    public void updateAnswersAllAnswersIncorrect(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();

        answer.setTextOfAnswer("dfdfsdf");
        answer1.setTextOfAnswer("dfdfsdf");
        answer.setCorrect(false);
        answer1.setCorrect(false);

        answers.add(answer);
        answers.add(answer1);

        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);


        questionService.updateAnswers(question, answers);
    }

    @Test(expected = Exception.class)
    public void updateAnswersAnswersNotQuestion(){
        Question question = new Question();
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        Answer answer1 = new Answer();

        answer.setTextOfAnswer("dfdfsdf");
        answer1.setTextOfAnswer("dfdfsdf");
        answer.setCorrect(false);
        answer1.setCorrect(true);
        answer.setQuestionId(15);
        answer1.setQuestionId(15);

        answers.add(answer);
        answers.add(answer1);

        question.setTextOfQuestion("sdfsdgfsdgfsdf");
        question.setDifficultyLevelId(2);
        question.setId(14);

        questionService.updateAnswers(question, answers);
    }

}