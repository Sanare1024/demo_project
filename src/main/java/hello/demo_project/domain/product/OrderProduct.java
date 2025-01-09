package hello.demo_project.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    private long productId;
    private String name;
    private long price;
    private long quantity;
    private String imagePath;
}
