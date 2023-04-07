package mybucketlistbook.service;

import lombok.RequiredArgsConstructor;
import mybucketlistbook.exception.ErrorCode;
import mybucketlistbook.exception.SnsApplicationException;
import mybucketlistbook.model.entity.PostEntity;
import mybucketlistbook.model.entity.UserEntity;
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
        System.out.println(userName);
    }
}
