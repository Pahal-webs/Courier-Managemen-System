package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;

public class AddCourier extends JFrame {

    JTextField senderNameField, senderAddressField, senderPhoneField;
    JTextField receiverNameField, receiverAddressField, receiverPhoneField;
    JTextField weightField, dateField;

    JComboBox<String> typeBox, statusBox;

    public AddCourier() {

        setTitle("Add Courier");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color navy = new Color(20, 50, 90);
        Color background = new Color(240, 244, 250);

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 125));

        URL logoURL = getClass().getResource("PIETlogo.jpeg");

        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image image = icon.getImage().getScaledInstance(
                    400, 90, Image.SCALE_SMOOTH);
            header.add(new JLabel(new ImageIcon(image)));
        }

        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        JLabel title = new JLabel("ADD NEW COURIER");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        mainPanel.add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 215)),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        senderNameField = new JTextField(18);
        senderAddressField = new JTextField(18);
        senderPhoneField = new JTextField(18);

        receiverNameField = new JTextField(18);
        receiverAddressField = new JTextField(18);
        receiverPhoneField = new JTextField(18);

        weightField = new JTextField(18);

        dateField = new JTextField(LocalDate.now().toString(), 18);
        dateField.setEditable(false);

        typeBox = new JComboBox<>(new String[]{
                "Document", "Parcel", "Express", "Standard"
        });

        statusBox = new JComboBox<>(new String[]{
                "Booked", "In Transit", "Out for Delivery", "Delivered"
        });

        int row = 0;

        addPair(formPanel, gbc, row++, "Sender Name:", senderNameField,
                "Sender Phone:", senderPhoneField);

        addPair(formPanel, gbc, row++, "Sender Address:", senderAddressField,
                "Booking Date:", dateField);

        addPair(formPanel, gbc, row++, "Receiver Name:", receiverNameField,
                "Receiver Phone:", receiverPhoneField);

        addPair(formPanel, gbc, row++, "Receiver Address:", receiverAddressField,
                "Courier Type:", typeBox);

        addPair(formPanel, gbc, row++, "Weight (kg):", weightField,
                "Delivery Status:", statusBox);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(background);
        centerPanel.add(formPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        buttonPanel.setBackground(background);

        JButton saveButton = createButton("SAVE COURIER", navy);
        JButton clearButton = createButton("CLEAR", new Color(90, 100, 115));
        JButton backButton = createButton("BACK", new Color(170, 45, 45));

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Project Made By: Pahal Jain | B3 Batch");

        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(navy);
        footerPanel.add(footer);

        add(footerPanel, BorderLayout.SOUTH);

        // Save Button - MySQL Connectivity
        saveButton.addActionListener(e -> {

            String senderName = senderNameField.getText().trim();
            String senderAddress = senderAddressField.getText().trim();
            String senderPhone = senderPhoneField.getText().trim();

            String receiverName = receiverNameField.getText().trim();
            String receiverAddress = receiverAddressField.getText().trim();
            String receiverPhone = receiverPhoneField.getText().trim();

            String weight = weightField.getText().trim();

            if (senderName.isEmpty() || senderAddress.isEmpty()
                    || senderPhone.isEmpty() || receiverName.isEmpty()
                    || receiverAddress.isEmpty() || receiverPhone.isEmpty()
                    || weight.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all the fields.");
                return;
            }

            if (!senderPhone.matches("\\d{10}")
                    || !receiverPhone.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(this,
                        "Phone numbers must contain exactly 10 digits.");
                return;
            }

            double weightValue;

            try {
                weightValue = Double.parseDouble(weight);

                if (!Double.isFinite(weightValue) || weightValue <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Weight must be a positive number.");
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid weight.");
                return;
            }

            String courierId = CourierDAO.getNextCourierId();

            if (courierId == null) {
                JOptionPane.showMessageDialog(this,
                        "Unable to generate Courier ID. Check database connection.");
                return;
            }

            Courier courier = new Courier(
                    courierId,
                    senderName,
                    senderAddress,
                    senderPhone,
                    receiverName,
                    receiverAddress,
                    receiverPhone,
                    typeBox.getSelectedItem().toString(),
                    String.valueOf(weightValue),
                    LocalDate.now().toString(),
                    statusBox.getSelectedItem().toString()
            );

            boolean saved = CourierDAO.addCourier(courier);

            if (saved) {

                JOptionPane.showMessageDialog(this,
                        "Courier Added Successfully!\nCourier ID: " + courierId);

                clearFields();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Failed to add courier. Please check database connection.");
            }
        });

        // Clear Button
        clearButton.addActionListener(e -> clearFields());

        // Back Button
        backButton.addActionListener(e -> {
            new DashboardFrame();
            dispose();
        });

        setVisible(true);
    }

    private void addPair(JPanel panel, GridBagConstraints gbc,
                         int row, String label1, JComponent field1,
                         String label2, JComponent field2) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createLabel(label1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createLabel(label2), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        panel.add(field2, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(35, 45, 60));
        return label;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 40));
        return button;
    }

    private void clearFields() {
        senderNameField.setText("");
        senderAddressField.setText("");
        senderPhoneField.setText("");

        receiverNameField.setText("");
        receiverAddressField.setText("");
        receiverPhoneField.setText("");

        weightField.setText("");

        dateField.setText(LocalDate.now().toString());

        typeBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
    }
}