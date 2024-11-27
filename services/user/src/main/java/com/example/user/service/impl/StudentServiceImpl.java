package com.example.user.service.impl;

import com.example.user.service.KafkaService;
import com.example.user.service.interfaces.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final KafkaService kafkaProducer;

    @Override
    public void getAllStudents() {
        kafkaProducer.sendRequest("");
    }

    @Override
    public void getStudentByGradeBook(String gradeBookNumber) {
        kafkaProducer.sendRequest(gradeBookNumber);
    }
}
