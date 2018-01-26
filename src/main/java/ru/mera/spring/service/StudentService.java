package ru.mera.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.spring.entity.Student;
import ru.mera.spring.repository.StudentRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(Student student){

        Assert.notNull(student, "Student can't be null!");

        Assert.hasText(student.getName(), "Name is empty!");
        Assert.hasText(student.getEmail(), "Email is empty!");
        Assert.hasText(student.getPassword(), "Password is empty!");

        Assert.isTrue(checkName(student.getName()), "Wrong name");
        Assert.isTrue(checkEmail(student.getEmail()), "Wrong email");
        Assert.isTrue(checkPassword(student.getPassword()), "Wrong password");

        studentRepository.save(student);
    }

    public void updateStudent(Student student){

    }

    private boolean checkName(String  input){
        Pattern pattern = Pattern.compile("[a-zA-zа-яА-Я]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean checkEmail(String input) {
        Pattern pattern = Pattern.compile("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean checkPassword(String password){
        Pattern regex = Pattern.compile("\\w+\\W+|\\W+\\w+");
        Matcher matcher = regex.matcher(password);
        return matcher.matches() && password.length() >= 5;
    }


}
