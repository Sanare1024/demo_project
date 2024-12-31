package hello.demo_project.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.order.OrderRepository;
import hello.demo_project.domain.orderHistory.OrderHistory;
import hello.demo_project.domain.orderHistory.OrderHistoryDto;
import hello.demo_project.domain.orderHistory.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryService {

    private final IamportClient iamportClient;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderRepository orderRepository;

    public IamportResponse<Payment> validateIamport(String imp_uid) {
        try {
            IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
            log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse());
            return payment;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public IamportResponse<Payment> cancelPayment(String imp_uid) {
        try {
            CancelData cancelData = new CancelData(imp_uid, true);
            IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);
            return payment;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public String saveOrder(OrderHistoryDto orderHistoryDto) {
        try {
            OrderHistory orderHistory = new OrderHistory(

            );

            orderHistoryRepository.save(orderHistory);
            return "주문 정보가 성공적으로 저장되었습니다.";
        } catch (Exception e) {
            log.info(e.getMessage());
            cancelPayment(orderHistoryDto.getImpUid());
            return "주문 정보 저장에 실패했습니다.";
        }
    }


}
