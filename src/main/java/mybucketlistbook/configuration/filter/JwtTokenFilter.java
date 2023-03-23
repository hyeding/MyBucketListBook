package mybucketlistbook.configuration.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mybucketlistbook.model.User;
import mybucketlistbook.service.UserService;
import mybucketlistbook.util.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // 매요청마다 필터사용

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null or invalid");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String token = header.split(" ")[1].trim(); //trim() : 문자열 공백제거

            // TODO : check token is valid
            if(JwtTokenUtils.isExpired(token,key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
            }

            // TODO : get username from token
            String userName = JwtTokenUtils.getUserName(token, key);
            // TODO : check the userName is valid
            User user = userService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    // TODO
                    user, null, List.of(new SimpleGrantedAuthority(user.getUserRole().toString()))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (RuntimeException e) {
            log.error("Error occurs while validation, {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
