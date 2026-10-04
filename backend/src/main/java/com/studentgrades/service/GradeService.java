package com.studentgrades.service;

import com.studentgrades.dto.GradeDTO;
import com.studentgrades.model.Grade;
import com.studentgrades.model.Student;
import com.studentgrades.model.Course;
import com.studentgrades.repository.GradeRepository;
import com.studentgrades.repository.StudentRepository;
import com.studentgrades.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Grade addGrade(GradeDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Course course = courseRepository.findById(dto.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found"));

        String gradeLetter = calculateGradeLetter(dto.getMarks());
        BigDecimal gradePoints = calculateGradePoints(gradeLetter);

        Grade grade = Grade.builder()
            .student(student)
            .course(course)
            .marks(dto.getMarks())
            .gradeLetter(gradeLetter)
            .gradePoints(gradePoints)
            .semester(dto.getSemester())
            .academicYear(dto.getAcademicYear())
            .build();

        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByCourse(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    public Grade updateGrade(Long id, GradeDTO dto) {
        Grade grade = gradeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grade not found"));

        grade.setMarks(dto.getMarks());
        grade.setGradeLetter(calculateGradeLetter(dto.getMarks()));
        grade.setGradePoints(calculateGradePoints(grade.getGradeLetter()));
        grade.setSemester(dto.getSemester());
        grade.setAcademicYear(dto.getAcademicYear());

        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grade not found"));
        gradeRepository.delete(grade);
    }

    private String calculateGradeLetter(BigDecimal marks) {
        if (marks.compareTo(new BigDecimal("90")) >= 0) return "A";
        if (marks.compareTo(new BigDecimal("80")) >= 0) return "B";
        if (marks.compareTo(new BigDecimal("70")) >= 0) return "C";
        if (marks.compareTo(new BigDecimal("60")) >= 0) return "D";
        return "F";
    }

    private BigDecimal calculateGradePoints(String gradeLetter) {
        return switch (gradeLetter) {
            case "A" -> new BigDecimal("4.0");
            case "B" -> new BigDecimal("3.0");
            case "C" -> new BigDecimal("2.0");
            case "D" -> new BigDecimal("1.0");
            default -> new BigDecimal("0.0");
        };
    }
}