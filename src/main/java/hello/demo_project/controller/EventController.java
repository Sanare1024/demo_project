package hello.demo_project.controller;

import hello.demo_project.domain.event.EventDto;
import hello.demo_project.domain.event.EventReq;
import hello.demo_project.exception.DataNotFoundException;
import hello.demo_project.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
@Slf4j
public class EventController {

    private final EventService eventService;

    // 정렬기능을 프론트단에서 js로 하기
    // 대신 이벤트 리스트를
    // 1. 진행중인 이벤트 리스트  2. 종료된 이벤트 리스트
    // 로 나눠서 2번 보내주고 앞단에서 정렬해서 보여주기

    // 이벤트 추가
    @PostMapping("/add")
    public String addEvent(@RequestBody EventReq req) {
        eventService.addEvent(req);
        return "redirect:list";
    }

    // 이벤트 상세페이지
    @GetMapping("{boardId}")
    public String getEventDetailById(@PathVariable long boardId, Model model) throws DataNotFoundException {
        EventDto eventDto = eventService.getEventDetail(boardId);
        model.addAttribute("event", eventDto);
        return "event/eventDetail";
    }

    // 진행중인 이벤트 가져오기
    @GetMapping("/ongoingList")
    public String getListOngoingEvent(Model model) {
        List<EventDto> ongoingEventList = eventService.getOngoingEventList();
        model.addAttribute("ongoingList", ongoingEventList);
        return "event/eventList";
    }

    // 종료된 이벤트 가져오기
    @GetMapping("/endedList")
    public String getListEndedEvent(Model model) {
        List<EventDto> endedEventList = eventService.getEndedEventList();
        model.addAttribute("endedList", endedEventList);
        return "event/eventList";
    }

    // 이벤트 내용 수정
    @PostMapping("/update/{boardId}")
    public String updateEvent(@PathVariable long boardId, @ModelAttribute EventReq eventReq) {
        eventService.updateEvent(boardId, eventReq);
        return "redirect:/event/{boardId}";
    }

    // 이벤트 삭제(종료된게 쌓였을때 관리자가 주기적 사용)
    @GetMapping("/delete/{boardId}")
    public String deleteEvent(@PathVariable long boardId) {
        log.info("boarId : {}", boardId);
        eventService.deleteEvent(boardId);

        return "redirect:/event/list";
    }
}
