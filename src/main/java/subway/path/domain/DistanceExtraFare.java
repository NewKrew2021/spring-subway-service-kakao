package subway.path.domain;

public enum DistanceExtraFare {
    DEFAULT(0,0,1250),
    BELOW_50KM(10,5,100),
    EXCEED_50KM(50,8,100)
    ;

    private int minDistance;
    private int period;
    private int extraFare;

    DistanceExtraFare(int minDistance, int period, int extraFare) {
        this.minDistance = minDistance;
        this.period = period;
        this.extraFare = extraFare;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public int getPeriod() {
        return period;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public static int getTotalFare(int distance){
        if(distance > EXCEED_50KM.getMinDistance()){
            return DEFAULT.getExtraFare() + getTotalExtraFare(distance,EXCEED_50KM);
        }

        if(distance > BELOW_50KM.getMinDistance()){
            return DEFAULT.getExtraFare() + getTotalExtraFare(distance,BELOW_50KM);
        }

        return DEFAULT.getExtraFare();
    }

    private static int getTotalExtraFare(int distance, DistanceExtraFare distanceExtraFare){
        return (distance - DEFAULT.getMinDistance()) / distanceExtraFare.getPeriod() * distanceExtraFare.getExtraFare();
    }
}
