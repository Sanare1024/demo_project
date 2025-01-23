package hello.demo_project.domain.payHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    Optional<PaymentHistory> getPaymentHistoryByPaymentId(long PaymentId);

    PaymentHistory findPaymentHistoriesByOrderId(String orderId);
}
