package hello.demo_project.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import hello.demo_project.domain.cart.CartRepository;
import hello.demo_project.domain.order.Order;
import hello.demo_project.domain.order.OrderRepository;
import hello.demo_project.domain.order.OrderReq;
import hello.demo_project.domain.order.OrderResult;
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

    public long createOrder(OrderReq orderReq) throws DataNotFoundException, OutOfStockException {
        userRepository.getUserByMemberId(orderReq.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        String orderId = UUID.randomUUID().toString();
        orderReq.setOrderId(orderId); // UUID로 오더 id 넣어줌

        //주문 상품
        long totalPrice = 0;
        for (OrderProduct orderProduct : orderReq.getProductList()) {
            Product product = productRepository.getProductByProductId(orderProduct.getId())
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
                    "payStatus",
                    "impUid"
            );

            orderRepository.save(order);
        }
        //사용자의 주문 가격을 전부 더하고 그걸 전달
        return totalPrice;
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
    public String completeOrder(OrderResult orderResult) {
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
                order.completeOrder(orderResult.getOrderAt(), orderResult.getPaymentMethod(), orderResult.getPayStatus()
                , orderResult.getImpUid());


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

            return "주문 정보가 성공적으로 저장되었습니다.";
        } catch (Exception e) {
            log.info(e.getMessage());
            cancelPayment(orderResult.getImpUid());
            // 실패했을때 이것만 하고 끝인가?
            // 주문을 다시 이어나가려면 어떻게 해야할까
            // 아닌가 cancelPayment를 하니까 창은 안바뀔거고 다시 주문하기 창에서 주문결제하기 버튼을 누르면 되는건가
            return "주문 정보 저장에 실패했습니다.";
        }
    }
}
