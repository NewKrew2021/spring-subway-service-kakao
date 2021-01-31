package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.PathResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FareCalculator {
    public static Fare calculate(PathResult result, Integer age){
        FareByDistance basicFare = new FareByDistance(result.getDistance());

        FareByLine extraFare = new FareByLine(findLineListInPath(result.getPathVertices()));

        FareByAge fareDiscount = new FareByAge(basicFare.getFare() + extraFare.getFare(), age);

        return fareDiscount;
    }

    private static List<Line> findLineListInPath(PathVertices pathVertices){

        Set<Line> lineSet = new HashSet<>();
        List<Line> previousLineList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            Line duplicateLine = getDuplicateLineId(pathVertex.getLineList(), previousLineList);
            addLineIdIfExist(duplicateLine, lineSet);
            previousLineList = pathVertex.getLineList();
        }
        return new ArrayList<>(lineSet);
    }

    private static void addLineIdIfExist(Line dup, Set<Line> lineSet){
        if(dup != null)
            lineSet.add(dup);
    }

    private static Line getDuplicateLineId(List<Line> newLineList, List<Line> previousLineList){

        return newLineList
                .stream()
                .filter(newLine -> previousLineList.contains(newLine))
                .findFirst()
                .orElse(null);
    }
}
