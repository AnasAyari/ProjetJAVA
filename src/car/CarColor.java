package car;

public class CarColor extends AbstractComponent {
    public static final CarColor PEARL_WHITE = new CarColor("Pearl White", 0);
    public static final CarColor MIDNIGHT_BLACK = new CarColor("Midnight Black", 0);
    public static final CarColor RACING_RED = new CarColor("Racing Red", 500);
    public static final CarColor OCEAN_BLUE = new CarColor("Ocean Blue", 500);
    public static final CarColor FOREST_GREEN = new CarColor("Forest Green", 500);
    public static final CarColor SOLAR_YELLOW = new CarColor("Solar Yellow", 800);
    public static final CarColor MATTE_GRAY = new CarColor("Matte Gray", 1200);
    public static final CarColor CUSTOM_CHROME = new CarColor("Custom Chrome", 2500);

    private CarColor(String displayName, double price) {
        super(displayName, price);
    }

    public static CarColor[] values() {
        return new CarColor[]{PEARL_WHITE, MIDNIGHT_BLACK, RACING_RED, OCEAN_BLUE, FOREST_GREEN, SOLAR_YELLOW, MATTE_GRAY, CUSTOM_CHROME};
    }
}
