package hello.demo_project.controller;

import hello.demo_project.domain.review.ReviewDto;
import hello.demo_project.domain.review.ReviewReq;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("{reviewId}")
    public String getReviewById(@PathVariable long reviewId, Model model) throws DataNotFoundException {
        ReviewDto reviewDto = reviewService.getReview(reviewId);
        model.addAttribute("review", reviewDto);

        return "review/reviewDetail";
    }

    @GetMapping("/list")
    public String getReviewList(Model model){
        List<ReviewDto> reviewList = reviewService.getReviewList();
        model.addAttribute("list", reviewList);
        return "review/reviewList";
    }

    @GetMapping("/add")
    public String createReview(Model model) {
        model.addAttribute("reviewReq", new ReviewReq("", 0, "", 0, "", "",
                0, 0, 101, 102, 103, false));
        return "review/writeReview";
    }

    @PostMapping("/add")
    public String createReview(@ModelAttribute ReviewReq req) throws DataNotFoundException {
        reviewService.createReview(req);
        return "redirect:list";
    }


    @GetMapping("/update/{reviewId}")
    public String updateReview(@PathVariable long reviewId, Model model) throws DataNotFoundException {
        ReviewDto reviewDto = reviewService.getReview(reviewId);
        ReviewReq reviewReq = new ReviewReq(reviewDto.getContent(), reviewDto.getRating(), reviewDto. getGender(), reviewDto.getAge(),
                reviewDto.getType(), reviewDto.getImgPath(), reviewDto.getMemberId(), reviewDto.getProductId(), reviewDto.getTagId01(),
                reviewDto.getTagId02(), reviewDto.getTagId03(), reviewDto.isMonth());

        model.addAttribute("reviewReq", reviewReq);

        return "review/updateReview";
    }

    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable long reviewId, @ModelAttribute ReviewReq req) {
        reviewService.updateReview(reviewId, req);
        return "redirect:{reviewId}";
    }

    @GetMapping("/delete/{reviewId}") //D
    public String deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:list";
    }
}
