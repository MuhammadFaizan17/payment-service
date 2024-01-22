package com.rak.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO implements Serializable {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("studentId")
    private Long id;

    private String studentName;

    private String grade;

    private String mobileNumber;

    private Long schoolId;

    private String guardianName;

    private String schoolName;

    private String rollNumber;
}

