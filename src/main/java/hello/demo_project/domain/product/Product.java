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
    private String ingredient; //성분
    private String brand; //브랜드
    private String info; //제품 상세설명
    private long scope_Avg; //평균별점
    private String image_path; //이미지
    private long product_TypeId; //카테고리키
    private long stock; //재고

    //생성자 뭐뭐 넣어야 할지 고민중 ERD 정리 + 기능 확정 때까지 고민
    public Product(String name, long price, String ingredient, String brand, String info, long scope_Avg, String image_path, long product_TypeId, long stock) {
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
        this.brand = brand;
        this.info = info;
        this.scope_Avg = scope_Avg;
        this.image_path = image_path;
        this.product_TypeId = product_TypeId;
        this.stock = stock;
    }


}
