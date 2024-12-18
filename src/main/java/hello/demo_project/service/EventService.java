package hello.demo_project.service;

import hello.demo_project.domain.event.Event;
import hello.demo_project.domain.event.EventDto;
import hello.demo_project.domain.event.EventRepository;
import hello.demo_project.domain.event.EventReq;
import hello.demo_project.domain.product.ProductReq;
import hello.demo_project.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    //이벤트 추가
    public void addEvent(EventReq req) {
        Event event = new Event(req.getTitle(), req.getStartDate(), req.getEndDate(), req.getEventImagePath());
        eventRepository.save(event);
    }

    public EventDto getEventDetail(long boardId) throws DataNotFoundException {
        Event event = eventRepository.getEventByBoardId(boardId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        log.info("event : {}", event);
        return new EventDto(event.getBoardId(), event.getTitle(), event.getStartDate(), event.getEndDate(),
                event.getEventImagePath());
    }

    public List<EventDto> getOngoingEventList() {


        List<Event> ongoingEvents = eventRepository.findEventsByStartDateBeforeAndEndDateAfter(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        log.info(String.valueOf(new Timestamp(System.currentTimeMillis())));
        log.info(ongoingEvents.toString());

        //stream(뱅글뱅글 for문)  map(앞에 있는걸 -> 이걸로 변경해줘)
        List<EventDto> ongoingEventsDto = ongoingEvents.stream().map(event -> new EventDto(event.getBoardId(), event.getTitle(), event.getStartDate(),
                event.getEndDate(), event.getEventImagePath())).collect(Collectors.toList());

        //List<EventDto> ongoingEventsDto = new ArrayList<>();
        /*for (Event event : ongoingEvents) {
            ongoingEventsDto.add(new EventDto(event.getBoardId(), event.getTitle(), event.getStartDate(),
                    event.getEndDate(), event.getEventImagePath()));
        }*/


        log.info(ongoingEventsDto.toString());
        return ongoingEventsDto;

/*
        List<Event> ongoingEvents = eventRepository.findAll(); // 이렇게....찾아와도 괜찮나?
        List<EventDto> ongoingEventsDto = new ArrayList<>();
        for (Event event : ongoingEvents) {
            LocalDate eventDate = new java.sql.Date(event.getEndDate().getTime()).toLocalDate(); // 저장된 시간을 로컬 데이트로 변경
            if (LocalDate.now().isAfter(eventDate)) continue; // 지난 이벤트 continue

            ongoingEventsDto.add(new EventDto(event.getBoardId(), event.getTitle(), event.getStartDate(),
                    event.getEndDate(), event.getEventImagePath()));
        }
*/
    }

    public List<EventDto> getEndedEventList() {
        List<Event> endedEvents = eventRepository.findAll(); //상동

        List<EventDto> endedEventsDto = new ArrayList<>();
        for (Event event : endedEvents) {
            LocalDate eventDate = new java.sql.Date(event.getEndDate().getTime()).toLocalDate(); // 저장된 시간을 로컬 데이트로 변경
            if (LocalDate.now().isBefore(eventDate)) continue; // 아직 안지난 이벤트 continue

            endedEventsDto.add(new EventDto(event.getBoardId(), event.getTitle(), event.getStartDate(),
                    event.getEndDate(), event.getEventImagePath()));
        }
        return endedEventsDto;
    }


    public void updateEvent(long boardId, EventReq eventReq) {
        Event event = eventRepository.findEventByBoardId(boardId);
        event.updateEvent(eventReq.getTitle(), eventReq.getStartDate(), eventReq.getEndDate(), eventReq.getEventImagePath());
        eventRepository.save(event);
    }


    public void deleteEvent(long boardId) {
        eventRepository.deleteById(boardId);
    }
}
