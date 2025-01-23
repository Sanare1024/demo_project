package hello.demo_project.service;

import hello.demo_project.domain.payHistory.PaymentHistory;
import hello.demo_project.domain.payHistory.PaymentHistoryDto;
import hello.demo_project.domain.payHistory.PaymentHistoryRepository;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductDto;
import hello.demo_project.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryDto getPaymentHistory(long paymentId) throws DataNotFoundException {
        PaymentHistory paymentHistory = paymentHistoryRepository.getPaymentHistoryByPaymentId(paymentId)
                .orElseThrow(() -> new DataNotFoundException("paymentHistory not found"));

        log.info("paymentHistory : {}",paymentHistory);

        return new PaymentHistoryDto(paymentHistory.getPaymentId(), paymentHistory.getOrderId(), paymentHistory.getTotalPrice(),
                paymentHistory.getBankCode(), paymentHistory.getBankName(), paymentHistory.getOrderAt(), paymentHistory.getPayStatus(),
                paymentHistory.getUsedPoint());
    }

    public List<PaymentHistoryDto> getPaymentHistoryList() {
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository.findAll();
        log.info("paymentHistoryList : {}", paymentHistoryList);

        List<PaymentHistoryDto> PaymentHistoryDtoList = new ArrayList<>();
        for (PaymentHistory paymentHistory : paymentHistoryList) {
            PaymentHistoryDtoList.add(new PaymentHistoryDto(paymentHistory.getPaymentId(), paymentHistory.getOrderId(),
                    paymentHistory.getTotalPrice(), paymentHistory.getBankCode(), paymentHistory.getBankName(),
                    paymentHistory.getOrderAt(), paymentHistory.getPayStatus(), paymentHistory.getUsedPoint()));
        }

        return PaymentHistoryDtoList;
    }

    public PaymentHistoryDto getPaymentHistory(String orderId) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findPaymentHistoriesByOrderId(orderId);

        log.info("paymentHistory : {}",paymentHistory);

        return new PaymentHistoryDto(paymentHistory.getPaymentId(), paymentHistory.getOrderId(), paymentHistory.getTotalPrice(),
                paymentHistory.getBankCode(), paymentHistory.getBankName(), paymentHistory.getOrderAt(), paymentHistory.getPayStatus(),
                paymentHistory.getUsedPoint());
    }

}
