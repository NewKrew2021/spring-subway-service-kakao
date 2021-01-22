package subway.favorite.dto;

import subway.favorite.domain.Favorite;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

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

    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(favorite.getId(),
                StationResponse.of(favorite.getSourceStation()),
                StationResponse.of(favorite.getTargetStation()));
    }

    public static List<FavoriteResponse> listOf(List<Favorite> favorites){
        return favorites.stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());
    }
}
