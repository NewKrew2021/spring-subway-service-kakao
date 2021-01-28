package subway.member.domain;

public enum Age {
    BABY(0, 0, 6),
    CHILD(0.5, 175, 13),
    TEENAGER(0.8, 70, 19),
    ADULT(1L, 0, Integer.MAX_VALUE);

    private final double sale;
    private final int limitAge;
    private final int deduction;

    Age(double sale, int deduction, int limitAge) {
        this.sale = sale;
        this.deduction = deduction;
        this.limitAge = limitAge;
    }

    public static Age getAge(int age) {
        if (age < BABY.limitAge) {
            return BABY;
        }
        if (age < CHILD.limitAge) {
            return CHILD;
        }
        if (age < TEENAGER.limitAge) {
            return TEENAGER;
        }
        return ADULT;
    }

    public double getSale() {
        return sale;
    }

    public int getDeduction() {
        return deduction;
    }

    public int discount(int fare) {
        return (int) (fare * getSale() + getDeduction());
    }
}
