package hello.demo_project.domain.payHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class PaymentHistoryDto {

    private long paymentId;
    private String orderId;
    private long totalPrice;
    private String bankCode;
    private String bankName;
    private Date orderAt;
    private String payStatus;
    private long usedPoint;
//  private boolean isGift;
}
