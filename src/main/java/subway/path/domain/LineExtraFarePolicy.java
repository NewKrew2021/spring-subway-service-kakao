package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.Fare;

import java.util.List;
import java.util.stream.Collectors;

public class LineExtraFarePolicy {

    public int apply(List<Integer> extraFareList){
        return extraFareList.stream().max(Integer::compare).orElse(0);
    }
}
