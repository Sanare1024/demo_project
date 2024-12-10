package hello.demo_project.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private long order_UserId; //주문한 회원키
//  private long franchiseId; //가맹점 번호
    private String order_userName; //주문자 성함
    private String productName; //상품이름
    private String payment_Method_type; //결제수단
    private long totalPrice; //총금액
    //배송관련
    private String gift_User_Name; //받는분 성함
    private long postCode; //우편번호
    private String address; //받는분 주소
    private String addressDetail; //주소 뒤 상세주소
    private String message; //주문 요청사항
    @Enumerated(EnumType.STRING)
    private Status status; //주문 상태
    private Date order_Created_Date; //주문 날짜
}
