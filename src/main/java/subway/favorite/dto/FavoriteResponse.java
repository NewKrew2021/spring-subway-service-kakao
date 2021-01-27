package subway.favorite.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.station.dto.StationResponse;

public class FavoriteResponse {
    private final Long id;
    private final StationResponse source;
    private final StationResponse target;

    @JsonCreator
    public FavoriteResponse(Long id, StationResponse source, StationResponse target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public StationResponse getSource() {
        return source;
    }

    public StationResponse getTarget() {
        return target;
    }
}
