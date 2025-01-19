package hello.demo_project.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.order.*;
import hello.demo_project.domain.payHistory.PaymentHistory;
import hello.demo_project.domain.product.OrderProduct;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.user.User;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import hello.demo_project.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //[0] 장바구니에서 물품담고 그 중 살거 체크박스 해서 구매하기 버튼 클릭 / cart부분
    //[1] 주문페이지 - 여기서 기본오더 생성하고 배송지 입력, 상품리스트 보여줌,
    //    -> 기본오더 생성하면서 재고 확인
    // 결제방식 선택 + 포인트 사용, 쿠폰 선택 -> 총금액 xxxxxx원을 결제하기 버튼을 클릭
    //[2] 포트원 api로 카카오페이나 이니시스 창이 뜸 -> 창보고 결제완료 / 결제가 완료되면서 orderhistory 생성
    //[3] 결제가 완료되었습니다 창 (결제정보 좌라락)
    //    -> 하면서 포트원 정보 받아와서 order완성 + 실제 제고 감소

    //장바구니에서 결제하기 누르면 실행
    @GetMapping("/prepare")
    public String prepareOrder (@ModelAttribute OrderReq orderReq, Model model) throws DataNotFoundException, OutOfStockException {
        //ToDo 장바구니에서 체크한 제품리스트 받아와서 서비스에서 총금액 계산해서 orderDetail(지금은 index)에 넘기기

        orderReq.setProductList(new ArrayList<>());
        orderReq.getProductList().add(new OrderProduct(1, "상품1", 3000, 2, "https://media.istockphoto.com/id/148423285/ko/%EB%B2%A1%ED%84%B0/%ED%8A%9C%EB%B8%8C-%ED%99%94%EC%9E%A5%ED%92%88.jpg?s=1024x1024&w=is&k=20&c=YhO1DSPfA3NiK0uDo2nbmSjodN0z35lRuKR-DESSAZA=", 123));

        OrderPrepare orderPrepare = orderService.createOrder(orderReq);

        model.addAttribute("orderPrepare", orderPrepare);
        return "order"; //
    }

    //결제 확인
    @ResponseBody
    @PostMapping("/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid: {}", imp_uid);
        log.info("validateIamport");
        return orderService.validateIamport(imp_uid);
    }

    //결제 완료
    @PostMapping("/donePayment")
    public String processOrder(@RequestBody OrderResult orderResult, Model model) { //내 니즈에 맞춰서 형태를 새로 만들어서
        // 주문 정보를 로그에 출력
        log.info("Received orders: {}", orderResult.toString());
        // 성공적으로 받아들였다는 응답 반환
        PaymentHistory paymentHistory = orderService.completeOrder(orderResult);
        String orderId = paymentHistory.getOrderId();
        return "redirect:completeOrder/" + orderId;
    }

    @GetMapping("/completeOrder/{orderId}")
    public String completeOrder (@PathVariable String orderId, Model model) {
        OrderDto orderDto = orderService.getOrder(orderId);
        model.addAttribute("orderDto", orderDto);
        return "orderComplete";
    }


    //결제 취소
    @ResponseBody
    @PostMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        return orderService.cancelPayment(imp_uid);
    }



    /*@PostConstruct
    public void init() {
        User user1 = new User("id", "pass", "김", 123, 2, 123, new Date(), false);
        Product product1 = new Product("상품1", 123, 3,"ima_path", 2, 400);

        userRepository.save(user1);
        productRepository.save(product1);
    }*/

/*
    // ================================= 재령 파트 =================================

    @ResponseBody
    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getOrderHistory(@RequestParam("memberId") Long memberId) {
        List<OrderDto> orderList = orderService.getOrderListByMemberId(memberId);
        return ResponseEntity.ok(orderList);
    }

    // 배송 조회 API 호출
    *//*@ResponseBody
    @PostMapping("/trackingInfo")
    public ResponseEntity<?> trackDelivery(@RequestParam String waybillCode) {
        try {
            Object trackingInfo = deliveryService.getTrackingInfo(waybillCode);
            return ResponseEntity.ok(trackingInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("배송 조회 오류");
        }
    }*//*

    // 상태별 주문 조회 (취소, 교환, 환불)
    @ResponseBody
    @GetMapping("/status/filter")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@RequestParam("statusType") String statusType) {
        try {
            List<Order> orders;

            // 상태에 맞는 주문 내역 조회
            if ("all".equalsIgnoreCase(statusType)) {
                // "all"일 경우 모든 주문을 반환
                orders = orderService.getAllOrders();
            } else {
                switch (statusType.toLowerCase()) {
                    case "cancel":
                        orders = orderService.getOrdersByStatus("취소");
                        break;
                    case "exchange":
                        orders = orderService.getOrdersByStatus("교환");
                        break;
                    case "refund":
                        orders = orderService.getOrdersByStatus("환불");
                        break;
                    case "return":
                        orders = orderService.getOrdersByStatus("반품");
                        break;
                    default:
                        return ResponseEntity.status(400).body(null);  // 잘못된 상태Type 처리
                }
            }

            List<OrderDto> orderDtos = orderService.convertToDtoList(orders);  // DTO로 변환
            return ResponseEntity.ok(orderDtos);  // 상태별 주문 내역 반환

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // 오류 발생 시 null 반환
        }
    }



    // 모든 주문 조회 (all 상태 처리)
    @ResponseBody
    @GetMapping("/status/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();  // 모든 주문 조회
            List<OrderDto> orderDtos = orderService.convertToDtoList(orders);
            return ResponseEntity.ok(orderDtos);  // List<OrderDto> 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // 오류 발생 시 null 반환
        }
    }

    // 취소
    @ResponseBody
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            // 주문 취소 가능 상태 체크 후 처리
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("주문이 취소되었습니다.");
        } catch (Exception e) {
            // 예외가 발생하면 메시지를 반환
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    // 교환
    @ResponseBody
    @PostMapping("/exchange/{orderId}")
    public ResponseEntity<?> exchangeOrder(@PathVariable Long orderId) {
        try {
            orderService.exchangeOrder(orderId); // 교환 로직
            return ResponseEntity.ok("주문이 교환되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 예외
        }
    }

    // 환불
    @ResponseBody
    @PostMapping("/refund/{orderId}")
    public ResponseEntity<?> refundOrder(@PathVariable Long orderId) {
        try {
            orderService.refundOrder(orderId); // 환불 로직
            return ResponseEntity.ok("주문이 환불되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 예외
        }
    }
    */
}
