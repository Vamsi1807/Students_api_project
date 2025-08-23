package com.project.studentapi.service;

import com.project.studentapi.model.Student;
import com.project.studentapi.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getAll(){
        return studentRepo.findAll();
    }

    public Student getById(int id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            return null;
        }
    }

    public Student createStudent(Student student) {
        // The save method handles both creation and updates
        return studentRepo.save(student);
    }

    public Student updateStudent(int id, Student studentDetails) {
        // First, find the existing student by ID
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Update the student's details with the new information
        student.setName(studentDetails.getName());
        student.setDepartment(studentDetails.getDepartment());
        student.setAttendance(studentDetails.getAttendance());
        student.setGrade(studentDetails.getGrade());
        student.setSem(studentDetails.getSem());
        student.setId(id);


        // Save the updated student back to the database
        return studentRepo.save(student);
    }

    public void deleteStudent(int id) {
        // Delete the student by their ID
        studentRepo.deleteById(id);
    }
}