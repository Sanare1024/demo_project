package hello.demo_project.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private long phoneNumber; //휴대폰번호
    private long role; //권한 (admin:1 User:2)
    private long point; //포인트
    private Date create_at; //가입날짜
    private boolean is_deleted; //회원탈퇴여부
}
