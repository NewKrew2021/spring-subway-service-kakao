package subway.favorite.dto;

import subway.favorite.domain.Favorite;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse() {
    }

    public FavoriteResponse(Long id, Station sourceStation, Station targetStation) {
        this.id = id;
        this.source = StationResponse.from(sourceStation);
        this.target = StationResponse.from(targetStation);
    }

    public static FavoriteResponse of(Favorite favorite, Station sourceStation, Station targetStation) {
        return new FavoriteResponse(favorite.getId(),sourceStation, targetStation);
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
