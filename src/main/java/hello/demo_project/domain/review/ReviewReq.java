package hello.demo_project.domain.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ReviewReq {

    private String content;
    private long rating;
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
