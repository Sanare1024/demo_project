package hello.demo_project.domain.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class CartReq {
    @NotNull
    private long userId;
    private long productId;
    private long quantity;
}
