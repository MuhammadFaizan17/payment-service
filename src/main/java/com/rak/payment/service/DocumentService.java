package com.rak.payment.service;

public interface DocumentService {
    byte[] generatePdf(String templateName, String paymentRefNum) throws Exception;
}
