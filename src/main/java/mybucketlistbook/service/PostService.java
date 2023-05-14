package mybucketlistbook.service;

import lombok.RequiredArgsConstructor;
import mybucketlistbook.exception.ErrorCode;
import mybucketlistbook.exception.SnsApplicationException;
import mybucketlistbook.model.entity.PostEntity;
import mybucketlistbook.model.entity.UserEntity;
import mybucketlistbook.model.Post;
import mybucketlistbook.repository.PostEntityRepository;
import mybucketlistbook.repository.UserEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded",userName)));
        // post save
        postEntityRepository.save(PostEntity.of(title, body, userEntity));
    }
    @Transactional
    public Post modify(String title, String body, String userName, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded",userName)));
        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        // post permission : 유저Id 말고 객체로 비교함
        if(postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION,String.format("%s has no permission with %s", userName, postId));
        }
        postEntity.setTitle(title);
        postEntity.setBody(body);

         return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }
    @Transactional
    public void delete(String userName, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded",userName)));

        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        // post permission : 유저Id 말고 객체로 비교함
        if(postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION,String.format("%s has no permission with %s", userName, postId));
        }
        postEntityRepository.delete(postEntity);

    }
}
