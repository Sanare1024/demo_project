package hello.demo_project.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.payment.PaymentOrderDto;
import hello.demo_project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/vi/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    //결제 확인
    @PostMapping("/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid: {}", imp_uid);
        log.info("validateIamport");
        return paymentService.validateIamport(imp_uid);
    }
    //결제 시작
    @PostMapping("/order")
    public ResponseEntity<String> processOrder(@RequestBody PaymentOrderDto paymentOrderDto) {
        // 주문 정보를 로그에 출력
        log.info("Received orders: {}", paymentOrderDto.toString());
        // 성공적으로 받아들였다는 응답 반환
        return ResponseEntity.ok(paymentService.saveOrder(paymentOrderDto));
    }
    //결제 취소
    @PostMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        return paymentService.cancelPayment(imp_uid);
    }



}
