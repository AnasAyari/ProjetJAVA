package car;

public abstract class AbstractComponent implements Component {
    protected final String displayName;
    protected final double price;

    protected AbstractComponent(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return price == 0
            ? String.format("%-22s (included)", displayName)
            : String.format("%-22s (+$%.0f)", displayName, price);
    }
}