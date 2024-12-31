package hello.demo_project.service;

import hello.demo_project.connection.KakaoApi;
import hello.demo_project.domain.cart.CartRepository;
import hello.demo_project.domain.order.Order;
import hello.demo_project.domain.order.OrderRepository;
import hello.demo_project.domain.order.OrderReq;
import hello.demo_project.domain.product.OrderProduct;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final KakaoApi kakaoApi;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    /***
     *     카카오 API의 동작 순서
     *     사용자 주문 요청 -> KJUN 서버 -> 주문 가격 계산
     */

    @Transactional
    public String createOrder(OrderReq orderReq) throws DataNotFoundException, OutOfStockException {
        userRepository.getUserByMemberId(orderReq.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        //주문 상품
        long price = 0;
        for (OrderProduct orderProduct : orderReq.getProductList()) {
            Product product = productRepository.getProductByProductId(orderProduct.getId())
                    .orElseThrow(() -> new DataNotFoundException("product not found"));
            price += product.getPrice() * orderProduct.getQuantity();
            if (orderProduct.getQuantity() > product.getStock()){ //
                throw new OutOfStockException("물품 재고 부족");
            }
        }

        //사용자의 주문 가격을 전부 더하고 카카오에게 전달하시오
        return kakaoApi.payStart(price); //주문번호를 돌려줄거임 ㅋㅋ
    }

    /***
     *      (LOOP)
     *      사용자 웹페이지 (카카오 결제 했는지 확인) -> KJUN 서버
     *      (LOOP)
     */
    public String confirmOrder(String kakaoOrder_Id) {
        return kakaoApi.payConfirm(kakaoOrder_Id).getBody();
    }

    //요구사항
    //사용자의 니즈
    //사용자의 생각 또는 개발자의 생각을 코드로 풀어내는것
    //    사용자 웹페이지 -> Success
    @Transactional
    public void completeOrder(OrderReq orderReq, String kakaoOrder_Id) throws DataNotFoundException, OutOfStockException {
        userRepository.getUserByMemberId(orderReq.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        String orderId = UUID.randomUUID().toString();

        for (OrderProduct orderProduct : orderReq.getProductList()) {
            Product product = productRepository.getProductByProductId(orderProduct.getId())
                    .orElseThrow(() -> new DataNotFoundException("product not found"));

            if (orderProduct.getQuantity() > product.getStock()) { //
                throw new OutOfStockException("물품 재고 부족");
            }

            Order order = new Order(
                    orderId,
                    orderReq.getUserId(),
                    orderProduct.getId(),
                    orderProduct.getQuantity(),
                    kakaoOrder_Id,
                    orderReq.getPostCode(),
                    orderReq.getAddress(),
                    orderReq.getAddressDetail(),
                    orderReq.getMessage(),
                    "KAKAO"
            );

            orderRepository.save(order);
            product.buy(orderProduct.getQuantity());
            productRepository.save(product);
        }
        List<Long> productIds = new ArrayList<>();
        for (OrderProduct o : orderReq.getProductList()) {
            productIds.add(o.getId());
        }

        cartRepository.deleteCartByProductIdInAndUserId(productIds, orderReq.getUserId()); // 장바구니 초기화

        kakaoApi.payComplete(); //결제 끝
    }


}
