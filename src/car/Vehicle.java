package car;

public abstract class Vehicle {
    protected final String ownerName;
    protected final double totalPrice;

    protected Vehicle(String ownerName, double totalPrice) {
        this.ownerName = ownerName;
        this.totalPrice = totalPrice;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public abstract String buildReceipt();
}