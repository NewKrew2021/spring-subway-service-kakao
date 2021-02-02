package subway.favorite.dto;

import subway.favorite.domain.Favorite;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse(Long id, Station source, Station target){
        this.id = id;
        this.source = StationResponse.of(source);
        this.target = StationResponse.of(target);
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
