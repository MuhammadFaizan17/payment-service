package com.rak.payment.repo;

import com.rak.payment.domain.PaymentDetail;
import com.rak.payment.enums.CardScheme;
import com.rak.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PaymentRepoTest {

    @Autowired
    private PaymentRepository paymentRepository;

    String imageUrl="https://play-lh.googleusercontent.com/1h4qUW1ECJ9bd27nDbkvc3uGhwFeFGt0yIGIRBQspXW24uJ0i34ePxMy-EVAXSX9Pg=w600-h300-pc0xffffff-pd";


    @Test
    public void testFindFirstByPaymentRefNumberPositive() {
        PaymentDetail paymentDetail = new PaymentDetail(1L, "Faizan", "G1", 2.3, "Ali", "VISA", "44444-1111-1110-2202", "REF123", CardScheme.MASTER_CARD, LocalDateTime.now(), "1234",imageUrl);

        paymentRepository.save(paymentDetail);

        Optional<PaymentDetail> result = paymentRepository.findFirstByPaymentRefNumber("REF123");

        assertTrue(result.isPresent());
    }

    @Test
    public void testFindFirstByPaymentRefNumberNegative() {
        Optional<PaymentDetail> result = paymentRepository.findFirstByPaymentRefNumber("REF456");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAllByRollNumberPositive() {

        PaymentDetail paymentDetail1 = new PaymentDetail(1L, "Faizan", "G1", 2.3, "Ali", "VISA", "44444-1111-1110-2202", "REF123", CardScheme.MASTER_CARD, LocalDateTime.now(), "1234",imageUrl);
        PaymentDetail paymentDetail2 = new PaymentDetail(2L, "Faizan", "G1", 2.3, "Ali", "VISA", "44444-1111-1110-2202", "REF123", CardScheme.MASTER_CARD, LocalDateTime.now(), "1234",imageUrl);


        paymentRepository.save(paymentDetail1);
        paymentRepository.save(paymentDetail2);

        List<PaymentDetail> result = paymentRepository.findAllByRollNumber("1234");

        assertEquals(2, result.size());
    }

    @Test
    public void testFindAllByRollNumberNegative() {
        List<PaymentDetail> result = paymentRepository.findAllByRollNumber("456");
        assertTrue(result.isEmpty());
    }
}
