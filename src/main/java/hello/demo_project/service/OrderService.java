package hello.demo_project.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.cart.CartRepository;
import hello.demo_project.domain.order.*;
import hello.demo_project.domain.payHistory.PaymentHistory;
import hello.demo_project.domain.payHistory.PaymentHistoryRepository;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final IamportClient iamportClient;
    private final PaymentHistoryRepository paymentHistoryRepository;

    public OrderPrepare createOrder(OrderReq orderReq) throws DataNotFoundException, OutOfStockException {
        userRepository.getUserByMemberId(orderReq.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        String orderId = UUID.randomUUID().toString();
        orderReq.setOrderId(orderId); // UUID로 오더 id 넣어줌

        //주문 상품
        long totalPrice = 0;
        for (OrderProduct orderProduct : orderReq.getProductList()) {
            Product product = productRepository.getProductByProductId(orderProduct.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("product not found"));

            if (orderProduct.getQuantity() > product.getStock()){ //
                throw new OutOfStockException("물품 재고 부족");
            }

            totalPrice += product.getPrice() * orderProduct.getQuantity();

            Order order = new Order(
                    orderId,
                    product.getProductId(),
                    product.getName(),
                    orderProduct.getQuantity(),
                    orderReq.getUserId(),
                    orderProduct.getOptionId(),
                    0,
                    "address",
                    "addressDetail",
                    123,
                    "message",
                    new Date(),
                    "paymentMethod",
                    "주문접수",
                    "impUid",
                    'N'
            );

            orderRepository.save(order);
        }

        OrderPrepare orderPrepare = new OrderPrepare(
                orderId,
                orderReq.getUserId(),
                orderReq.getProductList(),
                totalPrice,
                totalPrice //finalPrice(임시로 토탈)
        );
        //사용자의 주문 가격을 전부 더하고 그걸 전달
        return orderPrepare;
    }

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

    @Transactional
    public PaymentHistory completeOrder(OrderResult orderResult) {
        //포트원을 통해 결제가 되고 (orderDetail(지금은 index))에서 넘어온 정보를 order 저장
        try {
            userRepository.getUserByMemberId(orderResult.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("user not found"));

            //오더 완성 + 저장
            List<Order> orderList = orderRepository.findOrdersByOrderId(orderResult.getOrderId());
            List<Long> productIds = new ArrayList<>();

            for (Order order : orderList) {
                //물품 재고 확인
                Product product = productRepository.getProductByProductId(order.getProductId())
                        .orElseThrow(() -> new DataNotFoundException("product not found"));

                if (order.getProductQuantity() > product.getStock()) { //
                    throw new OutOfStockException("물품 재고 부족");
                }
                product.buy(order.getProductQuantity());

                // 오더 남은부분 확정짓기
                order.completeOrder(
                        orderResult.getPostCode(),
                        orderResult.getAddress(),
                        orderResult.getAddressDetail(),
                        orderResult.getPhoneNumber(),
                        orderResult.getMessage(),
                        orderResult.getOrderAt(),
                        orderResult.getPaymentMethod(),
                        "결제완료",
                        orderResult.getImpUid());
                //장바구니 넘버 모으기
                productIds.add(order.getProductId());
            }
            orderRepository.saveAll(orderList);

            //결제내역 생성
            PaymentHistory paymentHistory = new PaymentHistory(
                    orderResult.getOrderId(),
                    orderResult.getTotalPrice(),
                    orderResult.getBankCode(),
                    orderResult.getBankName(),
                    orderResult.getOrderAt(),
                    orderResult.getPayStatus(),
                    orderResult.getUserPoint()
            );
            paymentHistoryRepository.save(paymentHistory);

            //장바구니 비우기
            cartRepository.deleteCartByProductIdInAndUserId(productIds, orderResult.getUserId());

            log.info("주문 정보가 성공적으로 저장되었습니다.");

            return paymentHistory;

        } catch (Exception e) {
            log.info(e.getMessage());
            cancelPayment(orderResult.getImpUid());
            log.info("주문 정보 저장에 실패했습니다.");
            return null;
        }
    }

    public OrderCompleted getOrder(String orderId) throws DataNotFoundException {
        Order order = orderRepository.findOrderByOrderId(orderId);
        List<Order> orderList = orderRepository.findOrdersByOrderId(orderId);
        log.info("orderList : {}", orderList);

        List<OrderProduct> productList = new ArrayList<>();
        for (Order orders : orderList) {
            Product product = productRepository.getProductByProductId(orders.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("product not found"));

            productList.add(new OrderProduct(orders.getProductId(), orders.getProductName(), product.getPrice(),
                    orders.getProductQuantity(), product.getImage_path(), orders.getOptionId()));
        }
        return  new OrderCompleted(orderId, "recipientName", "카카오 페이", order.getUserId(),productList);
    }
    // 여기까지 현준
}
