package subway.favorite.domain;

import subway.member.domain.LoginMember;
import subway.station.domain.Station;

public class Favorite {

    private Long id;
    private LoginMember member;
    private Station sourceStation;
    private Station targetStation;

    public Favorite(Long id, LoginMember member, Station sourceStation, Station targetStation) {
        this.id = id;
        this.member = member;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    public Favorite(LoginMember member, Station sourceStation, Station targetStation) {
        this(null, member, sourceStation, targetStation);
    }

    public Long getId() {
        return id;
    }

    public LoginMember getMember() {
        return member;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public Station getTargetStation() {
        return targetStation;
    }
}