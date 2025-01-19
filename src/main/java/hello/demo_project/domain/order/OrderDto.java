package hello.demo_project.domain.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

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
    private String orderStatus; //결제 상태
    private String impUid; //결제 아임포트 키
    private char isReview;
}
