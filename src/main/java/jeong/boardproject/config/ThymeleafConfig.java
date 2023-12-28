package jeong.boardproject.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic()); // 끄고싶을때는 yaml 파일가서 false 변경

        return defaultTemplateResolver;
    }


    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3") // 이걸 쓸려면 보드 어플리케이션쪽가서 ConfigurationPropertiesScan 써줘야함....
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }

}