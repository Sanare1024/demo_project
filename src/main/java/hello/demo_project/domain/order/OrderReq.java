package hello.demo_project.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderReq {
    private long orderUserId; //주문한 회원키
    private long productId; //상품키
    private long productQuantity; // 상품 개수
}
