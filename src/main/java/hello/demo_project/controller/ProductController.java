package hello.demo_project.controller;

import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductDto;
import hello.demo_project.domain.product.ProductReq;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    //상품 하나 id로 찾기
    @GetMapping("{productId}")
    public String getProductById(@PathVariable long productId, Model model) throws DataNotFoundException {
        ProductDto productDto = productService.getProduct(productId);
        model.addAttribute("product", productDto);
        return "product/productDetail";
    }

    //상품 여러개 리스트로 찾기
    @GetMapping("/list")
    public String getProductsList(Model model){
        List<ProductDto> productList = productService.getProductList();
        model.addAttribute("list", productList);
        return "product/productList";
    }

    //상품 추가하기 - 추가폼으로 보내기
    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("productReq", new ProductReq("", 0, 0, "", 0, 0));
        return "product/addFrom";
    }
    //상품 추가 - 폼에서 받은 요청 실제 저장
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductReq req) {
        productService.addProduct(req);
        return "redirect:list";
    }

    //상품 수정(update)
    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable long productId, Model model) throws DataNotFoundException {
        ProductDto productDto = productService.getProduct(productId);
        ProductReq productReq = new ProductReq(productDto.getName(), productDto.getPrice(), productDto.getScope_Avg(), productDto. getImage_path()
                , productDto.getProduct_TypeId(), productDto.getStock());
        model.addAttribute("productReq", productReq);

        return "product/updateForm";
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable long productId, @ModelAttribute ProductReq req) {
        productService.updateProduct(productId, req);
        return "redirect:/product/{productId}";
    }

    //상품 삭제(delete)
    @GetMapping("/delete/{productId}") //D
    public String deleteProduct(@PathVariable long productId) {
        log.info("productId ={}",productId);
        productService.deleteProduct(productId);
        return "redirect:/product/list";
    }

    //상품의 리뷰를 확인하고 평균 별점 계산
    //

}
