package com.rak.payment.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.rak.payment.dto.PaymentDetailDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final PaymentService paymentService;

    private final SpringTemplateEngine springTemplateEngine;

    public byte[] generatePdf(String templateName, String paymentRefNum) throws Exception {
        PaymentDetailDTO dto = paymentService.getByPaymentRefNum(paymentRefNum);
        Context context = new Context();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("guardianName", dto.getGuardianName());
        variables.put("studentName", dto.getStudentName());
        variables.put("amount", dto.getAmount());
        variables.put("schoolName", dto.getSchoolName());
        variables.put("datetime", dto.getTransactionDateTime());
        variables.put("rollNumber", dto.getRollNumber());
        variables.put("reference", dto.getPaymentRefNumber());
        variables.put("cardNumber", dto.getCardNo());
        variables.put("cardType", dto.getCardType());
        variables.put("grade", dto.getGrade());


        context.setVariables(variables);

        return generatePdfFromTemplate(templateName, context);
    }


    public byte[] generatePdfFromTemplate(String templateName, Context context) throws Exception {
        String htmlContent = springTemplateEngine.process(templateName, context);
        return convertHtmlToPdfBytes(htmlContent);
    }

    public byte[] convertHtmlToPdfBytes(String htmlString) throws DocumentException, FileNotFoundException {
        Document document = new Document();


        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, out);

        document.open();

        InputStream in = IOUtils.toInputStream(htmlString);
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();

        return out.toByteArray();
    }


}

