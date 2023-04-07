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
        postService.create(authentication.getName(), request.getTitle(), request.getBody());

        return Response.success();
        }
    }
