package subway.favorite.domain;

import subway.member.domain.Member;
import subway.station.domain.Station;

public class Favorite {
    private Long id;
    private Member memberId;
    private Station sourceStationId;
    private Station targetStationId;

    public Favorite(Long id, Member memberId, Station sourceStationId, Station targetStationId){
        this.id = id;
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public Long getId(){
        return id;
    }

    public Member getMemberId(){
        return memberId;
    }

    public Station getSourceStationId(){
        return sourceStationId;
    }

    public Station getTargetStationId(){
        return targetStationId;
    }
}
