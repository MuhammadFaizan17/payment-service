package com.rak.payment.service;

import com.rak.payment.dto.PaymentDetailDTO;

import java.util.List;

public interface PaymentService {
    PaymentDetailDTO payFee(String studentId);
    PaymentDetailDTO getByPaymentRefNum(String paymentRefNum);
    List<PaymentDetailDTO> getPaymentByRollNumber(String tollNumber);
}
