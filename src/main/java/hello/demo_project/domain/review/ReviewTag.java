package hello.demo_project.domain.review;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table
@NoArgsConstructor
@Getter
@ToString
public class ReviewTag {
    @Id
    private long tagId;
    private String tagName;
    private long productTypeId;
    private long parentId;
}
