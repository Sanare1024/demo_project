package hello.demo_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    // 장바구니에서 체크해서 구매하기로한 물품 리스트 받아오기(주문 제품 조회)

    // 장바구니에서 계산한 총 금액 + 배송비 - (쿠폰이나 포인트) 를 사용한 최종 금액 산출해 내기

    // 장바구니에 담아둔 물품들중 체크된 상품만 주문하기(주문하면 삭제되게)

    // 결제 관련 api
    // 결제 api  어떻게 할건지....
    // 카카오 api 와 일반결제 api 생각하고는 있는데





}
