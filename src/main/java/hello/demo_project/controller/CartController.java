package hello.demo_project.controller;

import hello.demo_project.domain.cart.CartDto;
import hello.demo_project.domain.cart.CartReq;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.exception.OutOfStockException;
import hello.demo_project.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    // 장바구니 자체 목록조회
    // 회원번호로 된 장바구니 확인 -> 확인된걸 리스트로 가져오기
    @GetMapping("/list/{userId}")
    public String getCartList(@PathVariable long userId, Model model) {
        List<CartDto> list = cartService.getCartsList(userId);
        model.addAttribute("list", list);
        return "cart/cartlist/{userId}";
    }

    // 장바구니에 물건 담기
    // 나눠보기
    // 필요한거 : 회원정보(회원이 만든 카드 찾기용), 물품정보
    // 장바구니에 담을 물건 체크
    //    1. 이미 있는 제품 -> 해당 제품이 있는 cart 불러와서 거기에 수량 1개 추가하기
    //    2. 모든 장바구니에 없는 제품(새로운 제품) -> 없던 제품이면 cart를 새로 만들어서 추카
    @PostMapping("/add/{productId}")
    public String addCart(@ModelAttribute CartReq cartReq) throws DataNotFoundException, OutOfStockException {
        cartService.addCart(cartReq);
        return "cart/cartlist/{userId}";
    }

    // 장바구니에서 담아둔 물품의 수량변경(update 개념인가? 수량만 변경하니까 맞는거같기도)

    // 장바구니에서 담아둔 물품 삭제(나머지는 유지)

    // 장바구니의 제품들의 종류 x 수량 해서 총 가격 합계 내기

}
