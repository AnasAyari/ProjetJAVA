package car;

public class Interior extends AbstractComponent {
    public static final Interior FABRIC = new Interior("Fabric Seats", 0);
    public static final Interior LEATHER = new Interior("Leather Seats", 2000);
    public static final Interior PREMIUM = new Interior("Premium Leather + Wood", 4500);
    public static final Interior SPORT = new Interior("Sport Alcantara", 3500);

    private Interior(String displayName, double price) {
        super(displayName, price);
    }

    public static Interior[] values() {
        return new Interior[]{FABRIC, LEATHER, PREMIUM, SPORT};
    }
}
