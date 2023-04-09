package mybucketlistbook.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mybucketlistbook.model.User;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String userName;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername()
        );
    }
}
