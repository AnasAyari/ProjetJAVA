package car;

public class Engine extends AbstractComponent {
    public static final Engine BASE_1_5L = new Engine("1.5L Inline-4", 130, 0);
    public static final Engine MID_2_0L = new Engine("2.0L Inline-4", 180, 2000);
    public static final Engine SPORT_2_5L = new Engine("2.5L V6", 270, 5000);
    public static final Engine PERFORMANCE_3_5L = new Engine("3.5L V8", 400, 10000);
    public static final Engine ELECTRIC = new Engine("Electric Motor", 450, 15000);

    private final int horsepower;

    private Engine(String displayName, int horsepower, double price) {
        super(displayName, price);
        this.horsepower = horsepower;
    }

    public int getHorsepower() {
        return horsepower;
    }

    @Override
    public String toString() {
        return price == 0
            ? String.format("%-22s (%d hp, included)", displayName, horsepower)
            : String.format("%-22s (%d hp, +$%.0f)", displayName, horsepower, price);
    }

    public static Engine[] values() {
        return new Engine[]{BASE_1_5L, MID_2_0L, SPORT_2_5L, PERFORMANCE_3_5L, ELECTRIC};
    }
}
