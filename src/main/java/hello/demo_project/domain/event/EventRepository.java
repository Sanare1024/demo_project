package hello.demo_project.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> getEventByBoardId(long boardId);

    Event findEventByBoardId(long boardId);

    List<Event> findEventsByStartDateBeforeAndEndDateAfter(Timestamp nowDate, Timestamp nowDate2);

}
