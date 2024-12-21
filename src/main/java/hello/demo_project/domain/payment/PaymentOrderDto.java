package hello.demo_project.domain.payment;

import lombok.Getter;

@Getter
public class PaymentDto {

    Long productId;
    String productName;
    int price;
    int quantity;
    String impUid;
    String merchantUid;

    public Payment toEntity() {
        return Payment.builder()
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .impUid(impUid)
                .merchantUid(merchantUid)
                .build();
    }
}
