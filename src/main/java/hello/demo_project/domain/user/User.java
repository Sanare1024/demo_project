package hello.demo_project.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Table(name = "TB_USER")
@Entity
@NoArgsConstructor
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId; //회원키
    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private long phoneNumber; //휴대폰번호
    private long role; //권한 (admin:1 User:2)
    private long point; //포인트
    private Date create_at; //가입날짜
    private boolean is_deleted; //회원탈퇴여부
}
