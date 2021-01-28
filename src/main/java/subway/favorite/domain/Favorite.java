package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {
    private final Long id;
    private final Long memberId;
    private final Station sourceStation;
    private final Station targetStation;

    public Favorite(Long id, Long memberId, Station sourceStation, Station targetStation) {
        this.id = id;
        this.memberId = memberId;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    public Long getSourceStationId() {
        return sourceStation.getId();
    }

    public Long getTargetStationId() {
        return targetStation.getId();
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public Station getTargetStation() {
        return targetStation;
    }
}
