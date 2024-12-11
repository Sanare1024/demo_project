package hello.demo_project.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private long memberId;
    private String id;
    private String password;
    private String name;
    private long phoneNumber;
    private long role;
    private long point;
    private Date create_at;
    private boolean is_deleted;
}
