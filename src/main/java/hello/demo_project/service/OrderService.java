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

            Order order = new Order( // 나중에 오더 저장할때 저장해둘 틀 만들어 두기
                    orderId,
                    product.getProductId(),
                    product.getName(),
                    orderProduct.getQuantity(),
                    orderReq.getUserId(),
                    orderReq.getPostCode(),
                    orderReq.getAddress(),
                    orderReq.getAddressDetail(),
                    orderReq.getPhoneNumber(),
                    orderReq.getMessage(),
                    new Date(),
                    "paymentMethod",
                    "notPay",
                    "impUid"
            );

            orderRepository.save(order);
        }

        OrderPrepare orderPrepare = new OrderPrepare(
                orderId,
                orderReq.getUserId(),
                orderReq.getProductList(),
                totalPrice,
                totalPrice
        );
        //사용자의 주문 가격을 전부 더하고 그걸 전달
        return orderPrepare; //ToDo 만들어서 보내
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
        // ToDo 포트원을 통해 결제가 되고 (orderDetail(지금은 index))에서 넘어온 정보를 order 저장
        try {
            userRepository.getUserByMemberId(orderResult.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("user not found"));

            //오더 완성 + 저장
            List<Order> orderList = orderRepository.getOrdersByOrderId(orderResult.getOrderId());
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
                        orderResult.getPayStatus(),
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

    public OrderDto getOrder(String orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId);

        log.info("order : {}", order);

        OrderDto orderDto = new OrderDto(
                order.getOrderNumber(), order.getOrderId(), order.getProductId(), order.getProductName(),
                order.getProductQuantity(), order.getUserId(), order.getPostCode(), order.getAddress(),
                order.getAddressDetail(), order.getPhoneNumber(), order.getMessage(), order.getOrderAt(),
                order.getPaymentMethod(), order.getPayStatus(), order.getImpUid()
        );

        return  orderDto;
    }
}
