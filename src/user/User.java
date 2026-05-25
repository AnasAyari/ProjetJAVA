package user;

import car.*;
import receipt.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User implements Identifiable {
    private final UUID id;
    private final String name;
    private String email;
    private String phone;
    private final Instant createdAt;
    private final List<Car> cars = new ArrayList<>();
    private final List<Receipt> receipts = new ArrayList<>();

    public User(String name) throws InvalidUserDataException {
        this(UUID.randomUUID(), name, null, null, Instant.now());
    }

    public User(String name, String email, String phone) throws InvalidUserDataException {
        this(UUID.randomUUID(), name, email, phone, Instant.now());
    }

    User(UUID id, String name, String email, String phone, Instant createdAt) throws InvalidUserDataException {
        if (id == null) throw new InvalidUserDataException("id cannot be null");
        if (name == null) throw new InvalidUserDataException("Name cannot be null");
        this.id = id;
        this.name = name.trim();
        this.email = email == null ? null : email.trim();
        this.phone = phone == null ? null : phone.trim();
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public List<Receipt> getReceipts() {
        return Collections.unmodifiableList(receipts);
    }

    public void addCar(Car c) throws InvalidUserDataException, DuplicateCarException {
        if (c == null) throw new InvalidUserDataException("car cannot be null");
        if (cars.contains(c)) throw new DuplicateCarException("User already owns this car");
        cars.add(c);
    }

    public void addReceipt(Receipt r) throws InvalidReceiptException {
        if (r == null) throw new InvalidReceiptException("receipt cannot be null");
        if (!receipts.contains(r)) receipts.add(r);
    }

    public void removeReceipt(Receipt r) throws InvalidReceiptException, ReceiptNotFoundException {
        if (r == null) throw new InvalidReceiptException("receipt cannot be null");
        if (!receipts.contains(r)) throw new ReceiptNotFoundException("receipt not found");
        receipts.remove(r);
    }

    public void removeCar(Car c) throws InvalidUserDataException, CarNotOwnedException {
        if (c == null) throw new InvalidUserDataException("car cannot be null");
        if (!cars.contains(c)) throw new CarNotOwnedException("User does not own this car");
        cars.remove(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                (email != null ? ", email='" + email + '\'' : "") +
                (phone != null ? ", phone='" + phone + '\'' : "") +
                ", cars=" + cars.size() +
                '}';
    }
}
