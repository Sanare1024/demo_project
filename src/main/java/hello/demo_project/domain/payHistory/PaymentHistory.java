package hello.demo_project.domain.paymentHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class PaymentHistory {

    private long paymentId;


}
