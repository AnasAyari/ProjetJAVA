package car;

public class CarModel extends AbstractComponent {
    public static final CarModel SEDAN = new CarModel("Sedan", 20000);
    public static final CarModel SUV = new CarModel("SUV", 30000);
    public static final CarModel COUPE = new CarModel("Coupe", 25000);
    public static final CarModel TRUCK = new CarModel("Truck", 28000);
    public static final CarModel CONVERTIBLE = new CarModel("Convertible", 35000);

    private final double basePrice;

    private CarModel(String displayName, double basePrice) {
        super(displayName, 0); // price is 0 for base, but we have basePrice
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return String.format("%-12s ($%.0f base)", displayName, basePrice);
    }

    public static CarModel[] values() {
        return new CarModel[]{SEDAN, SUV, COUPE, TRUCK, CONVERTIBLE};
    }
}
