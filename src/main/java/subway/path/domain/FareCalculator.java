package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.PathResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FareCalculator {
    private FareByDistance fareByDistance;
    private FareByLine fareByLine;
    private FareByAge fareDiscount;

    public FareCalculator (){
        fareByDistance = new FareByDistance();
        fareByLine = new FareByLine();
        fareDiscount = new FareByAge();
    }

    public Fare calculate(PathResult result, Integer age){
        fareByDistance.calculateFare(result.getDistance());
        fareByLine.calculateFare(findLineListInPath(result.getPathVertices()));

        return fareDiscount.calculateFare(fareByDistance.getFare() + fareByLine.getFare(), age);
    }

    private List<Line> findLineListInPath(PathVertices pathVertices){

        Set<Line> lineSet = new HashSet<>();
        List<Line> previousLineList = new ArrayList<>();
        for (PathVertex pathVertex : pathVertices.getPathVertexList()) {
            //Line duplicateLine = getDuplicateLineId(pathVertex.getLineList(), previousLineList);
            //addLineIdIfExist(duplicateLine, lineSet);
            //previousLineList = pathVertex.getLineList();
        }
        return new ArrayList<>(lineSet);
    }

    private void addLineIdIfExist(Line dup, Set<Line> lineSet){
        if(dup != null)
            lineSet.add(dup);
    }

    private Line getDuplicateLineId(List<Line> newLineList, List<Line> previousLineList){

        return newLineList
                .stream()
                .filter(newLine -> previousLineList.contains(newLine))
                .findFirst()
                .orElse(null);
    }
}
