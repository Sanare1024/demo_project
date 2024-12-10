package hello.demo_project.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;


@AllArgsConstructor
@Data
public class ProductReq {
    @NotNull
    private String name;
    @NotNull
    private long price;
    @NotNull
    private long scope_Avg;
    @NotNull
    private String image_path;
    @NotNull
    private long product_TypeId;
    @NotNull
    private long stock;

}