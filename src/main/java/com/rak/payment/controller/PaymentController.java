package com.rak.payment.controller;

import com.rak.payment.dto.PaymentDetailDTO;
import com.rak.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "pay fee")
    public ResponseEntity<PaymentDetailDTO> createSchool(@RequestParam("rollNo") String rollNo) {

        return new ResponseEntity<>(paymentService.payFee(rollNo), HttpStatus.ACCEPTED);
    }

    @GetMapping("{paymentRefNum}")
    @Operation(summary = "Get Payment Detail by paymentRefNum")
    public ResponseEntity<PaymentDetailDTO> getPaymentDetailByPaymentRefNum(@PathVariable("paymentRefNum") String bankRefNum) {

        return new ResponseEntity<>(paymentService.getByPaymentRefNum(bankRefNum), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Payment Detail by rollNum")
    public ResponseEntity<List<PaymentDetailDTO>> getPaymentDetailByRollNumber(@RequestParam("rollNum") String rollNum) {

        return new ResponseEntity<>(paymentService.getPaymentByRollNumber(rollNum), HttpStatus.OK);
    }
}
