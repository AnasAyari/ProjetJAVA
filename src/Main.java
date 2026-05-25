import car.*;
import receipt.Receipt;
import user.User;

import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Car Configurator: Demo of User, Car, and Receipt ---\n");

        User alice = new User("Alice", "alice@example.com", "555-1234");
        User bob   = new User("Bob", "bob@example.com", "555-5678");

        try {
            Car a1 = new Car.Builder(alice)
                    .model(CarModel.SEDAN)
                    .color(CarColor.PEARL_WHITE)
                    .engine(Engine.BASE_1_5L)
                    .wheels(Wheels.STEEL_16)
                    .interior(Interior.FABRIC)
                    .build();

            Car a2 = new Car.Builder(alice)
                    .model(CarModel.SUV)
                    .color(CarColor.MIDNIGHT_BLACK)
                    .engine(Engine.MID_2_0L)
                    .wheels(Wheels.ALLOY_19)
                    .interior(Interior.LEATHER)
                    .build();

            Car b1 = new Car.Builder(bob)
                .model(CarModel.COUPE)
                .color(CarColor.RACING_RED)
                .engine(Engine.BASE_1_5L)
                .wheels(Wheels.ALLOY_19)
                .interior(Interior.SPORT)
                .build();

            // Print receipts for each user
            printUserSummary(alice);
            printUserSummary(bob);

            // Show the detailed build receipts for Alice's cars
            System.out.println("\nDetailed receipts for Alice's cars:");
            int idx = 1;
            for (Car c : alice.getCars()) {
                System.out.println("\n--- Car #" + (idx++) + " ---");
                System.out.println(c.buildReceipt());
            }

        } catch (InvalidCarConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printUserSummary(User user) {
        System.out.println("User: " + user.getName() + " (" + user.getEmail() + ")");
        System.out.println("  Owned cars: " + user.getCars().size());
        for (Car c : user.getCars()) {
            System.out.printf("    - %s  ($%,.0f)%n", c.getModel().getDisplayName(), c.getTotalPrice());
        }
        System.out.println("  Receipts: " + user.getReceipts().size());
        DateTimeFormatter fmt = DateTimeFormatter.ISO_INSTANT;
        for (Receipt r : user.getReceipts()) {
            System.out.printf("    - %s | %s | $%,.0f%n",
                    r.getCar().getModel().getDisplayName(),
                    fmt.format(r.getPurchasedAt()),
                    r.getPrice());
        }
        System.out.println();
    }
}
