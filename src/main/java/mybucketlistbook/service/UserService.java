package mybucketlistbook.service;

import lombok.RequiredArgsConstructor;
import mybucketlistbook.exception.ErrorCode;
import mybucketlistbook.exception.SnsApplicationException;
import mybucketlistbook.model.User;
import mybucketlistbook.model.entity.UserEntity;
import mybucketlistbook.repository.UserEntityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User join(String userName, String password) {
        // 회원가입하려는 userName으로 회원가입된 user가 있는지
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%S is duplicated", userName));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));
        throw new RuntimeException();
//        throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%S is duplicated", userName));
//        return User.fromEntity(userEntity);
    }

    // TODO : implement
    public String login(String userName, String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,""));

        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,"");
        }
        // 토큰 생성

        return "";
    }
}
