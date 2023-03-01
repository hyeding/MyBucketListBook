package mybucketlistbook.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mybucketlistbook.model.User;
import mybucketlistbook.model.UserRole;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Integer id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getId(),
                user.getUserName(),
                user.getUserRole()
        );
    }
}
