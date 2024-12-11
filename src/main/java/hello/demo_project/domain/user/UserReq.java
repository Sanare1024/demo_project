package hello.demo_project.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class UserReq {
    @NotNull
    private String id; // 아이디
    @NotNull
    private String password; //비밀번호
    @NotNull
    private String name; //이름
    @NotNull
    private long phoneNumber; //휴대폰번호
    @NotNull
    private long role; //권한 (admin:1 User:2)
    @NotNull
    private long point; //포인트
    @NotNull
    private Date create_at; //가입날짜
    @NotNull
    private boolean is_deleted; //회원탈퇴여부
}
