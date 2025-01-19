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
    private long orderNumber; //pk
    private String orderId; //고유주문코드
    private long productId; //상품키
    private String productName; // 상품명
    private long productQuantity; //상품 개수
    private long userId; //회원키
    private long optionId;//옵션키
    //배송관련
    private long postCode; //우편번호
    private String address; //받는분 주소
    private String addressDetail; //주소 뒤 상세주소
    private long phoneNumber;
    private String message; //주문 요청사항
    @CreatedDate
    private Date orderAt; //주문 날짜
    //결제관련
    private String paymentMethod; //결제수단
    private String orderStatus; //주문상태
    private String impUid; //결제 아임포트 키
    private char isReview;

    public Order(String orderId, long productId, String productName, long productQuantity, long userId, long optionId, long postCode, String address, String addressDetail, long phoneNumber, String message, Date orderAt, String paymentMethod, String orderStatus, String impUid, char isReview) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.userId = userId;
        this.optionId = optionId;
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.orderAt = orderAt;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.impUid = impUid;
        this.isReview = isReview;
    }

    public void completeOrder(long postCode, String address, String addressDetail, long phoneNumber, String message, Date orderAt, String paymentMethod, String payStatus, String impUid) {
        this.postCode = postCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.orderAt = orderAt;
        this.paymentMethod = paymentMethod;
        this.orderStatus = payStatus;
        this.impUid = impUid;
    }
}
