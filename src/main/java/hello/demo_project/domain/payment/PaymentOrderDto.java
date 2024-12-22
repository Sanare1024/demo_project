package hello.demo_project.domain.payment;

import lombok.Getter;

@Getter
public class PaymentOrderDto {

    Long productId;
    String productName;
    int price;
    int quantity;
    String impUid;
    String merchantUid;

    public PaymentOrder toEntity() {
        return PaymentOrder.builder()
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .impUid(impUid)
                .merchantUid(merchantUid)
                .build();
    }
}
