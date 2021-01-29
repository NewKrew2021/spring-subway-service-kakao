package subway.favorite.domain;

import subway.station.domain.Station;

public class Favorite {

    private Long id;
    private Station source;
    private Station target;
    private Long userId;

    public Favorite(Station source, Station target, Long userId) {
        this.source = source;
        this.target = target;
        this.userId = userId;
    }

    public Favorite(Long id, Station source, Station target, Long userId) {
        this(source,target,userId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }

    public Long getUserId() {
        return userId;
    }
}
