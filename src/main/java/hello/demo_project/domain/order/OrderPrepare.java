package hello.demo_project.domain.order;

import hello.demo_project.domain.product.OrderProduct;
import hello.demo_project.domain.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPrepare {
    private String orderId;
    private long userId; //주문한 회원키
    private List<OrderProduct> orderProductList;
    private long totalPrice;
    private long finalPrice;
    //배송관련
}