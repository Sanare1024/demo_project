package hello.demo_project.domain.orderHistory;


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
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payId;
    private long orderId;
    private long price;
    private long totalPrice;
    private String paymentMethodType;
    private String bankCode;
    private String bankName;
    private Date paidAt;
    private String status; //상태?
    private boolean isReview;
    private long userPoint;
    private boolean isGift;
}
