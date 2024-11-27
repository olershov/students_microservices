package com.example.student.service;

import com.example.student.model.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
}
