package com.rak.payment.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.rak.payment.dto.PaymentDetailDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final PaymentService paymentService;

    private final SpringTemplateEngine springTemplateEngine;

    /**
     * Generates a PDF document using a template and payment details.
     *
     * @param templateName  The name of the template to be used.
     * @param paymentRefNum The payment reference number to retrieve payment details.
     * @return The generated PDF document as a byte array.
     * @throws Exception If an error occurs during PDF generation or if payment details are not found.
     */
    public byte[] generatePdf(String templateName, String paymentRefNum) throws Exception {
        PaymentDetailDTO dto = paymentService.getByPaymentRefNum(paymentRefNum);
        if (!ObjectUtils.isEmpty(dto)) {
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
            variables.put("logoUrl", dto.getSchoolLogoUrl());
            variables.put("cardLogoUrl", dto.getCardType().getLogoURL());

            context.setVariables(variables);
            return generatePdfFromTemplate(templateName, context);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "receipt not found against paymentRefNum: " + paymentRefNum);
    }


    /**
     * It contains a method named generatePdfFromTemplate that takes a template name and a context as input
     * and generates a PDF file by converting the HTML content obtained from the template using a Spring template engine.
     * The generated PDF is returned as a byte array.
     */
    private byte[] generatePdfFromTemplate(String templateName, Context context) throws Exception {
        String htmlContent = springTemplateEngine.process(templateName, context);
        return convertHtmlToPdfBytes(htmlContent);
    }

    /**
     * Converts an HTML string to a PDF byte array.
     *
     * @param htmlString the HTML string to convert
     * @return the converted PDF byte array
     * @throws DocumentException if an error occurs during the conversion
     */
    private byte[] convertHtmlToPdfBytes(String htmlString) throws DocumentException {
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

