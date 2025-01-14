package hello.demo_project.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getOrdersByOrderId(String orderId);

    Order findOrderByProductId(long productId);

    Order findOrderByOrderId(String orderId);
}
