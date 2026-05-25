package receipt;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import car.Car;

public class Receipt {
    private final UUID id;
    private final Car car;
    private final Instant purchasedAt;
    private final double price;

    public Receipt(Car car, Instant purchasedAt, double price) throws InvalidReceiptException {
        if (car == null) throw new InvalidReceiptException("car cannot be null");
        this.id = UUID.randomUUID();
        this.car = car;
        this.purchasedAt = purchasedAt == null ? Instant.now() : purchasedAt;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public Instant getPurchasedAt() {
        return purchasedAt;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id.equals(receipt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", carModel=" + car.getModel().getDisplayName() +
                ", owner=" + car.getOwnerName() +
                ", purchasedAt=" + purchasedAt +
                ", price=" + price +
                '}';
    }
}
