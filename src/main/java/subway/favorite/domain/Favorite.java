package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {
    Long id, memberId;
    Station sourceStation, targetStation;

    public Favorite(Long id, Long memberId, Station sourceStation, Station targetStation) {
        this.id = id;
        this.memberId = memberId;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    public Favorite(Long memberId, Station sourceStation, Station targetStation) {
        this(null, memberId, sourceStation, targetStation);
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