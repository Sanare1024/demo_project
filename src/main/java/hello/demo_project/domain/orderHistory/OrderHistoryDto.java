package hello.demo_project.domain.orderHistory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderHistoryDto {
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
    private String impUid;
}
