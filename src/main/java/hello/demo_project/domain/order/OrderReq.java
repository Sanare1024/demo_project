package hello.demo_project.domain.order;

import hello.demo_project.domain.product.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderReq {
    private String orderId;
    private long userId; //주문한 회원키
    private List<OrderProduct> productList;
}
