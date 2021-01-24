package subway.favorite.domain;

public class Favorite {
    private Long id;
    private Long memberId;
    private Long sourceStationId;
    private Long targetStationId;

    public Favorite(Long id, Long memberId, Long sourceStationId, Long targetStationId){
        this.id = id;
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public Long getId(){
        return id;
    }

    public Long getMemberId(){
        return memberId;
    }

    public Long getSourceStationId(){
        return sourceStationId;
    }

    public Long getTargetStationId(){
        return targetStationId;
    }
}
