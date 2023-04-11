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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


// 프론트엔드에서 매순간 request를 요청 할 때마다 이 필터를 먼저 거치도록 하여 인증되지 않은 사용자가 정보를 얻는 것을 방지함
// 헤더에 담겨온 토큰정보가 유효한지 확인하고, 맞다면 요청사항을 수행함
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // 매요청마다 필터사용

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null or invalid");
            chain.doFilter(request, response);
            return;
        }
        try {
            final String token = header.split(" ")[1].trim(); //trim() : 문자열 공백제거
            // TODO : check token is valid
            if(JwtTokenUtils.isExpired(token,key)) {
                log.error("Key is expired");
                chain.doFilter(request, response);
            }

            // TODO : get username from token
            String userName = JwtTokenUtils.getUserName(token, key);
            // TODO : check the userName is valid
            User user = userService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    // TODO
                    user, null,user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (RuntimeException e) {
            log.error("Error occurs while validation, {}", e.toString());
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}
