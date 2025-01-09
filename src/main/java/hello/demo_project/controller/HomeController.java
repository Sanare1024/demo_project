package hello.demo_project.controller;

import hello.demo_project.domain.order.OrderReq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        OrderReq req = new OrderReq();
        req.setProductList(new ArrayList<>());
        model.addAttribute("OrderReq",req);
        return "index";
    }
}
