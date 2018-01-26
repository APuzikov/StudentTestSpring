package ru.mera.spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_test")
public class StudentTest {

    private int id;
    private int studentId;
    private Timestamp testDate;
    private Timestamp endDate;
    private Timestamp createDate;
    private int testResult;
    private Student student;
    private Set<StudentTestQuestion> studentTestQuestions = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "student_id", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Column(name = "test_date")
    public Timestamp getTestDate() {
        return testDate;
    }

    public void setTestDate(Timestamp testDate) {
        this.testDate = testDate;
    }

    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Column(name = "test_result")
    public int getTestResult() {
        return testResult;
    }

    public void setTestResult(int testResult) {
        this.testResult = testResult;
    }

    @Column(name = "create_date", nullable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @OneToMany(mappedBy = "studentTest")
    public Set<StudentTestQuestion> getStudentTestQuestions() {
        return studentTestQuestions;
    }

    public void setStudentTestQuestions(Set<StudentTestQuestion> studentTestQuestions) {
        this.studentTestQuestions = studentTestQuestions;
    }

    @Override
    public String toString() {
        return "StudentTest{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", testDate=" + testDate +
                ", endDate=" + endDate +
                ", testResult=" + testResult +
                '}';
    }
}
