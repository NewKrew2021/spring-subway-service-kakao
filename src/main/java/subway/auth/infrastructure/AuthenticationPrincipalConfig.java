package subway.auth.infrastructure;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import subway.auth.application.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {
    private final AuthService authService;
    private final LoginInterceptor loginInterceptor;

    public AuthenticationPrincipalConfig(AuthService authService, LoginInterceptor loginInterceptor) {
        this.authService = authService;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/members/me")
                .addPathPatterns("/favorites/**");
    }
}
