package hello.demo_project.service;

import hello.demo_project.domain.cart.CartRepository;
import hello.demo_project.domain.order.Order;
import hello.demo_project.domain.order.OrderRepository;
import hello.demo_project.domain.order.OrderReq;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.user.User;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public void createOrder(OrderReq orderReq) throws DataNotFoundException, OutOfStockException {
        /*//주문의 유저
        User user = userRepository.getUserByUserId(orderReq.getOrderUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        log.info("user : {}", user); */

        //주문 상품
        Product product = productRepository.getProductByProductId(orderReq.getProductId())
                .orElseThrow(() -> new DataNotFoundException("product not found"));

        if (orderReq.getProductQuantity() > product.getStock()){ //
            throw new OutOfStockException("물품 재고 부족");
        }

        Order order = new Order(orderReq.getOrderUserId(), orderReq.getProductId(), orderReq.getProductQuantity());

        orderRepository.save(order);
    }

    public void buySelectedProduct(Long productsId, long userId) {

    }


}
