package hello.demo_project.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.order.OrderPrepare;
import hello.demo_project.domain.order.OrderReq;
import hello.demo_project.domain.order.OrderResult;
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
        orderReq.getProductList().add(new OrderProduct(1, "상품1",123, 5, "imgPath"));

        OrderPrepare orderPrepare = orderService.createOrder(orderReq);

        model.addAttribute("orderPrepare", orderPrepare);
        return "order"; //
    }

    //결제 확인
    @PostMapping("/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid: {}", imp_uid);
        log.info("validateIamport");
        return orderService.validateIamport(imp_uid);
    }

    //결제 완료
    @PostMapping("/donePayment")
    public ResponseEntity<String> processOrder(@RequestBody OrderResult orderResult) { //내 니즈에 맞춰서 형태를 새로 만들어서
        // 주문 정보를 로그에 출력
        log.info("Received orders: {}", orderResult.toString());
        // 성공적으로 받아들였다는 응답 반환
        return ResponseEntity.ok(orderService.completeOrder(orderResult));
        //Todo 리스폰스ok 가 아니라 페이지를 돌려준다 + 결제결과(OrderResult)를 담아서 같이 넘겨
    }


    //결제 취소
    @PostMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        return orderService.cancelPayment(imp_uid);
    }

    @PostConstruct
    public void init() {
        User user1 = new User("id", "pass", "김", 123, 2, 123, new Date(), false);
        Product product1 = new Product("상품1", 123, 3,"ima_path", 2, 400);

        userRepository.save(user1);
        productRepository.save(product1);
    }

}
