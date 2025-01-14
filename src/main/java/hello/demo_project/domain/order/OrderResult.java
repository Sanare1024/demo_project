package hello.demo_project.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResult {

    private String orderId; //주문번호
    private long userId; //회원키
    private long totalPrice; //총금액
    private long userPoint;//사용포인트
    //배송관련
    private long postCode; //우편번호
    private String address; //받는분 주소
    private String addressDetail; //주소 뒤 상세주소
    private long phoneNumber;
    private String message; //주문 요청사항
    private Date orderAt; //주문 날짜
    //결제관련
    private String paymentMethod; //결제수단
    private String bankCode;
    private String bankName;
    private String payStatus; //결제 상태
    private String impUid; //결제 아임포트 키
}
