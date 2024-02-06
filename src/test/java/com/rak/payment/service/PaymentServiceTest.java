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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest()
//@AutoConfigureMockMvc(addFilters = false)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentServiceTest {

    @Mock
    private StudentAdapter studentAdapter;

    @Mock
    private FeeAdapter feeAdapter;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;
    @Mock
    private PaymentMapper paymentMapper;

    String imageUrl = "https://play-lh.googleusercontent.com/1h4qUW1ECJ9bd27nDbkvc3uGhwFeFGt0yIGIRBQspXW24uJ0i34ePxMy-EVAXSX9Pg=w600-h300-pc0xffffff-pd";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPayFeeSuccess() {
        String rollNumber = "1234";
        StudentDTO studentDTO = new StudentDTO(1L, "Faizan", "G1", "090078601", 1L, "Ali", "Sikply", rollNumber, imageUrl);
        FeeDTO feeDTO = new FeeDTO("G1", 25.0);
        PaymentDetail expectedPaymentDetail = new PaymentDetail(1L, "Faizan", "G1", 2.3, "Ali", "VISA", "44444-1111-1110-2202", "Skiply", CardScheme.VISA.name(), LocalDateTime.now(), "1234", imageUrl);

        when(studentAdapter.getStudentByRollNo(rollNumber)).thenReturn(studentDTO);
        when(feeAdapter.getFeeByGrade(eq(studentDTO.getGrade()), eq(studentDTO.getSchoolId()))).thenReturn(feeDTO);
        when(paymentRepository.save(any(PaymentDetail.class))).thenReturn(expectedPaymentDetail);
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(1L, "Faizan", "G1", 2.3, "Ali", CardScheme.VISA.getScheme(), "44444-1111-1110-2202", "Skiply", "TRX-1235", LocalDateTime.now(), "1234", imageUrl);
        when(paymentMapper.toDTO(expectedPaymentDetail)).thenReturn(paymentDetailDTO);
        when(paymentMapper.toEntity(studentDTO, feeDTO)).thenReturn(expectedPaymentDetail);

        PaymentDetailDTO actualPaymentDetailDTO = paymentService.payFee(rollNumber, "4111-1111-1111-1111", 1234);

        Assertions.assertNotNull(actualPaymentDetailDTO);
    }

    @Test
    public void testPayFeeStudentNotFound() {
        String rollNumber = "123";

        when(studentAdapter.getStudentByRollNo(eq(rollNumber))).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> paymentService.payFee(rollNumber, "4111-1111-1111-1111", 1234));
    }

    @Test
    public void testPayFeeFeeNotFound() {
        String rollNumber = "123";
        StudentDTO studentDTO = new StudentDTO();

        when(studentAdapter.getStudentByRollNo(eq(rollNumber))).thenReturn(studentDTO);
        when(feeAdapter.getFeeByGrade(eq(studentDTO.getGrade()), eq(studentDTO.getSchoolId()))).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> paymentService.payFee(rollNumber, "4111-1111-1111-1111", 1234));

    }

    @Test
    public void testGetByPaymentRefNum() {
        String paymentRefNum = "TRX-12345";
        PaymentDetail paymentDetail = new PaymentDetail(1L, "Faizan", "G1", 2.3, "Ali", "VISA", "44444-1111-1110-2202", "Skiply", CardScheme.VISA.getScheme(), LocalDateTime.now(), "1234", imageUrl);
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(1L, "Faizan", "G1", 2.3, "Ali", CardScheme.VISA.getScheme(), "44444-1111-1110-2202", "Skiply", "TRX-12345", LocalDateTime.now(), "1234", imageUrl);

        when(paymentRepository.findFirstByPaymentRefNumber(paymentRefNum)).thenReturn(java.util.Optional.of(paymentDetail));
        when(paymentMapper.toDTO(paymentDetail)).thenReturn(paymentDetailDTO);

        PaymentDetailDTO result = paymentService.getByPaymentRefNum(paymentRefNum);
        Assertions.assertNotNull(result);

    }

    @Test
    public void testGetPaymentByRollNumber() {
        String rollNumber = "1234";
        PaymentDetail paymentDetail1 = new PaymentDetail(1L, "Faizan", "G1", 12.3, "Ali", "VISA", "44444-1111-1110-2202", "Skiply", CardScheme.VISA.getScheme(), LocalDateTime.now(), "1234", imageUrl);
        PaymentDetail paymentDetail2 = new PaymentDetail(1L, "Faizan", "G1", 12.3, "Ali", "VISA", "44444-1111-1110-2202", "Skiply", CardScheme.VISA.getScheme(), LocalDateTime.now(), "1234", imageUrl);
        PaymentDetailDTO paymentDetailDTO1 = new PaymentDetailDTO(1L, "Faizan", "G1", 12.3, "Ali", CardScheme.VISA.getScheme(), "44444-1111-1110-2202", "Skiply", "TRX-12345", LocalDateTime.now(), "1234", imageUrl);
        PaymentDetailDTO paymentDetailDTO2 = new PaymentDetailDTO(1L, "Faizan", "G1", 12.3, "Ali", CardScheme.VISA.getScheme(), "44444-1111-1110-2202", "Skiply", "TRX-12345", LocalDateTime.now(), "1234", imageUrl);

        List<PaymentDetail> paymentDetails = Arrays.asList(paymentDetail1, paymentDetail2);
        List<PaymentDetailDTO> paymentDetailsDTO = Arrays.asList(paymentDetailDTO1, paymentDetailDTO2);


        when(paymentRepository.findAllByRollNumber(rollNumber)).thenReturn(paymentDetails);
        when(paymentMapper.toDTO(paymentDetails)).thenReturn(paymentDetailsDTO);

        List<PaymentDetailDTO> result = paymentService.getPaymentByRollNumber(rollNumber);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAllByRollNumber(any(String.class));

    }


    @Test
    public void testGetPaymentByRollNumberEmptyList() {
        String rollNumber = "456";
        when(paymentRepository.findAllByRollNumber(rollNumber)).thenReturn(List.of());

        List<PaymentDetailDTO> result = paymentService.getPaymentByRollNumber(rollNumber);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

}
