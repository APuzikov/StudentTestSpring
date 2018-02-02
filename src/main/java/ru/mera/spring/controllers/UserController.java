package ru.mera.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mera.spring.entity.Student;
import ru.mera.spring.entity.StudentTest;
import ru.mera.spring.form.LoginForm;
import ru.mera.spring.form.StudentForm;
import ru.mera.spring.repository.StudentRepository;
import ru.mera.spring.repository.StudentTestRepository;
import ru.mera.spring.service.StudentService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    private StudentService studentService;

    @RequestMapping(path = "/authorization", method = RequestMethod.GET)
    public String authorization(Model model){

        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);

        return "checkLogin";
    }

    @RequestMapping(path = "/checkStudent", method = RequestMethod.POST)
    public String checkStudent(@ModelAttribute("loginForm") LoginForm loginForm, Model model){

        Student student = studentRepository.findByEmail(loginForm.getEmail());

        if (student == null) {
            model.addAttribute("errorMessage", "Student not found!");
            return "checkLogin";
        }

        if (!student.getPassword().equals(loginForm.getPassword())){
            model.addAttribute("errorMessage", "Invalid password!");
            return "checkLogin";
        }

        List<StudentTest> studentTests = studentTestRepository.findByStudentId(student.getId());
        model.addAttribute("studentTests", studentTests);
        model.addAttribute("student", student);

        return "studentTests";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {

        StudentForm studentForm = new StudentForm();
        model.addAttribute(studentForm);

        return "addStudent";
    }

    @RequestMapping(path = "/saveStudent", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("studentForm") StudentForm studentForm, Model model){

        Student student = new Student();
        if (studentForm.getPassword().equals(studentForm.getPasswordAgain())) {

            student.setName(studentForm.getName());
            student.setEmail(studentForm.getEmail());
            student.setPassword(studentForm.getPassword());

            try {
                studentService.createStudent(student);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "addStudent";
            }
        } else {
            model.addAttribute("errorMessage", "Passwords do not match!");
            return "addStudent";
        }

        Student studentWithId = studentRepository.findByEmail(studentForm.getEmail());
        model.addAttribute("student", studentWithId);
        return "studentTests";
    }
}
