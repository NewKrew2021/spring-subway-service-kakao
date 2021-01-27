package subway.favorite.dto;

import subway.station.domain.Station;
import subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse(Long id, Station sourceStation, Station targetStation) {
        this.id = id;
        this.source = StationResponse.of(sourceStation);
        this.target = StationResponse.of(targetStation);
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
