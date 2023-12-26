package jeong.boardproject.config;

import jeong.boardproject.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {                                // 사람 이름을 쓸거닌깐 String
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())    // 인증정보 가져온다.
                .map(SecurityContext::getAuthentication)                        // Authentication 정보를 불러온다
                .filter(Authentication::isAuthenticated)                        // Authentication 되었는지 확인을한다
                .map(Authentication::getPrincipal)                              // 확인했으면 principal의 인증정보를 가져온다
                .map(BoardPrincipal.class::cast)                                // 구현체인 BoardPrincipal.class를 불러와 cast한다
                .map(BoardPrincipal::getUsername);                              // 실제 사용자 정보 username
    }

}