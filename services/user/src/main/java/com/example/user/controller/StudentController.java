package com.example.user.controller;

import com.example.user.service.interfaces.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static com.example.user.controller.EndpointConstants.STUDENT;

@AllArgsConstructor
@RestController
@RequestMapping(STUDENT)
public class StudentController {

    private final StudentService studentService;
    private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        LOGGER.info("Request to get all students");
        studentService.getAllStudents();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gradeBook}")
    public ResponseEntity<?> getStudentByGradeBook(@PathVariable String gradeBook) {
        LOGGER.info("Request to get student with grade book number = " + gradeBook);
        studentService.getStudentByGradeBook(gradeBook);
        return ResponseEntity.ok().build();
    }
}