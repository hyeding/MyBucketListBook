package mybucketlistbook.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// WebSecurity와 HttpSecurity를 커스터마이징하는 단계
// 비밀번호를 인코딩하기 위해 필요한 빈 설정을 하고, jwt로 스프링 시큐리티를 이용하겠다는 설정을 진행
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
