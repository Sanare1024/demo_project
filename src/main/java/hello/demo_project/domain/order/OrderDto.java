package hello.demo_project.domain.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private long orderId;
    private long orderUserId;
    private long productId;
//  private String productName;
    private long productQuantity;
    //배송관련
    private String gift_User_Name;
    private long postCode;
    private String address;
    private String addressDetail;
    private String message;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date order_Created_Date;
    private String paymentMethod;
}
