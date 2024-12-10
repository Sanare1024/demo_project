package hello.demo_project.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId; //상품번호키
    private String name; //상품이름
    private long price; //가격
    private long scope_Avg; //평균별점 이건 해보고싶어서 남김
    private String image_path; //이미지 이것도 어캐할지 배우고싶어서 남김
    private long product_TypeId; //카테고리키
    private long stock; //재고


    public Product(String name, long price, long scope_Avg, String image_Path, long product_TypeId, long stock) {
        this.name = name;
        this.price = price;
        this.scope_Avg = scope_Avg;
        this.image_path = image_Path;
        this.product_TypeId = product_TypeId;
        this.stock = stock;
    }
}
