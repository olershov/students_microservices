package com.example.student.service.impl;

import com.example.student.model.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.interfaces.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentByGradeBook(String gradeBookNumber) {
        return studentRepository.findStudentByGradeBookNumber(gradeBookNumber);
    }
}
