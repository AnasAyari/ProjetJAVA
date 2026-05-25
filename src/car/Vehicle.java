package car;

import user.User;

public abstract class Vehicle implements Ownable<User>, Purchasable {
    protected final User owner;
    protected final double totalPrice;

    protected Vehicle(User owner, double totalPrice) {
        this.owner = owner;
        this.totalPrice = totalPrice;
    }

    public User getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return owner.getName();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public abstract String buildReceipt();
}