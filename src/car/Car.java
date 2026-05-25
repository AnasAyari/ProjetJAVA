package car;

import java.time.Instant;

import receipt.Receipt;
import user.User;

public class Car extends Vehicle {

    //fields
    private final CarModel model;
    private final CarColor color;
    private final Engine   engine;
    private final Wheels   wheels;
    private final Interior interior;

    //private constructor(only Builder can create a Car)
    private Car(Builder b) {
        super(b.owner, calcPrice(b));
        this.model      = b.model;
        this.color      = b.color;
        this.engine     = b.engine;
        this.wheels     = b.wheels;
        this.interior   = b.interior;
    }

    private static double calcPrice(Builder b) {
        return b.model.getBasePrice()
             + b.color.getPrice()
             + b.engine.getPrice()
             + b.wheels.getPrice()
             + b.interior.getPrice();
    }

    //getters
    public CarModel getModel()      { return model; }
    public CarColor getColor()      { return color; }
    public Engine   getEngine()     { return engine; }
    public Wheels   getWheels()     { return wheels; }
    public Interior getInterior()   { return interior; }

    //receipt
    @Override
    public String buildReceipt() {
        String line  = "═".repeat(52);
        String thin  = "─".repeat(52);
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append(line).append("\n");
        sb.append("       ★  YOUR CAR BUILD SUMMARY  ★\n");
        sb.append(line).append("\n");
        sb.append(String.format("  Owner   : %s%n",  getOwnerName()));
        sb.append(thin).append("\n");
        sb.append(String.format("  Model   : %s%n",  model.getDisplayName()));
        sb.append(String.format("  Color   : %s%n",  color.getDisplayName()));
        sb.append(String.format("  Engine  : %s (%d hp)%n",
                engine.getDisplayName(), engine.getHorsepower()));
        sb.append(String.format("  Wheels  : %s%n",  wheels.getDisplayName()));
        sb.append(String.format("  Interior: %s%n",  interior.getDisplayName()));
        sb.append(thin).append("\n");
        sb.append(String.format("  Base price          : $%,.0f%n", model.getBasePrice()));
        sb.append(String.format("  Color upgrade       : $%,.0f%n", color.getPrice()));
        sb.append(String.format("  Engine upgrade      : $%,.0f%n", engine.getPrice()));
        sb.append(String.format("  Wheels upgrade      : $%,.0f%n", wheels.getPrice()));
        sb.append(String.format("  Interior upgrade    : $%,.0f%n", interior.getPrice()));
        sb.append(thin).append("\n");
        sb.append(String.format("  TOTAL PRICE         : $%,.0f%n", getTotalPrice()));
        sb.append(line).append("\n");
        sb.append("  Thank you for using Car Configurator!\n");
        sb.append(line).append("\n");

        return sb.toString();
    }


    //  BUILDER

    public static class Builder {

        // required
        private final User owner;

        // optional with defaults
        private CarModel model    = CarModel.SEDAN;
        private CarColor color    = CarColor.PEARL_WHITE;
        private Engine   engine   = Engine.BASE_1_5L;
        private Wheels   wheels   = Wheels.STEEL_16;
        private Interior interior = Interior.FABRIC;

        public Builder(User owner) throws InvalidCarConfigurationException {
            if (owner == null) {
                throw new InvalidCarConfigurationException("Owner cannot be null.");
            }
            String ownerName = owner.getName();
            if (ownerName == null || ownerName.isBlank()) {
                throw new InvalidCarConfigurationException("Owner name cannot be empty.");
            }
            if (ownerName.trim().length() < 2) {
                throw new InvalidCarConfigurationException("Owner name must be at least 2 characters long.");
            }
            this.owner = owner;
        }

        public Builder model   (CarModel m) { this.model    = m; return this; }
        public Builder color   (CarColor c) { this.color    = c; return this; }
        public Builder engine  (Engine   e) { this.engine   = e; return this; }
        public Builder wheels  (Wheels   w) { this.wheels   = w; return this; }
        public Builder interior(Interior i) { this.interior = i; return this; }

        public Car build() throws receipt.InvalidReceiptException, user.InvalidUserDataException, DuplicateCarException {
            Car c = new Car(this);
            this.owner.addCar(c);
            Receipt r = new Receipt(c, Instant.now(), c.getTotalPrice());
            this.owner.addReceipt(r);
            return c;
        }
    }
}
