import car.Car;
import car.CarColor;
import car.CarModel;
import car.DuplicateCarException;
import car.Engine;
import car.Interior;
import car.InvalidCarConfigurationException;
import car.Wheels;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import receipt.InvalidReceiptException;
import user.InvalidUserDataException;
import user.User;

public class CarConfiguratorWindow extends JFrame {

    private final JTextField nameField = new JTextField("Alex Martin", 20);
    private final JTextField emailField = new JTextField("alex@example.com", 20);
    private final JTextField phoneField = new JTextField("555-0100", 20);

    private final JComboBox<CarModel> modelBox = new JComboBox<>(CarModel.values());
    private final JComboBox<CarColor> colorBox = new JComboBox<>(CarColor.values());
    private final JComboBox<Engine> engineBox = new JComboBox<>(Engine.values());
    private final JComboBox<Wheels> wheelsBox = new JComboBox<>(Wheels.values());
    private final JComboBox<Interior> interiorBox = new JComboBox<>(Interior.values());

    private final JLabel currentCustomerLabel = new JLabel();
    private final JLabel summaryLabel = new JLabel();
    private final JLabel priceLabel = new JLabel();

    private final DefaultListModel<Car> carListModel = new DefaultListModel<>();
    private final JList<Car> carList = new JList<>(carListModel);
    private final JTextArea receiptArea = new JTextArea();

