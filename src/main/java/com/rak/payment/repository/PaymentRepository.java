package com.rak.payment.repository;

import com.rak.payment.domain.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetail, Long> {
    Optional<PaymentDetail> findFirstByPaymentRefNumber(String paymentRefNum);

    List<PaymentDetail> findAllByRollNumber(String rollNumber);
}

