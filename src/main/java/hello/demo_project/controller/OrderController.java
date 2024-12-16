package hello.demo_project.controller;

import hello.demo_project.domain.order.OrderReq;
import hello.demo_project.domain.product.Product;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import hello.demo_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    // 프론트가 가져갈 기능인지 백에서 검증이 필요한 데이터인지 스스로 생각해서 구분할 수 있어야함
    // 모르겠으면 안하지 말고 밑에 todo처럼 깡통만 만들어놓고 할 수 있는부분 부터 해놓아

    // 기능 말고 대충 흐름
    // [1]고객이 상품을 주문(물품 & 수량을 결정해서) // 장바구니에서 주문 버튼을 이용해서 주문 시작 (이떄 새 주문 생성)
    // [2]주문량 만큼 물건이 있는지 확인 ()
    //     만약 재고가 없으면? -> out of stock 하고 주문 자체를 취소해야함(묶여 있는 전체 주문 전부 다)
    // [3]전부다 재고가 있으면 주문 가능!
    // [4]배송지 입력
    // 쿠폰 사용 여부 확인, 포인트 사용 여부 확인 -> 총금액 결산
    // 결제 확인 - 어려움(ex 삼성페이 웹페이지에 qr뜸 //여기부터 내가 하는거아미-> 폰으로 qr을 찍음 -> 폰에 )
    // 위에 과정 전부다 통과하면? 주문 성공

    // 구현해야하는 기능
    // [1] 장바구니에서 체크해서 구매하기로한 물품 받아오기(주문 제품 조회) -주문 시작
    @PostMapping("/createOrder/{}")
    public String createOrder (@ModelAttribute OrderReq orderReq) throws DataNotFoundException, OutOfStockException {
        orderService.createOrder(orderReq);
        return "order/checkStock";
    }



    // 리스트로 체크된 제품들 받아서 구매하는 기능
    @PostMapping("/buyItems/{ProductIds}")
    public String buyItems(@PathVariable List<Long> productsIds, @PathVariable long userId) { //체크된 아이템 리스트로 받고
        for (Long productsId: productsIds) { // 개별 하나하나씩 구매
            orderService.buySelectedProduct(productsId, userId);
        }
        return "order/orderComplete"; // 주문 완료 페이지로 보내기
    }

    // 단일 상품 구매 기능


    //기존 배송지사용

    //신규 배송지 입력

    // 상품 주문 성공시 주문 상품 재고 감소 기능

    // 장바구니에서 계산한 총 금액 + 배송비 - (쿠폰이나 포인트) 를 사용한 최종 금액 산출해 내기

    // 결제 관련 api
    // 결제 api  어떻게 할건지....
    // 카카오 api 와 일반결제 api 생각하고는 있는데
    public String credit () {
        //todo 카카오 or 일반 결제 api 연결
        return "";
    }





}
