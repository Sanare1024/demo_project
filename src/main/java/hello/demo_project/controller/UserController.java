package hello.demo_project.controller;

import hello.demo_project.domain.product.ProductReq;
import hello.demo_project.domain.user.UserDto;
import hello.demo_project.domain.user.UserReq;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    //회원 생성(회원가입)
    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("UserReq", new UserReq("id", "password", "name", 0,
                2, 0, new Date(), false));
        return "User/createForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserReq userReq) {
        userService.createUser(userReq);
        return "redirect:mainPage";
    }

    // 회원 마이프로필 개인정보 확인하는곳


    // 회원 개인정보수정
    @GetMapping("/update/{userId}")
    public String updateUser(@PathVariable long userId, Model model) throws DataNotFoundException {
        UserDto userDto = userService.getUser(userId);
        UserReq userReq = new UserReq(userDto.getId(), userDto.getPassword(), userDto.getName(), userDto.getPhoneNumber(),
                userDto.getRole(), userDto.getPoint(), userDto.getCreate_at(), userDto.is_deleted());
        return "User/updateForm";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable long userId, @ModelAttribute UserReq userReq) {
        userService.updateUser(userId, userReq);
        return "redirect:/user/{id}/detail";
    }

    // 회원 탈퇴
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return "redirect:/mainPage";
    }

    // 배송지 확인

    //

    //

    // 리뷰로 뺄 기능들
    // 리뷰 작성
    // 내가 작성한 리뷰 수정
    // 내가 작성한 리뷰들 보기

    // 주문내역 조회
}
