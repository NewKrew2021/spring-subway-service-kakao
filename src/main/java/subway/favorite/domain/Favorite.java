package subway.favorite.domain;

import subway.member.domain.Member;
import subway.station.domain.Station;

public class Favorite {
    private Long id;
    private Member memberId;
    private Station sourceStation;
    private Station targetStation;

    public Favorite(Long id, Member memberId, Station sourceStation, Station targetStation){
        this.id = id;
        this.memberId = memberId;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    public Long getId(){
        return id;
    }

    public Member getMemberId(){
        return memberId;
    }

    public Station getSourceStation(){
        return sourceStation;
    }

    public Station getTargetStation(){
        return targetStation;
    }
}
