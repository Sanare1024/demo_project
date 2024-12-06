package hello.demo_project.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId; //장바구니키
    private long userId; //유저키
    private long productId; //상품키
    private long option_Detail_Id; //상품의 상세옵션값
    private long quantity; //상품개수
    private Date last_Modified_Date; //마지막 수정일
    private boolean is_deleted; //삭제여부
}
