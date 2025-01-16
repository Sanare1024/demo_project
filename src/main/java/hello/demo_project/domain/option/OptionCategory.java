package hello.demo_project.domain.option;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "optionCategory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionCategoryId")
    private Long optionCategoryId;

    private String name;

    @OneToMany(mappedBy = "optionCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();
}
