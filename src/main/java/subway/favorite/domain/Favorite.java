package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {
    private Long id;
    private Long memberId;
    private Station sourceStation;
    private Station targetStation;

    public Favorite(Long id, Long memberId, Station sourceStation, Station targetStation) {
        this.id = id;
        this.memberId = memberId;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    public Favorite(Long memberId, Station sourceStation, Station targetStation) {
        this.memberId = memberId;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
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