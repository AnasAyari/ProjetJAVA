import car.*;
import menu.MenuHelper;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        printWelcome();

        List<Car> configuredCars = new ArrayList<>();
        boolean running = true;
        while (running) {
            try {
                Car car = configureCar();
                configuredCars.add(car);
                System.out.println(car.buildReceipt());

                running = MenuHelper.confirm("Would you like to configure another car?");
            } catch (InvalidCarConfigurationException e) {
                System.out.println("Configuration error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }

        printSummary(configuredCars);

        System.out.println("\n  Goodbye! Drive safe. \n");
    }

    private static void printSummary(List<Car> cars) {
        if (cars.isEmpty()) return;

        System.out.println("\n" + "═".repeat(52));
        System.out.println("       ★  CONFIGURATION SUMMARY  ★");
        System.out.println("═".repeat(52));
        System.out.println("Total cars configured: " + cars.size());
        double totalValue = cars.stream().mapToDouble(Car::getTotalPrice).sum();
        System.out.printf("Total value: $%,.0f%n", totalValue);
        System.out.println("═".repeat(52));
    }

    //configuration flow 
    private static Car configureCar() throws InvalidCarConfigurationException {

        MenuHelper.header("STEP 1 of 5 — Owner Name");
        String name = MenuHelper.readString("  Enter your name: ");

        MenuHelper.header("STEP 2 of 5 — Choose Model");
        int modelIdx = MenuHelper.promptChoice("Select a car model:", CarModel.values());

        MenuHelper.header("STEP 3 of 5 — Choose Color");
        int colorIdx = MenuHelper.promptChoice("Select a color:", CarColor.values());

        MenuHelper.header("STEP 4 of 5 — Choose Engine");
        int engineIdx = MenuHelper.promptChoice("Select an engine:", Engine.values());

        MenuHelper.header("STEP 4.5 of 5 — Choose Wheels");
        int wheelsIdx = MenuHelper.promptChoice("Select wheels:", Wheels.values());

        MenuHelper.header("STEP 5 of 5 — Choose Interior");
        int interiorIdx = MenuHelper.promptChoice("Select interior:", Interior.values());

        // Build the car using the Builder pattern
        return new Car.Builder(name)
                .model   (CarModel.values()[modelIdx])
                .color   (CarColor.values()[colorIdx])
                .engine  (Engine.values()[engineIdx])
                .wheels  (Wheels.values()[wheelsIdx])
                .interior(Interior.values()[interiorIdx])
                .build();
    }

    //banner
    private static void printWelcome() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║                                                  ║");
        System.out.println("  ║              CAR CONFIGURATOR                    ║");
        System.out.println("  ║        Build your dream car, your way.           ║");
        System.out.println("  ║                                                  ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }
}
