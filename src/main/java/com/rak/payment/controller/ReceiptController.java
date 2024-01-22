package com.rak.payment.controller;

import com.rak.payment.service.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/receipt")
@RestController
public class ReceiptController {

    private final DocumentServiceImpl documentService;

    @GetMapping()
    @ResponseBody
    public ResponseEntity<byte[]> viewReceipt(@RequestParam String paymentRefNumber) throws Exception {
        byte[] pdfBytes = documentService.generatePdf("Skiply", paymentRefNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("output.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
