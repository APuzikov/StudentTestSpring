package ru.mera.spring.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mera.spring.entity.Student;
import ru.mera.spring.repository.StudentRepository;
import ru.mera.spring.repository.StudentTestRepository;
import ru.mera.spring.service.StudentTestService;

@Controller
public class StudentTestController {

    @Autowired
    StudentTestService studentTestService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentTestRepository studentTestRepository;

    @RequestMapping(path = "/studentTests", method = RequestMethod.GET)
    public String startTest(@RequestParam int studentId, @RequestParam int countOfQuestions,  Model model){

        Student student = studentRepository.findOne(studentId);

        try {
            studentTestService.createStudentTest(student, countOfQuestions);
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "studentTests";
        }
        return "startTest";
    }
}
