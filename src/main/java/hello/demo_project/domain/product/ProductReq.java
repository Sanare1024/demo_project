package hello.demo_project.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;


@AllArgsConstructor
@Data
public class ProductReq {
    @NotNull
    private String name;

}