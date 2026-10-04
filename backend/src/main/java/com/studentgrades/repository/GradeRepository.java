package com.studentgrades.repository;

import com.studentgrades.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByCourseId(Long courseId);
    List<Grade> findByStudentIdAndSemester(Long studentId, String semester);
    
    @Query("SELECT AVG(g.marks) FROM Grade g WHERE g.course.id = ?1")
    BigDecimal getAverageByCourse(Long courseId);
    
    @Query("SELECT AVG(g.marks) FROM Grade g WHERE g.student.id = ?1")
    BigDecimal getAverageByStudent(Long studentId);
}