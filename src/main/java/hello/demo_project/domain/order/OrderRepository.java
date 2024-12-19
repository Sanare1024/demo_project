package hello.demo_project.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByOrderUserIdAndKakaoOrderId(long userId, String kakaoOrderId);

}
