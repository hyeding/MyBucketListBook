package mybucketlistbook.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mybucketlistbook.model.Post;
import mybucketlistbook.model.User;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Integer id;

    private String title;

    private String body;

    private UserResponse userResponse;

    private Timestamp registerAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post){
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisterAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }
}
