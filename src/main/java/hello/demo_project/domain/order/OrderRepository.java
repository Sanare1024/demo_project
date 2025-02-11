package hello.demo_project.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByOrderId(String orderId);

    List<Order> findOrdersByUserId(long userId);

    List<Order> findOrdersByOrderStatus(String orderStatus);

    Order findOrderByProductId(long productId);

    Order findOrderByOrderId(String orderId);
}
