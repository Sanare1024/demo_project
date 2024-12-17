package hello.demo_project.domain.order;

import hello.demo_project.domain.product.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class OrderReq {

    private long userId; //주문한 회원키
    private List<OrderProduct> productList;
    //배송관련
    private long postCode; //우편번호
    private String address; //받는분 주소
    private String addressDetail; //주소 뒤 상세주소
    private String message; //주문 요청사항
}
