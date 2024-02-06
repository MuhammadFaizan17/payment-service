package com.rak.payment.controller;

import com.rak.payment.dto.PaymentDetailDTO;
import com.rak.payment.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateSchoolPositive() {
        PaymentDetailDTO paymentDetail = new PaymentDetailDTO();
        when(paymentService.payFee("123", "4111-1111-1111-1111", 1234)).thenReturn(paymentDetail);

        ResponseEntity<PaymentDetailDTO> responseEntity = paymentController.payFee("123", "4111-1111-1111-1111", 1234);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(paymentDetail, responseEntity.getBody());
    }

    @Test
    public void testCreateSchoolNegative() {
        when(paymentService.payFee("456", "4111-1111-1111-1111", 1234)).thenReturn(null);
        ResponseEntity<PaymentDetailDTO> responseEntity = paymentController.payFee("456", "4111-1111-1111-1111", 1234);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}
