package com.studentgrades.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO {
    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Marks are required")
    @DecimalMin(value = "0.0", message = "Marks must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "Marks must be between 0 and 100")
    private BigDecimal marks;

    @NotBlank(message = "Semester is required")
    private String semester;

    @NotNull(message = "Academic year is required")
    private Integer academicYear;

    private String gradeLetter;
    private BigDecimal gradePoints;
}