package hello.demo_project.controller;

import hello.demo_project.domain.payHistory.PaymentHistory;
import hello.demo_project.domain.payHistory.PaymentHistoryDto;
import hello.demo_project.domain.product.ProductDto;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/paymentHistory")
@Slf4j
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    //상품 하나 id로 찾기
    @GetMapping("{paymentId}")
    public String getPaymentHistoryById(@PathVariable long paymentId, Model model) throws DataNotFoundException {
        PaymentHistoryDto paymentHistoryDto = paymentHistoryService.getPaymentHistory(paymentId);
        model.addAttribute("paymentHistory", paymentHistoryDto);
        return "paymentHistory/paymentHistoryDetail";
    }

    //상품 여러개 리스트로 찾기
    @GetMapping("/list")
    public String getPaymentHistoryList(Model model){
        List<PaymentHistoryDto> PaymentHistoryList = paymentHistoryService.getPaymentHistoryList();
        model.addAttribute("list", PaymentHistoryList);
        return "paymentHistory/paymentHistoryList";
    }

}
