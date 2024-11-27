package com.example.user.service.impl;

import com.example.user.service.KafkaSenderService;
import com.example.user.service.interfaces.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final KafkaSenderService kafkaSenderService;

    @Override
    public void getAllStudents() {
        var result = kafkaSenderService.sendSyncRequest(null);
    }

    @Override
    public void getStudentByGradeBook(String gradeBookNumber) {
        var result = kafkaSenderService.sendSyncRequest(gradeBookNumber);
    }
}