    private User currentUser;

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     javax.swing.UnsupportedLookAndFeelException ignored) {
            }

            Font segoe = new Font("Segoe UI", Font.PLAIN, 13);
            if (segoe.getFamily().equalsIgnoreCase("Segoe UI")) {
                UIManager.put("Label.font", segoe);
                UIManager.put("Button.font", segoe);
                UIManager.put("TextField.font", segoe);
                UIManager.put("ComboBox.font", segoe);
                UIManager.put("List.font", segoe);
                UIManager.put("TextArea.font", new Font("Consolas", Font.PLAIN, 13));
            }

            new CarConfiguratorWindow().setVisible(true);
        });
    }

    public CarConfiguratorWindow() {
        super("Car Configurator");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1120, 720));
        setLocationRelativeTo(null);

        currentUser = createUserFromFields();

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setBackground(new Color(245, 247, 250));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildMainContent(), BorderLayout.CENTER);

        wireEvents();
        refreshState();
        pack();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Car Configurator Studio");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(new Color(28, 42, 68));

        JLabel subtitle = new JLabel("Build a car, preview the price, and review the receipt in one place.");
        subtitle.setForeground(new Color(90, 102, 121));

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        return header;
    }

    private JSplitPane buildMainContent() {
        JPanel left = new JPanel(new BorderLayout(12, 12));
        left.setOpaque(false);
        left.add(buildCustomerPanel(), BorderLayout.NORTH);
        left.add(buildConfigurationPanel(), BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout(12, 12));
        right.setOpaque(false);
        right.add(buildSessionPanel(), BorderLayout.NORTH);
        right.add(buildReceiptPanel(), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setResizeWeight(0.52);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);
        return splitPane;
    }

    private JPanel buildCustomerPanel() {
        JPanel panel = cardPanel("Customer");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints c = gridConstraints();
        addField(form, c, 0, "Name", nameField);
        addField(form, c, 1, "Email", emailField);
        addField(form, c, 2, "Phone", phoneField);

        JPanel actions = new JPanel();
        actions.setOpaque(false);

        JButton applyButton = new JButton("Apply Customer");
        JButton resetButton = new JButton("Reset Session");
        JButton demoButton = new JButton("Load Demo Customer");
        actions.add(applyButton);
        actions.add(resetButton);
        actions.add(demoButton);

        applyButton.addActionListener(e -> applyCustomerFromFields());
        resetButton.addActionListener(e -> resetSession());
        demoButton.addActionListener(e -> loadDemoCustomer());

        panel.add(form, BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildConfigurationPanel() {
        JPanel panel = cardPanel("Build Options");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints c = gridConstraints();
        addField(form, c, 0, "Model", modelBox);
        addField(form, c, 1, "Color", colorBox);
        addField(form, c, 2, "Engine", engineBox);
        addField(form, c, 3, "Wheels", wheelsBox);
        addField(form, c, 4, "Interior", interiorBox);

        priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD, 15f));
        priceLabel.setForeground(new Color(33, 94, 51));

        JButton buildButton = new JButton("Build Car");
        buildButton.addActionListener(e -> buildCar());

        JPanel actions = new JPanel();
        actions.setOpaque(false);
        actions.add(buildButton);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setOpaque(false);
        bottom.add(priceLabel, BorderLayout.NORTH);
        bottom.add(actions, BorderLayout.CENTER);

        panel.add(form, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildSessionPanel() {
        JPanel panel = cardPanel("Session");
        panel.setLayout(new BorderLayout(6, 6));

        currentCustomerLabel.setForeground(new Color(70, 82, 100));
        summaryLabel.setForeground(new Color(70, 82, 100));

        panel.add(currentCustomerLabel, BorderLayout.NORTH);
        panel.add(summaryLabel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildReceiptPanel() {
        JPanel panel = cardPanel("Built Cars and Receipt Preview");
        panel.setLayout(new BorderLayout(10, 10));

        carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carList.setVisibleRowCount(7);
        carList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(carSummary(value));
            label.setBorder(new EmptyBorder(6, 8, 6, 8));
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(222, 236, 255) : Color.WHITE);
            label.setForeground(new Color(38, 48, 65));
            return label;
        });

        JScrollPane listScroll = new JScrollPane(carList);
        listScroll.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 228)));

        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        receiptArea.setLineWrap(false);
        receiptArea.setWrapStyleWord(false);
        receiptArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        receiptScroll.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 228)));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScroll, receiptScroll);
        splitPane.setResizeWeight(0.35);
        splitPane.setBorder(null);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private void wireEvents() {
        modelBox.addActionListener(e -> refreshPricePreview());
        colorBox.addActionListener(e -> refreshPricePreview());
        engineBox.addActionListener(e -> refreshPricePreview());
        wheelsBox.addActionListener(e -> refreshPricePreview());
        interiorBox.addActionListener(e -> refreshPricePreview());

        carList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedCarReceipt();
            }
        });
    }

    private void refreshState() {
        refreshPricePreview();
        updateSessionLabels();
        showSelectedCarReceipt();
    }

    private void refreshPricePreview() {
        double total = selectedModel().getBasePrice()
                + selectedColor().getPrice()
                + selectedEngine().getPrice()
                + selectedWheels().getPrice()
                + selectedInterior().getPrice();

        priceLabel.setText(String.format(
                "Estimated price: %s  |  %s + %s + %s + %s + %s",
                formatCurrency(total),
                formatCurrency(selectedModel().getBasePrice()),
                formatCurrency(selectedColor().getPrice()),
                formatCurrency(selectedEngine().getPrice()),
                formatCurrency(selectedWheels().getPrice()),
                formatCurrency(selectedInterior().getPrice())));
    }

    private void buildCar() {
        try {
            applyCustomerFromFields();

            Car car = new Car.Builder(currentUser)
                    .model(selectedModel())
                    .color(selectedColor())
                    .engine(selectedEngine())
                    .wheels(selectedWheels())
                    .interior(selectedInterior())
                    .build();

            carListModel.addElement(car);
            carList.setSelectedIndex(carListModel.size() - 1);

            updateSessionLabels();
            JOptionPane.showMessageDialog(this,
                    "Car built successfully for " + currentUser.getName() + ".",
                    "Build Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidCarConfigurationException | InvalidReceiptException | InvalidUserDataException |
                 DuplicateCarException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Build Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyCustomerFromFields() {
        String name = nameField.getText().trim();
        String email = nameOrNull(emailField.getText());
        String phone = nameOrNull(phoneField.getText());

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Customer name cannot be empty.",
                    "Invalid Customer",
                    JOptionPane.WARNING_MESSAGE);
            nameField.requestFocusInWindow();
            return;
        }

        boolean sessionChanged = currentUser == null || !currentUser.getName().equals(name);

        try {
            if (sessionChanged) {
                currentUser = new User(name, email, phone);
                carListModel.clear();
                receiptArea.setText("");
            } else {
                currentUser.setEmail(email);
                currentUser.setPhone(phone);
            }
            updateSessionLabels();
        } catch (InvalidUserDataException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Invalid Customer",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDemoCustomer() {
        nameField.setText("Jordan Lee");
        emailField.setText("jordan.lee@example.com");
        phoneField.setText("555-0148");
        applyCustomerFromFields();
    }

    private void resetSession() {
        nameField.setText("Alex Martin");
        emailField.setText("alex@example.com");
        phoneField.setText("555-0100");
        modelBox.setSelectedItem(CarModel.SEDAN);
        colorBox.setSelectedItem(CarColor.PEARL_WHITE);
        engineBox.setSelectedItem(Engine.BASE_1_5L);
        wheelsBox.setSelectedItem(Wheels.STEEL_16);
        interiorBox.setSelectedItem(Interior.FABRIC);

        currentUser = createUserFromFields();
        carListModel.clear();
        receiptArea.setText("");
        updateSessionLabels();
        refreshPricePreview();
    }

    private User createUserFromFields() {
        try {
            return new User(nameField.getText().trim(), nameOrNull(emailField.getText()), nameOrNull(phoneField.getText()));
        } catch (InvalidUserDataException e) {
            throw new IllegalStateException(e);
        }
    }

    private void updateSessionLabels() {
        currentCustomerLabel.setText(String.format("Customer: %s%s%s",
                currentUser.getName(),
                currentUser.getEmail() == null || currentUser.getEmail().isBlank() ? "" : " | " + currentUser.getEmail(),
                currentUser.getPhone() == null || currentUser.getPhone().isBlank() ? "" : " | " + currentUser.getPhone()));

        double totalSpend = 0;
        for (Car car : currentUser.getCars()) {
            totalSpend += car.getTotalPrice();
        }

        summaryLabel.setText(String.format("Cars built: %d   |   Total spent: %s",
                currentUser.getCars().size(),
                formatCurrency(totalSpend)));
    }

    private void showSelectedCarReceipt() {
        Car car = carList.getSelectedValue();
        if (car == null) {
            receiptArea.setText(carListModel.isEmpty()
                    ? "Build a car to see its receipt here."
                    : "Select a built car to preview its receipt.");
            return;
        }

        receiptArea.setText(car.buildReceipt());
        receiptArea.setCaretPosition(0);
    }

    private JPanel cardPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 218, 227)),
                new EmptyBorder(12, 12, 12, 12)));
        panel.setBackground(Color.WHITE);

        JLabel heading = new JLabel(title);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 16f));
        heading.setForeground(new Color(32, 46, 69));
        panel.add(heading, BorderLayout.NORTH);
        return panel;
    }

    private GridBagConstraints gridConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 0, 6, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        return constraints;
    }

    private void addField(JPanel form, GridBagConstraints base, int row, String label, Component field) {
        GridBagConstraints labelConstraints = (GridBagConstraints) base.clone();
        labelConstraints.gridy = row;
        labelConstraints.weightx = 0.2;

        GridBagConstraints fieldConstraints = (GridBagConstraints) base.clone();
        fieldConstraints.gridy = row;
        fieldConstraints.gridx = 1;
        fieldConstraints.weightx = 0.8;
        fieldConstraints.insets = new Insets(6, 12, 6, 0);

        form.add(new JLabel(label), labelConstraints);
        form.add(field, fieldConstraints);
    }

    private CarModel selectedModel() {
        return (CarModel) modelBox.getSelectedItem();
    }

    private CarColor selectedColor() {
        return (CarColor) colorBox.getSelectedItem();
    }

    private Engine selectedEngine() {
        return (Engine) engineBox.getSelectedItem();
    }

    private Wheels selectedWheels() {
        return (Wheels) wheelsBox.getSelectedItem();
    }

    private Interior selectedInterior() {
        return (Interior) interiorBox.getSelectedItem();
    }

    private String carSummary(Car car) {
        return String.format("%s | %s | %s",
                car.getModel().getDisplayName(),
                car.getColor().getDisplayName(),
                formatCurrency(car.getTotalPrice()));
    }

    private String formatCurrency(double value) {
        return String.format("$%,.0f", value);
    }

    private String nameOrNull(String value) {
        String trimmed = value == null ? "" : value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}