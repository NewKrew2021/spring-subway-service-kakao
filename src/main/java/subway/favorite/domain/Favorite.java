package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {
    Long id;
    Long memberId;
    Station source;
    Station target;

    private Favorite(Long id, Long memberId, Station source, Station target) {
        this.id = id;
        this.memberId = memberId;
        this.source = source;
        this.target = target;
    }

    public static Favorite of(Long memberId, Station source, Station target) {
        return new Favorite(0L, memberId, source, target);
    }

    public static Favorite of(Long id, Long memberId, Station source, Station target) {
        return new Favorite(id, memberId, source, target);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }
}
