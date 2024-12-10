package hello.demo_project.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto { //프러덕트랑 일치함 /
    private long productId;
    private String name;
    private long price;
    private long scope_Avg;
    private String image_path;
    private long product_TypeId;
    private long stock;
}
