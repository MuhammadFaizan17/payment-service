package com.rak.payment.adapter;

import com.rak.payment.dto.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private StudentAdapter studentAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        studentAdapter = new StudentAdapter(restTemplate);
    }

    @Test
    public void testGetStudentByRollNoSuccess() {
        // Arrange
        String studentRollNo = "123";
        StudentDTO studentDTO = new StudentDTO();
        ResponseEntity<StudentDTO> responseEntity = new ResponseEntity<>(studentDTO, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(StudentDTO.class))).thenReturn(responseEntity);


        StudentDTO actualSchool = studentAdapter.getStudentByRollNo(studentRollNo);


        assertEquals(studentDTO, actualSchool);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(StudentDTO.class));
    }

    @Test
    public void testGetStudentByRollNoStudentNotFound() {
        String studentRollNo = "123";
        when(restTemplate.getForEntity(anyString(), eq(StudentDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));


        assertNull(studentAdapter.getStudentByRollNo(studentRollNo));
    }

    @Test
    public void testGetStudentByRollNoRestClientException() {
        String studentRollNo = "123";
        when(restTemplate.getForEntity(anyString(), eq(StudentDTO.class))).thenThrow(new RestClientException("Error"));
        assertThrows(RestClientException.class, () -> studentAdapter.getStudentByRollNo(studentRollNo));
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(StudentDTO.class));
    }
}
