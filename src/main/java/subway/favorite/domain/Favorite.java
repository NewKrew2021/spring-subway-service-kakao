package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {
    private Long id;
    private Long memberId;
    private Station source;
    private Station target;

    public Favorite() {
    }

    public Favorite(Long id, Long memberId, Station source, Station target) {
        this.id = id;
        this.memberId = memberId;
        this.source = source;
        this.target = target;
    }

    public Favorite(Long memberId, Station source, Station target) {
        this.memberId = memberId;
        this.source = source;
        this.target = target;
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
