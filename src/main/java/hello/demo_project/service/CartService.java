package hello.demo_project.service;

import hello.demo_project.domain.cart.Cart;
import hello.demo_project.domain.cart.CartDto;
import hello.demo_project.domain.cart.CartRepository;
import hello.demo_project.domain.cart.CartReq;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.user.User;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<CartDto> getCartsList(long userId) {
        List<Cart> cartList = cartRepository.getCartsByUserId(userId);
        log.info("carts : {}", cartList);
        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : cartList) {
            cartDtos.add(new CartDto(cart.getCartId(), cart.getUserId(), cart.getProductId(), cart.getQuantity(),
                    cart.getLast_Modified_Date(), cart.is_deleted()));
        }

        return cartDtos;
    }

    public void addCart(CartReq cartReq) throws DataNotFoundException, OutOfStockException {
        // 카트의 유저
        User user = userRepository.getUserByUserId(cartReq.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        log.info("user : {}", user);

        // 카트에 담을 상품
        Product product = productRepository.getProductByProductId(cartReq.getProductId())
                .orElseThrow(() -> new DataNotFoundException("product not found"));
        log.info("product : {}", product);
        //상품 재고 부족 확인
        if (product.getStock() == 0) {
            throw new OutOfStockException("상품 매진");
        }

        //맴버아이디랑 프로덕트 아이디로 카트 있는지 없는지 확인하기(확인을 어캐......하지?)
        Optional<Cart> currentCartProduct = cartRepository.findCartByUserIdAndProductId(user.getMemberId(), product.getProductId());
        // 있는건지 없는건지 확인을 해서 로직 나누기
        //orElse = 없으면 이걸로 만들어줘 없으면 get이랑 똑같이 작동
        Cart cart = currentCartProduct.orElse(new Cart(cartReq.getUserId(), cartReq.getProductId(), 0, false));
        // 추가한 수량 + 원래 있던수량(새로만든경우 0)
        cart.setQuantity(cart.getQuantity() + cartReq.getQuantity());
        cartRepository.save(cart);

        //람다 없이 쓰는법
//        if (currentCartProduct.isPresent()) { // isPresent = 존재하면
//            Cart cart = currentCartProduct.get(); //
//            cart.setQuantity(cartReq.getQuantity() + cart.getQuantity()); //받아온 수량 + 원래 있던 수량
//            cartRepository.save(cart);
//
//
//        } else { //요건 else니까 존재하지않으면
//            Cart cart = new Cart(cartReq.getUserId(), cartReq.getProductId(), cartReq.getQuantity(), false);
//            cartRepository.save(cart);
//        }
    }

    public void updateQuantity(long cartId, CartReq cartReq) throws OutOfStockException, DataNotFoundException {
        //상품
        Product product = productRepository.getProductByProductId(cartReq.getProductId())
                .orElseThrow(() -> new DataNotFoundException("product not found"));
        log.info("product : {}", product);
        //상품 재고 부족 확인
        if (product.getStock() == 0) {
            throw new OutOfStockException("상품 매진");
        }

        //넘어온 카드번호로 카트 가져오기
        Cart cart = cartRepository.findCartByCartId(cartId);
        //가져온 카트
        if (product.getStock() - cartReq.getQuantity() > 0) { // 제품의 재고 - 변경된 수량 > 0 일때 (팔 수 있을때)
            cart.updateCartQuantity(cartReq.getQuantity()); // 수량 바꿔주고
            cartRepository.save(cart); // 저장
        } else { //재고 - 수량 <= 0 이면 못팜
            throw new OutOfStockException("재고가 부족합니다");
        }
    }

    // 선택삭제
    public void deleteSelectedCart(long cartId) {
        cartRepository.deleteById(cartId);
    }

    //전체삭제
    public void deleteAllCart(long userId) {
        // 유저아이디로 가지고 있는 카트 전부확인
        // 리스트로 가져오기
        List<Cart> cartList = cartRepository.getCartsByUserId(userId);
        //전부 삭제
        for (Cart cart : cartList) {
            cartRepository.deleteById(cart.getCartId());
        }
    }

    //전체금액 계산
    public long calculateSum(long userId) throws DataNotFoundException {
        // 아이디로 카트 리스트 불러오기
        List<Cart> cartList = cartRepository.getCartsByUserId(userId);
        long sum = 0;

        for (Cart cart : cartList) {
            Product product = productRepository.getProductByProductId(cart.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("product not found"));

            sum += (cart.getQuantity() * product.getPrice());
        }

        return sum;
    }
}
