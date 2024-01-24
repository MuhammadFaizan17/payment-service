package com.rak.payment.controller;

import com.rak.payment.service.DocumentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DocumentControllerTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private DocumentServiceImpl documentService = mock(DocumentServiceImpl.class);
    private ReceiptController receiptController = new ReceiptController(documentService);

    @Test
    public void testViewReceiptPositiveCase() throws Exception {
        String paymentRefNumber = "123456";
        byte[] pdfBytes = new byte[]{1, 2, 3};
        when(documentService.generatePdf("Skiply", paymentRefNumber)).thenReturn(pdfBytes);
        ResponseEntity<byte[]> response = receiptController.viewReceipt(paymentRefNumber);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(pdfBytes, response.getBody());
        HttpHeaders headers = response.getHeaders();
        assertEquals(MediaType.APPLICATION_PDF, headers.getContentType());
        assertEquals("inline; filename=\"output.pdf\"", headers.getContentDisposition().toString());
        verify(documentService).generatePdf("Skiply", paymentRefNumber);
    }

}
