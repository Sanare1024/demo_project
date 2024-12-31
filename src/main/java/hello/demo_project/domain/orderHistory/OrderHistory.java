package hello.demo_project.domain.orderHistory;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payId;
    private long orderId;
    private long productId;
    private long price;
    private long quantity;
    private String paymentMethodType;
    private String bankCode;
    private String bankName;
    private Date paidAt;
    private String status; //결제상태
    private boolean isReview;
    private long usedPoint;
    private long couponId;
    private boolean isGift;
    private String impUid; // 아임포트 uid

    public OrderHistory(long orderId, long productId, long price, long quantity, String paymentMethodType, String bankCode, String bankName, Date paidAt, String status, boolean isReview, long usedPoint, long couponId, boolean isGift, String impUid) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.paymentMethodType = paymentMethodType;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.paidAt = paidAt;
        this.status = status;
        this.isReview = isReview;
        this.usedPoint = usedPoint;
        this.couponId = couponId;
        this.isGift = isGift;
        this.impUid = impUid;
    }
}
