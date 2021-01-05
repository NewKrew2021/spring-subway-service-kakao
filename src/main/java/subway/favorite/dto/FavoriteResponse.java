package subway.favorite.dto;

import subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

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
