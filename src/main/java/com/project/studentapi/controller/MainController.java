package com.project.studentapi.controller;

import com.project.studentapi.model.Student;
import com.project.studentapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class MainController {

    @Autowired
    private StudentService studentService;

    // This method is publicly accessible to anyone as per the SecurityConfig
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    // This method is publicly accessible to anyone
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getById(id);
    }

    // Requires ADMIN role to create a new student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    // Requires ADMIN role to update a student
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails);
    }

    // Requires ADMIN role to delete a student
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
    }
}