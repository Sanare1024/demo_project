package hello.demo_project.domain.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;
    private String content;
    private long rating;
    private Date createdDate;
    private String gender;
    private long age;
    private String type;
    private String imgPath;
    private long memberId;
    private long productId;
//  private long optionId;
    private long tagId01;
    private long tagId02;
    private long tagId03;
    private boolean isMonth;

    public Review(String content, long rating, String gender, long age, String type, String imgPath, long memberId, long productId, long tagId01, long tagId02, long tagId03, boolean isMonth) {
        this.content = content;
        this.rating = rating;
        this.gender = gender;
        this.age = age;
        this.type = type;
        this.imgPath = imgPath;
        this.memberId = memberId;
        this.productId = productId;
        this.tagId01 = tagId01;
        this.tagId02 = tagId02;
        this.tagId03 = tagId03;
        this.isMonth = isMonth;
    }

    public void updateReview(String content, long rating, String gender, long age, String type, String imgPath, long memberId, long productId, long tagId01, long tagId02, long tagId03, boolean isMonth) {
        this.content = content;
        this.rating = rating;
        this.gender = gender;
        this.age = age;
        this.type = type;
        this.imgPath = imgPath;
        this.memberId = memberId;
        this.productId = productId;
        this.tagId01 = tagId01;
        this.tagId02 = tagId02;
        this.tagId03 = tagId03;
        this.isMonth = isMonth;
    }


}
