package hello.demo_project.domain.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {

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

}
