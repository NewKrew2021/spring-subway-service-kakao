package subway.fare;

import java.util.List;

public class LineFarePolicy {
    public static int findLineExtraFare(List<Integer> lineExtraFares) {
        return lineExtraFares.stream()
                .max(Integer::compare)
                .orElse(0);
    }
}
