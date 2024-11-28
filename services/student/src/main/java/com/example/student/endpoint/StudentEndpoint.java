package com.example.student.endpoint;

import com.example.student.generatedxml.GetStudentRequest;
import com.example.student.generatedxml.GetStudentResponse;
import com.example.student.generatedxml.StudentSoap;
import com.example.student.model.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@RequiredArgsConstructor
@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://student.example.com/generatedXml";
    private final StudentService studentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentRequest")
    @ResponsePayload
    public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) {
        GetStudentResponse response = new GetStudentResponse();
        var gradeBookNumber = request.getGradeBookNumber();
        if (gradeBookNumber != null) {
            response.setStudent(findByGradeBook(gradeBookNumber));
        } else {
            response.getStudents().addAll(findAll());
        }
        return response;
    }

    private StudentSoap findByGradeBook(String gradeBookNumber) {
        StudentSoap result = null;
        Student student = studentService.getStudentByGradeBook(gradeBookNumber);
        if (student != null) {
            result = convertToSoapStudent(student);
        }
        return result;
    }

    private List<StudentSoap> findAll() {
        List<Student> allStudents = studentService.getAllStudents();
        return allStudents.stream().map(this::convertToSoapStudent).toList();
    }

    private StudentSoap convertToSoapStudent(Student student) {
        var soapStudent = new StudentSoap();
        soapStudent.setId(student.getId());
        soapStudent.setFirstName(student.getFirstName());
        soapStudent.setSurname(student.getSurname());
        soapStudent.setPatronymic(student.getPatronymic());
        soapStudent.setGradeBookNumber(student.getGradeBookNumber());
        soapStudent.setFaculty(String.valueOf(student.getFaculty()));
        soapStudent.setPhotoUrl(null);
        return soapStudent;
    }
}
