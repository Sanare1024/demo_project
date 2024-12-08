package hello.demo_project.controller;

import hello.demo_project.domain.product.ProductReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    //상품 하나 id로 찾기
    @GetMapping("{productId}")
    public String getProductById(@PathVariable long productId, Model model) {

        return "product/productDetail";
    }

    //상품 여러개 리스트로 찾기
    @GetMapping("/list")
    public String getProductsList(Model model){

        return "product/productList";
    }

    //상품 추가하기 - 추가폼으로 보내기
    @GetMapping("/add")
    public String addProduct(Model model) {

        return "product/addFrom";
    }
    //상품 추가 - 폼에서 받은 요청 실제 저장
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductReq req) {

        return "redirect:list";
    }

    //상품 수정(update)

    //상품 삭제(delete)

}
