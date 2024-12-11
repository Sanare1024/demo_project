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
    private long memberId; //회원고유키
    private String id; // 아이디
    private String password; //비밀번호
    private String name; //이름
    private long phoneNumber; //휴대폰번호
    private long role; //권한 (admin:1 User:2)
    private long point; //포인트
    private Date create_at; //가입날짜
    private boolean is_deleted; //회원탈퇴여부

    public User(String id, String password, String name, long phoneNumber, long role, long point, Date create_at, boolean is_deleted) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.point = point;
        this.create_at = create_at;
        this.is_deleted = is_deleted;
    }

    public void updateUser(String id, String password, String name, long phoneNumber, long role, long point, Date createAt, boolean is_deleted) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.point = point;
        this.create_at = createAt;
        this.is_deleted = is_deleted;
    }
}
