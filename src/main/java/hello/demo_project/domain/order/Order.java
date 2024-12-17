package hello.demo_project.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "TB_ORDER")
@NoArgsConstructor
@Getter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId; //주문번호키
    private long orderUserId; //주문한 회원키
    private long productId; //상품키
    //  private String productName; //상품이름 - DB에 저장하면서까지 가져가야하는것인가? 내 생각에 아님 지워도 무방함
    private long productQuantity; //상품 개수

    private String kakaoOrderId;
    @CreatedDate
    private Date order_Created_Date; //주문 날짜
    private String paymentMethod; //결제수단
    //배송관련
    private String gift_User_Name; //받는분 성함
    private long postCode; //우편번호
    private String address; //받는분 주소
    private String addressDetail; //주소 뒤 상세주소
    private String message; //주문 요청사항
    @Enumerated(EnumType.STRING)
    private Status status; //배송 상태

    public Order(long orderUserId, long productId, long productQuantity) {
        this.orderUserId = orderUserId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Order(long orderUserId, long productId, long productQuantity, String kakaoOrderId, long postCode, String address, String addressDetail, String message, String paymentMethod) {
        this.orderUserId = orderUserId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.kakaoOrderId = kakaoOrderId;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.message = message;
        this.paymentMethod = paymentMethod;
    }
}
