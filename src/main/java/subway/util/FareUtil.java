package subway.util;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;

import java.util.Comparator;
import java.util.List;

public class FareUtil {
    private static final int BASE_FARE = 1250;
    private static final int HUNDRED = 100;
    private static final int BASIC_FARE_DISTANCE = 10;
    private static final int EXTRA_FARE_DISTANCE = 50;
    private static final int OVER_EXTRA_DISTANCE_FARE = 800;
    private static final double MIDDLE_DISTANCE_DENOMINATOR = 5;
    private static final double HIGH_DISTANCE_DENOMINATOR = 8;

    public static int getTotalFare(LoginMember loginMember, int totalFare) {
        FareStrategy fareStrategy = new DefaultFareStrategy();
        if (loginMember.isTeenager()) {
            fareStrategy = new TeenagerFareStrategy();
        }
        if (loginMember.isChild()) {
            fareStrategy = new ChildFareStrategy();
        }

        return fareStrategy.getTotalFare(loginMember, totalFare);
    }

    public static int calculateDistanceFare(int totalDistance) {
        int distanceFare = BASE_FARE;

        if (BASIC_FARE_DISTANCE < totalDistance && totalDistance <= EXTRA_FARE_DISTANCE) {
            distanceFare += Math.ceil((totalDistance - BASIC_FARE_DISTANCE) / MIDDLE_DISTANCE_DENOMINATOR) * HUNDRED;
        }
        if (totalDistance > EXTRA_FARE_DISTANCE) {
            distanceFare += OVER_EXTRA_DISTANCE_FARE + Math.ceil((totalDistance - EXTRA_FARE_DISTANCE) / HIGH_DISTANCE_DENOMINATOR) * HUNDRED;
        }
        return distanceFare;
    }

    public static int getMaxExtraFare(List<Section> sections, List<Line> lines) {
        return lines.stream()
                .filter(line -> sections.stream()
                        .anyMatch(section -> line.getSections().isExist(section)))
                .max(Comparator.comparingInt(Line::getExtraFare))
                .get()
                .getExtraFare();
    }
}
