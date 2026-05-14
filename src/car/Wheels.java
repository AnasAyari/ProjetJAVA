package car;

public class Wheels extends AbstractComponent {
    public static final Wheels STEEL_16 = new Wheels("16\" Steel Rims", 0);
    public static final Wheels ALLOY_17 = new Wheels("17\" Alloy Rims", 800);
    public static final Wheels ALLOY_19 = new Wheels("19\" Sport Alloy", 1500);
    public static final Wheels CARBON_20 = new Wheels("20\" Carbon Fiber", 3000);
    public static final Wheels FORGED_22 = new Wheels("22\" Forged Rims", 5000);

    private Wheels(String displayName, double price) {
        super(displayName, price);
    }

    public static Wheels[] values() {
        return new Wheels[]{STEEL_16, ALLOY_17, ALLOY_19, CARBON_20, FORGED_22};
    }
}
