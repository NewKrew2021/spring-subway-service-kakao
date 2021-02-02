package subway.favorite.domain;

import subway.exception.WrongStationIdException;

public class Favorite {
    private Long id;
    private Long memberId;
    private Long sourceStationId;
    private Long targetStationId;

    public Favorite(Long memberId, Long sourceStationId, Long targetStationId) {
        if (sourceStationId.equals(targetStationId)) {
            throw new WrongStationIdException();
        }
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public Favorite(Long id, Long memberId, Long sourceStationId, Long targetStationId) {
        this(memberId, sourceStationId, targetStationId);
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getSourceStationId() {
        return sourceStationId;
    }

    public Long getTargetStationId() {
        return targetStationId;
    }
}
