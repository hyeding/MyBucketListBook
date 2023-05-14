package mybucketlistbook.controller;

import lombok.AllArgsConstructor;
import mybucketlistbook.controller.request.PostCreateRequest;
import mybucketlistbook.controller.request.PostModifyRequest;
import mybucketlistbook.controller.response.PostResponse;
import mybucketlistbook.controller.response.Response;
import mybucketlistbook.model.Post;
import mybucketlistbook.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        System.out.println("v1/posts POST 요청");
        System.out.println(authentication.isAuthenticated());
        postService.create(request.getTitle(), request.getBody(), authentication.getName());

        return Response.success();
        }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Integer postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);

        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Integer postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }
}
