package hello.demo_project.service;

import hello.demo_project.domain.order.Order;
import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductDto;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.review.Review;
import hello.demo_project.domain.review.ReviewDto;
import hello.demo_project.domain.review.ReviewRepository;
import hello.demo_project.domain.review.ReviewReq;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewDto getReview(long reviewId) throws DataNotFoundException {
        Review review = reviewRepository.getReviewByReviewId(reviewId)
                .orElseThrow(() -> new DataNotFoundException("review not found"));

        log.info("review : {}", review);

        return new ReviewDto(review.getReviewId(), review.getContent(), review.getRating(), review.getCreatedDate(),
                review.getGender(), review.getAge(), review.getType(), review.getImgPath(), review.getMemberId(),
                review.getProductId(), review.getTagId01(), review.getTagId02(), review.getTagId03(), review.isMonth());
    }

    public List<ReviewDto> getReviewList() {
        List<Review> reviews = reviewRepository.findAll();
        log.info("reviews : {}", reviews);
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewDto(review.getReviewId(), review.getContent(), review.getRating(), review.getCreatedDate(),
                    review.getGender(), review.getAge(), review.getType(), review.getImgPath(), review.getMemberId(),
                    review.getProductId(), review.getTagId01(), review.getTagId02(), review.getTagId03(), review.isMonth()));
        }

        return reviewDtos;
    }

    public void createReview(ReviewReq reviewReq) throws DataNotFoundException {
        userRepository.getUserByMemberId(reviewReq.getMemberId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));

        productRepository.getProductByProductId(reviewReq.getProductId())
                .orElseThrow(() -> new DataNotFoundException("product not found"));

        Review review = new Review(reviewReq.getContent(), reviewReq.getRating(), reviewReq.getGender(), reviewReq.getAge(),
                reviewReq.getType(), reviewReq.getImgPath(), reviewReq.getMemberId(), reviewReq.getProductId(),
                reviewReq.getTagId01(), reviewReq.getTagId02(), reviewReq.getTagId03(), reviewReq.isMonth());

        //여기서 order뒤져서 주문을 한적있는지 뒤져서 이게 재구매인지 확인해서 저장해야함 - 로직추가

        reviewRepository.save(review);
    }

    public void updateReview(long reviewId, ReviewReq reviewReq) {
        Review review = reviewRepository.findReviewByReviewId(reviewId);
        review.updateReview(reviewReq.getContent(), reviewReq.getRating(), reviewReq.getGender(), reviewReq.getAge(),
                reviewReq.getType(), reviewReq.getImgPath(), reviewReq.getMemberId(), reviewReq.getProductId(),
                reviewReq.getTagId01(), reviewReq.getTagId02(), reviewReq.getTagId03(), reviewReq.isMonth());
        reviewRepository.save(review);
    }

    public void deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    //이건 리뷰서비스에 있어야할거
    public SaveReviewFormDto getProduct(Long orderNumber) {
        Optional<Order> order = orderRepository.findById(orderNumber);
        Product product = productRepository.findById(order.get().getProduct().getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Option option = null;
        if(order.get().getOption() != null) {
            option = optionRepository.findById(order.get().getOption().getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("Option not found"));
        }

        Date now = new Date();

        // 한 달 이상 경과 여부 확인
        long diffInMillies = now.getTime() - order.get().getCreatedAt().getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        boolean isMonth = diffInDays >= 30;

        SaveReviewFormDto saveReviewFormDto = new SaveReviewFormDto(
                order.get().getUser().getUserId(),
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getBrand(),
                null,
                product.getProductType().getProdTypeId(),
                option != null ? option.getOptionId() : null,
                option != null ? option.getContent() : null,
                isMonth ? 'Y' : 'N'
        );

        // prodImage 설정
        saveReviewFormDto.setProdImageFromString(product.getImagePath());
        log.info("prodImage: {}",saveReviewFormDto.getProdImage());

        return saveReviewFormDto;
    }
}
