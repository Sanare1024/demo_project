package hello.demo_project.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class CartDto {
    private long cartId;
    private long userId;
    private long productId;
    private long quantity;
    private Date last_Modified_Date;
    private boolean is_deleted;
}
