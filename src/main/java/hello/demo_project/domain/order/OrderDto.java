package hello.demo_project.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private long order_UserId;
    //  private long franchiseId;
    private String order_userName;
    private String productName;
    private String payment_Method_type;
    private long totalPrice;
    //배송관련
    private String gift_User_Name;
    private long postCode;
    private String address;
    private String addressDetail;
    private String message;
    private String status;
    private Date order_Created_Date;
}
