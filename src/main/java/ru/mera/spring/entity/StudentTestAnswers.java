package ru.mera.spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "student_test_answers")
public class StudentTestAnswers {

    private int id;
    private int studentTestQuestionId;
    private int answerId;
    private StudentTestQuestion studentTestQuestion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "student_test_question_id")
    public int getStudentTestQuestionId() {
        return studentTestQuestionId;
    }

    public void setStudentTestQuestionId(int studentTestQuestionId) {
        this.studentTestQuestionId = studentTestQuestionId;
    }

    @Column(name = "answer_id")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @ManyToOne
    @JoinColumn(name = "student_test_question_id", insertable = false, updatable = false)
    public StudentTestQuestion getStudentTestQuestion() {
        return studentTestQuestion;
    }

    public void setStudentTestQuestion(StudentTestQuestion studentTestQuestion) {
        this.studentTestQuestion = studentTestQuestion;
    }

    @Override
    public String toString() {
        return "StudentTestAnswers{" +
                "id=" + id +
                ", studentTestQuestionId=" + studentTestQuestionId +
                ", answerId=" + answerId +
                '}';
    }
}
