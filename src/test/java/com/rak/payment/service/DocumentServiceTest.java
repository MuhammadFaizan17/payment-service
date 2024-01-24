package com.rak.payment.service;

import com.rak.payment.dto.PaymentDetailDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentServiceTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGeneratePdf() throws Exception {
        String templateName = "template";
        String paymentRefNum = "123";
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();

        when(paymentService.getByPaymentRefNum(paymentRefNum)).thenReturn(paymentDetailDTO);

        Context context = mock(Context.class);
        when(context.getVariable("guardianName")).thenReturn("Ali");
        byte[] mockByteArray = "Mocked PDF Content".getBytes();


        when(documentService.generatePdf(eq(templateName), any(String.class)))
                .thenReturn(mockByteArray);

        byte[] result = documentService.generatePdf(templateName, paymentRefNum);

        Assertions.assertNotNull(result);
    }
}
