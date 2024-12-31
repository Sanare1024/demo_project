package hello.demo_project.domain.orderHistory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderHistoryReq {

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

    public OrderHistory toEntity(){
        return OrderHistory.builder()
                .orderId(orderId)
                .productId(productId)
                .price(price)
                .quantity(quantity)
                .paymentMethodType(paymentMethodType)
                .bankCode(bankCode)
                .bankName(bankName)
                .paidAt(paidAt)
                .status(status)
                .build();
    }
}
