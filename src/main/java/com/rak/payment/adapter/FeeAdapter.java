package com.rak.payment.adapter;

import com.rak.payment.dto.FeeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeeAdapter {
    private final RestTemplate restTemplate;

    @Value("${fee.service.url}")
    private String feeServiceUrl;

    public FeeDTO getFeeByGrade(String grade, Long schoolId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(feeServiceUrl + "/fee/grade?grade=" + grade + "&schoolId=" + schoolId);
        try {
            log.info("Calling student service:{}", builder.toUriString());
            ResponseEntity<FeeDTO> responseEntity = restTemplate.getForEntity(builder.toUriString(), FeeDTO.class);
            FeeDTO feeDTO = responseEntity.getBody();
            if (null == feeDTO)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fee not configured");
            return feeDTO;
        } catch (RestClientException e) {
            log.error("Error occurred while saving user on admin: {}", e.getMessage());
            throw (e);
        }
    }
}
