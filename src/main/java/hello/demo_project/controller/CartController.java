package hello.demo_project.controller;

import hello.demo_project.domain.cart.CartDto;
import hello.demo_project.domain.cart.CartRepository;
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
    // 이미 존재하는 장바구니의 물품 에서 위버튼(+1), 아래버튼(-1) 눌러서 수량을 조절하고 변경버튼을 누르면 실행
    // 기존 장바구니쓰니까 장바구니 번호로 확인
    // 줄이거나 늘릴때 product 의 Stock 이 가능한지 확인해야함 -> 부족하면 Out of stock
    @PostMapping("/update/{cartId}")
    public String updateStock(@PathVariable long cartId, @ModelAttribute CartReq cartReq) throws OutOfStockException, DataNotFoundException {
        cartService.updateQuantity(cartId, cartReq);
        return "cart/cartlist/{userId}";
    }

    // 장바구니에서 담아둔 물품 선택 삭제(나머지는 유지)
    // 선택물품이 여러개면.....?
    // 체크체크 각 카트별로 명령이 따로 넘어오나...?
    // 밑의 방법으로 해도 괜찮나....? 일단 지르고 혼나자
    @GetMapping("/delete/{cartIds}")
    public String deleteSelectedCart(@PathVariable List<Long> cartIds) {
        for (Long cartId : cartIds) {
            cartService.deleteSelectedCart(cartId);
        }
        return "cart/cartlist/{userId}";
    }

    // 장바구니 전부 비우기(전체 삭제)
    // 유저아이디로 존재하는 모든 카트 지워버리면 될듯
    @GetMapping("/deleteAll/{userId}")
    public String deleteAllCart(@PathVariable long userId) {
        cartService.deleteAllCart(userId);
        return "cart/cartlist/{userId}";
    }

    // 장바구니의 제품들의 종류 x 수량 해서 총 가격 합계 내기
    // dataNotFound 가 들어가는게 맞나? 존재 확실한 물품들 + 카트들의 합계를 내는건데..?
    // 그리고 이 계산이 내 생각에는 update 나 새로 추가 할때마다 무조건 발동해서 실시간으로 합계가 바뀌는걸려고 하는데
    // 그럼 어떻게 해줘야 위에 상황에서 항상 실행되지?
    @GetMapping("/calculateSum/{userId}}")
    public String calculateSum(@PathVariable long userId, Model model) throws DataNotFoundException {
        long sum = cartService.calculateSum(userId);
        model.addAttribute("Sum", sum);
        return "cart/cartlist/{userId}";
    }

}
