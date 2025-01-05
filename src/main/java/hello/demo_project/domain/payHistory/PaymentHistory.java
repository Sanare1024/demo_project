package hello.demo_project.domain.payHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;
    private String orderId;
    private long totalPrice;
    private String bankCode;
    private String bankName;
    private Date orderAt;
    private String payStatus;
    private long usedPoint;
//  private boolean isGift;

    public PaymentHistory(String orderId, long totalPrice, String bankCode, String bankName, Date orderAt, String payStatus, long usedPoint) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.orderAt = orderAt;
        this.payStatus = payStatus;
        this.usedPoint = usedPoint;
    }
}
