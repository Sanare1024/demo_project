package hello.demo_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
    //장바구니의 물건을 통하여 장바구니 내용물 전체 구매
    //내용물 일부만 선택한 선택구매
    //물품들에 해당하는 물품의 이벤트(1+1, 30% 할인 등) 내용의 적용



}
