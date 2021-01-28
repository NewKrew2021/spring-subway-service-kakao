package subway.favorite.domain;

import subway.member.domain.Member;
import subway.station.domain.Station;

public class Favorite {
    private Long id;
    private Member member;
    private Station sourceStation;
    private Station targetStation;

    public Favorite(Long id, Member member, Station sourceStation, Station targetStation){
        this.id = id;
        this.member = member;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }



    public Long getId(){
        return id;
    }

    public Member getMember(){
        return member;
    }

    public Station getSourceStation(){
        return sourceStation;
    }

    public Station getTargetStation(){
        return targetStation;
    }

}
