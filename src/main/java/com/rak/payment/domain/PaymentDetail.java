package com.rak.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String guardianName;

    @Column(nullable = false)
    private String cardNo;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String paymentRefNumber;

    @Column(nullable = false)
    private String cardType;


    @Column(nullable = false)
    private LocalDateTime transactionDateTime;

    @Column(nullable = false)
    private String rollNumber;

    @Column
    private String schoolLogoUrl;


}