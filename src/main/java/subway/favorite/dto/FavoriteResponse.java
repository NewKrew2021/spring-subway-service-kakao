package subway.favorite.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;
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

    public static FavoriteResponse of(Favorite favorite, Station sourceStation,
                                      Station targetStation) {
        return new FavoriteResponse(favorite.getId(), StationResponse.of(sourceStation),
                StationResponse.of(targetStation));
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
