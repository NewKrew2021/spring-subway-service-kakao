//package subway.auth.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import subway.auth.ui.AuthenticationPrincipalArgumentResolver;
//
//import java.util.List;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
//
//    public WebConfig(AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver) {
//        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(authenticationPrincipalArgumentResolver);
//    }
//}
