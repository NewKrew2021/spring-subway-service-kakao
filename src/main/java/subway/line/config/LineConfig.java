package subway.line.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import subway.line.application.LineService;
import subway.line.domain.SubwayMap;

@Configuration
public class LineConfig {
    private final LineService lineService;

    public LineConfig(LineService lineService) {
        this.lineService = lineService;
    }

    @Bean
    public SubwayMap getSubwayMap(){
        return new SubwayMap(lineService.findLines());
    }
}
