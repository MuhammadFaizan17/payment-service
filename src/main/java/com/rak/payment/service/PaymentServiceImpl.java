package com.rak.payment.service;

import com.rak.payment.adapter.FeeAdapter;
import com.rak.payment.adapter.StudentAdapter;
import com.rak.payment.domain.PaymentDetail;
import com.rak.payment.dto.FeeDTO;
import com.rak.payment.dto.PaymentDetailDTO;
import com.rak.payment.dto.StudentDTO;
import com.rak.payment.enums.CardScheme;
import com.rak.payment.mapper.PaymentMapper;
import com.rak.payment.repository.PaymentRepository;
import com.rak.payment.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final StudentAdapter studentAdapter;
    private final FeeAdapter feeAdapter;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDetailDTO payFee(String rollNumber) {
        StudentDTO studentDTO = studentAdapter.getStudentByRollNo(rollNumber);
        if (null != studentDTO && StringUtils.hasLength(studentDTO.getGrade())) {
            FeeDTO feeDTO = feeAdapter.getFeeByGrade(studentDTO.getGrade(), studentDTO.getSchoolId());
            PaymentDetail paymentDetail = paymentMapper.toEntity(studentDTO, feeDTO);
            paymentDetail.setCardType(CardScheme.VISA.getScheme());
            paymentDetail.setTransactionDateTime(LocalDateTime.now());
            paymentDetail.setCardNo(String.valueOf(Utility.generateFormattedRandomNumber()));
            paymentDetail.setPaymentRefNumber("TRX-" + UUID.randomUUID().toString().substring(0, 5));
            return paymentMapper.toDTO(paymentRepository.save(paymentDetail));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "we cannot process your at this time");


    }

    @Override
    public PaymentDetailDTO getByPaymentRefNum(String paymentRefNum) {
        return paymentMapper.toDTO(paymentRepository.findFirstByPaymentRefNumber(paymentRefNum)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "record not found against: " + paymentRefNum)));


    }

    @Override
    public List<PaymentDetailDTO> getPaymentByRollNumber(String rollNumber) {
        return paymentMapper.toDTO(paymentRepository.findAllByRollNumber(rollNumber));

    }
}
