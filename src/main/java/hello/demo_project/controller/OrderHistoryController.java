package hello.demo_project.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.orderHistory.OrderHistoryDto;
import hello.demo_project.domain.orderHistory.OrderHistoryRepository;
import hello.demo_project.domain.payment.PaymentOrderDto;
import hello.demo_project.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderHistory")
@Slf4j
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    //결제 확인
    @PostMapping("/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid: {}", imp_uid);
        log.info("validateIamport");
        return orderHistoryService.validateIamport(imp_uid);
    }
    //결제 시작
    @PostMapping("/order")
    public ResponseEntity<String> processOrder(@RequestBody OrderHistoryDto orderHistoryDto) {
        // 주문 정보를 로그에 출력
        log.info("Received orders: {}", orderHistoryDto.toString());
        // 성공적으로 받아들였다는 응답 반환
        return ResponseEntity.ok(orderHistoryService.saveOrder(orderHistoryDto));
    }
    //결제 취소
    @PostMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        return orderHistoryService.cancelPayment(imp_uid);
    }

}
