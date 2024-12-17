package hello.demo_project.service;

import hello.demo_project.domain.user.User;
import hello.demo_project.domain.user.UserDto;
import hello.demo_project.domain.user.UserRepository;
import hello.demo_project.domain.user.UserReq;
import hello.demo_project.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserReq userReq) {
        User user = new User(userReq.getId(), userReq.getPassword(), userReq.getName(), userReq.getPhoneNumber(),
                userReq.getRole(), userReq.getPoint(), userReq.getCreate_at(), userReq.is_deleted());
        userRepository.save(user);
    }

    public UserDto getUser(long memberId) throws DataNotFoundException {
        User user = userRepository.getUserByMemberId(memberId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        log.info("User : {}", user);

        return new UserDto(user.getMemberId(), user.getId(), user.getPassword(), user.getName(), user.getPhoneNumber(),
                user.getRole(), user.getPoint(), user.getCreate_at(), user.is_deleted());
    }


    public void updateUser(long memberId, UserReq userReq) {
        User user = userRepository.findUserByMemberId(memberId);
        user.updateUser(userReq.getId(), userReq.getPassword(), userReq.getName(), userReq.getPhoneNumber(), userReq.getRole(),
                 userReq.getPoint(), userReq.getCreate_at(), userReq.is_deleted());
        userRepository.save(user);
    }

    public void deleteUser(long memberId) {
        User user = userRepository.findUserByMemberId(memberId);
        user.updateUser(user.getId(), user.getPassword(), user.getName(), user.getPhoneNumber(),
                user.getRole(), user.getPoint(), user.getCreate_at(), false);
        userRepository.save(user);
    }
}
