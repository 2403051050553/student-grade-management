package com.studentgrades.service;

import com.studentgrades.dto.StudentDTO;
import com.studentgrades.model.Student;
import com.studentgrades.model.User;
import com.studentgrades.repository.StudentRepository;
import com.studentgrades.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(StudentDTO dto) {
        // Check if enrollment number already exists
        if (studentRepository.findByEnrollmentNumber(dto.getEnrollmentNumber()).isPresent()) {
            throw new RuntimeException("Enrollment number already exists");
        }

        // Create user
        User user = User.builder()
            .username(dto.getEmail().split("@")[0])
            .email(dto.getEmail())
            .password("default123") // Should be hashed in real app
            .role(User.Role.STUDENT)
            .isActive(true)
            .build();
        user = userRepository.save(user);

        // Create student
        Student student = Student.builder()
            .user(user)
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .enrollmentNumber(dto.getEnrollmentNumber())
            .dateOfBirth(dto.getDateOfBirth())
            .phoneNumber(dto.getPhoneNumber())
            .address(dto.getAddress())
            .city(dto.getCity())
            .build();

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, StudentDTO dto) {
        Student student = getStudentById(id);
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setAddress(dto.getAddress());
        student.setCity(dto.getCity());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}