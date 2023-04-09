package mybucketlistbook.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mybucketlistbook.configuration.AuthenticationConfig;
import mybucketlistbook.controller.response.PostCreateRequest;
import mybucketlistbook.controller.response.Response;
import mybucketlistbook.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }
