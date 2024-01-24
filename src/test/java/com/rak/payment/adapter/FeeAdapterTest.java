package com.rak.payment.adapter;

import com.rak.payment.dto.FeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeeAdapterTest {
    @Mock
    private RestTemplate restTemplate;

    private FeeAdapter feeAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        feeAdapter = new FeeAdapter(restTemplate);
    }

    @Test
    void getFeeByIdPositiveCase() {

        Long schoolId = 1L;
        String grade = "G1";
        FeeDTO expectedSchool = new FeeDTO();
        ResponseEntity<FeeDTO> responseEntity = new ResponseEntity<>(expectedSchool, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(FeeDTO.class))).thenReturn(responseEntity);


        FeeDTO actualSchool = feeAdapter.getFeeByGrade(grade, schoolId);


        assertEquals(expectedSchool, actualSchool);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(FeeDTO.class));
    }

    @Test
    void getFeeByIdRestClientException() {

        Long schoolId = 1L;
        String grade = "G1";
        when(restTemplate.getForEntity(anyString(), eq(FeeDTO.class))).thenThrow(new RestClientException("Error"));
        assertThrows(RestClientException.class, () -> feeAdapter.getFeeByGrade(grade, schoolId));
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(FeeDTO.class));
    }
}
