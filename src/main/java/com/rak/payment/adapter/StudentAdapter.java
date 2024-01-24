package com.rak.payment.adapter;

import com.rak.payment.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class StudentAdapter {
    private final RestTemplate restTemplate;

    @Value("${student.service.url}")
    private String studentServiceUrl;


    public StudentDTO getStudentByRollNo(String studentId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(studentServiceUrl + "/students/rollNo/" + studentId);
        try {
            log.info("Calling student service:{}", builder.toUriString());
            ResponseEntity<StudentDTO> responseEntity = restTemplate.getForEntity(builder.toUriString(), StudentDTO.class);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Error occurred while saving user on admin: {}", e.getMessage());
            throw (e);
        }
    }
}
