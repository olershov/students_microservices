package com.example.student.endpoint;

import com.example.student.generatedxml.GetStudentRequest;
import com.example.student.generatedxml.GetStudentResponse;
import com.example.student.generatedxml.StudentSoap;
import com.example.student.model.entity.Student;
import com.example.student.service.interfaces.MinioService;
import com.example.student.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

import static com.example.student.repository.MinioRepository.STUDENTS_PHOTO_BUCKET;

@RequiredArgsConstructor
@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://student.example.com/generatedXml";
    private final StudentService studentService;
    private final MinioService minioService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentRequest")
    @ResponsePayload
    public GetStudentResponse getStudent(@RequestPayload GetStudentRequest request) {
        GetStudentResponse response = new GetStudentResponse();
        var gradeBookNumber = request.getGradeBookNumber();
        response.getStudents().add(findByGradeBook(gradeBookNumber));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentsRequest")
    @ResponsePayload
    public GetStudentResponse getStudents() {
        GetStudentResponse response = new GetStudentResponse();
        response.getStudents().addAll(findAll());
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
        var photoUrl = minioService.getObjectUrl(student.getGradeBookNumber(), STUDENTS_PHOTO_BUCKET);
        soapStudent.setId(student.getId());
        soapStudent.setFirstName(student.getFirstName());
        soapStudent.setSurname(student.getSurname());
        soapStudent.setPatronymic(student.getPatronymic());
        soapStudent.setGradeBookNumber(student.getGradeBookNumber());
        soapStudent.setFaculty(String.valueOf(student.getFaculty()));
        soapStudent.setPhotoUrl(photoUrl);
        return soapStudent;
    }
}
