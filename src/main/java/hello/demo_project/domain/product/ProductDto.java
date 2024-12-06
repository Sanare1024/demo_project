package hello.demo_project.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String name;
    private long price;
    private String ingredient;
    private String brand;
    private String info;
    private long scope_Avg;
    private String image_path;
    private long product_TypeId;
    private long stock;
}
