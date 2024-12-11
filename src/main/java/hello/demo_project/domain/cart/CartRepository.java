package hello.demo_project.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long>{
    List<Cart> getCartsByUserId(long userId);

    Optional<Cart> getCartByCartId(long cartId); // Optional은 있는지 없는지 모를때 사용

    Cart findCartByCartId(long cartId);

    Optional<Cart> findCartByUserIdAndProductId(long userId, long productId); //카트의 원소단위가 아니라
}
