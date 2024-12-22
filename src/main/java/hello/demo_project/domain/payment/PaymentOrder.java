package hello.demo_project.domain.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paymentOrder")
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentOrderId;
    Long productId;
    String productName;
    int price;
    int quantity;
    String impUid;
    String merchantUid;
}
