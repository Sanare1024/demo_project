package hello.demo_project.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> getReviewByReviewId(long reviewId);

    Review findReviewByReviewId(long reviewId);
}
