package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FareCalculator {
    public static int calculate(int shortestDistance, List<Station> stations, List<Line> lines, LoginMember loginMember) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(shortestDistance);
        int fare = distanceFare.calculateExtraFareByDistance(shortestDistance);

        fare += getMaxExtraFareOfLine(stations, lines);

        if (loginMember != null) {
            AgeFare ageFare = AgeFare.getAgeFare(loginMember.getAge());
            fare -= ageFare.calculateDiscountFareByAge(fare);
        }

        return fare;
    }

    private static int getMaxExtraFareOfLine(List<Station> stations, List<Line> lines) {
        Sections allSections = new Sections();
        lines.forEach(line -> allSections.addSections(line.getSections()));
        Set<Integer> extraFares = new HashSet<>();

        for (int i = 0; i < stations.size() - 1; i++) {
            Station upStation = stations.get(i);
            Station downStation = stations.get(i + 1);
            Section section = allSections.getSection(upStation, downStation);
            extraFares.add(lines.stream()
                    .filter(line -> line.getSections().hasSection(section))
                    .findFirst()
                    .get()
                    .getExtraFare());
        }

        return Collections.max(extraFares);
    }
}
