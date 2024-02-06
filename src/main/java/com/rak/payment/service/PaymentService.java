package com.rak.payment.service;

import com.rak.payment.dto.PaymentDetailDTO;

import java.util.List;

public interface PaymentService {
    PaymentDetailDTO payFee(String studentId, String cardSerialNumber, Integer cvv);

    PaymentDetailDTO getByPaymentRefNum(String paymentRefNum);

    List<PaymentDetailDTO> getPaymentByRollNumber(String rollNumber);
}
