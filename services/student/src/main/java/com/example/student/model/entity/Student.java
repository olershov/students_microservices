package com.example.student.model.entity;

import com.example.student.model.enums.Faculty;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "grade_book_number")
    private String gradeBookNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "faculty")
    private Faculty faculty;

}
