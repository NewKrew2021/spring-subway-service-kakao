package subway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import subway.auth.ui.LoginInterceptor;
import subway.line.dao.LineDao;
import subway.path.domain.Path;

@Configuration
public class SubwayConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/favorites/**", "/members/me");
    }

    @Bean
    public Path path(LineDao lineDao){
        return new Path(lineDao.findAll());
    }
}
