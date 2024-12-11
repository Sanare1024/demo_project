package hello.demo_project.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

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
    private long quantity; //상품개수
    @LastModifiedDate
    private Date last_Modified_Date; //마지막 수정일
    private boolean is_deleted; //삭제여부...?

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setLast_Modified_Date(Date last_Modified_Date) {
        this.last_Modified_Date = last_Modified_Date;
    }

    public Cart(long userId, long productId, long quantity, boolean is_deleted) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.is_deleted = is_deleted;
    }
}
