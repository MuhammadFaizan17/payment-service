package com.rak.payment.mapper;

import com.rak.payment.domain.PaymentDetail;
import com.rak.payment.dto.FeeDTO;
import com.rak.payment.dto.PaymentDetailDTO;
import com.rak.payment.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    PaymentDetailDTO toDTO(PaymentDetail paymentDetail);

    @Mapping(target = "schoolName", source = "studentDTO.schoolName")
    @Mapping(target = "studentName", source = "studentDTO.studentName")
    @Mapping(target = "grade", source = "studentDTO.grade")
    @Mapping(target = "guardianName", source = "studentDTO.guardianName")
    @Mapping(target = "amount", source = "feeDTO.amount")
    @Mapping(target = "rollNumber", source = "studentDTO.rollNumber")
    PaymentDetail toEntity(StudentDTO studentDTO, FeeDTO feeDTO);

    List<PaymentDetailDTO> toDTO(List<PaymentDetail> paymentDetail);


}