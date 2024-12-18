package hello.demo_project.domain.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EventReq {
    @NotNull
    private String title;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private String eventImagePath;
}
