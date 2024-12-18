package hello.demo_project.domain.event;

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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String eventImagePath;

    public Event(String title, Date startDate, Date endDate, String eventImagePath) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventImagePath = eventImagePath;
    }

    public void updateEvent(String title, Date startDate, Date endDate, String eventImagePath) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventImagePath = eventImagePath;
    }
}
