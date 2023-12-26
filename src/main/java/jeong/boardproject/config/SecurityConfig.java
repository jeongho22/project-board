package jeong.boardproject.config;

import jeong.boardproject.dto.UserAccountDto;
import jeong.boardproject.dto.security.BoardPrincipal;
import jeong.boardproject.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적페이지는 제외한다
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"    // 세 주소만 권한을 허가하겠다.
                        ).permitAll()
                        .anyRequest().authenticated()           // 나머지는 인증이 되어야만한다.
                )
                .formLogin().and()
                .logout()
                        .logoutSuccessUrl("/")
                        .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) { // 유저 어카운트 레포지터리 가져옴
        return username -> userAccountRepository
                .findById(username)  //유저네임을 찾는다
                .map(UserAccountDto::from) // 찾아서 dto로 매핑시킨다
                .map(BoardPrincipal::from) // prinncipal을 가져온다.
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username)); // 예외처리
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    // 패스워드를 인코더로 위임해서 가져오겠다.


}