package com.studentgrades.controller;

import com.studentgrades.dto.GradeDTO;
import com.studentgrades.model.Grade;
import com.studentgrades.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
@Tag(name = "Grades", description = "Grade management endpoints")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    @Operation(summary = "Add grade")
    public ResponseEntity<Grade> addGrade(@Valid @RequestBody GradeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeService.addGrade(dto));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get grades by student")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get grades by course")
    public ResponseEntity<List<Grade>> getGradesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeService.getGradesByCourse(courseId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update grade")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeDTO dto) {
        return ResponseEntity.ok(gradeService.updateGrade(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete grade")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}