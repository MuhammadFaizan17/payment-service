package com.rak.payment.dto;

import com.rak.payment.enums.CardScheme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDTO implements Serializable {


    private Long id;


    private String studentName;


    private String grade;


    private Double amount;


    private String guardianName;


    private CardScheme cardType;


    private String cardNo;


    private String schoolName;


    private String paymentRefNumber;


    private LocalDateTime transactionDateTime;

    private String rollNumber;

    private String schoolLogoUrl;
}