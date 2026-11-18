package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UpdateCourier extends JFrame {

    JTextField searchField;
    JTextField senderNameField, senderAddressField, senderPhoneField;
    JTextField receiverNameField, receiverAddressField, receiverPhoneField;
    JTextField weightField;

    JComboBox<String> typeBox, statusBox;

    Courier selectedCourier;

    Color navy = new Color(20, 50, 90);
    Color background = new Color(240, 244, 250);

    public UpdateCourier() {

        setTitle("Update Courier");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 120));

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

        JLabel title = new JLabel("UPDATE COURIER DETAILS");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        mainPanel.add(title, BorderLayout.NORTH);

        // Search Panel
        searchField = new JTextField(15);

        JButton searchButton = createButton("SEARCH", navy);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(background);
        searchPanel.add(new JLabel("Courier ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Form Fields
        senderNameField = new JTextField(18);
        senderAddressField = new JTextField(18);
        senderPhoneField = new JTextField(18);

        receiverNameField = new JTextField(18);
        receiverAddressField = new JTextField(18);
        receiverPhoneField = new JTextField(18);

        weightField = new JTextField(18);

        typeBox = new JComboBox<>(new String[]{
                "Document", "Parcel", "Express", "Standard"
        });

        statusBox = new JComboBox<>(new String[]{
                "Booked", "In Transit", "Out for Delivery", "Delivered"
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 215)),
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addPair(formPanel, gbc, 0, "Sender Name:", senderNameField,
                "Sender Phone:", senderPhoneField);

        addPair(formPanel, gbc, 1, "Sender Address:", senderAddressField,
                "Receiver Name:", receiverNameField);

        addPair(formPanel, gbc, 2, "Receiver Address:", receiverAddressField,
                "Receiver Phone:", receiverPhoneField);

        addPair(formPanel, gbc, 3, "Courier Type:", typeBox,
                "Weight (kg):", weightField);

        addPair(formPanel, gbc, 4, "Delivery Status:", statusBox,
                "", new JLabel(""));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));
        centerPanel.setBackground(background);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(background);
        formContainer.add(formPanel);

        centerPanel.add(formContainer, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JButton updateButton = createButton("UPDATE DETAILS", navy);
        JButton clearButton = createButton("CLEAR", new Color(90, 100, 115));
        JButton backButton = createButton("BACK", new Color(170, 45, 45));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Project Made By: Pahal Jain | B3 Batch");
        footer.setForeground(Color.WHITE);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(navy);
        footerPanel.setPreferredSize(new Dimension(1000, 40));
        footerPanel.add(footer);

        add(footerPanel, BorderLayout.SOUTH);

        // Initially disable update fields
        setFieldsEnabled(false);

        // Search Action
        searchButton.addActionListener(e -> searchCourier());

        searchField.addActionListener(e -> searchCourier());

        // Update Action - MySQL Connectivity
        updateButton.addActionListener(e -> {

            if (selectedCourier == null) {
                JOptionPane.showMessageDialog(this,
                        "Please search for a courier first.");
                return;
            }

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

            // Create updated courier object
            Courier updatedCourier = new Courier(
                    selectedCourier.courierId,
                    senderName,
                    senderAddress,
                    senderPhone,
                    receiverName,
                    receiverAddress,
                    receiverPhone,
                    typeBox.getSelectedItem().toString(),
                    String.valueOf(weightValue),
                    selectedCourier.bookingDate,
                    statusBox.getSelectedItem().toString()
            );

            // Update MySQL database
            boolean updated = CourierDAO.updateCourier(updatedCourier);

            if (updated) {

                selectedCourier = updatedCourier;

                JOptionPane.showMessageDialog(this,
                        "Courier details updated successfully!");

                clearFields();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Unable to update courier. Please check database connection.");
            }
        });

        // Clear Action
        clearButton.addActionListener(e -> clearFields());

        // Back Action
        backButton.addActionListener(e -> {
            new DashboardFrame();
            dispose();
        });

        setVisible(true);
    }

    // Search Courier from MySQL
    private void searchCourier() {

        String id = searchField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a Courier ID.");
            return;
        }

        selectedCourier = CourierDAO.getCourierById(id);

        if (selectedCourier == null) {

            JOptionPane.showMessageDialog(this,
                    "Courier record not found.");

            setFieldsEnabled(false);
            return;
        }

        senderNameField.setText(selectedCourier.senderName);
        senderAddressField.setText(selectedCourier.senderAddress);
        senderPhoneField.setText(selectedCourier.senderPhone);

        receiverNameField.setText(selectedCourier.receiverName);
        receiverAddressField.setText(selectedCourier.receiverAddress);
        receiverPhoneField.setText(selectedCourier.receiverPhone);

        weightField.setText(selectedCourier.weight);

        typeBox.setSelectedItem(selectedCourier.courierType);
        statusBox.setSelectedItem(selectedCourier.deliveryStatus);

        setFieldsEnabled(true);
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
        return label;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 38));
        return button;
    }

    private void setFieldsEnabled(boolean enabled) {

        senderNameField.setEnabled(enabled);
        senderAddressField.setEnabled(enabled);
        senderPhoneField.setEnabled(enabled);

        receiverNameField.setEnabled(enabled);
        receiverAddressField.setEnabled(enabled);
        receiverPhoneField.setEnabled(enabled);

        weightField.setEnabled(enabled);
        typeBox.setEnabled(enabled);
        statusBox.setEnabled(enabled);
    }

    private void clearFields() {

        searchField.setText("");

        senderNameField.setText("");
        senderAddressField.setText("");
        senderPhoneField.setText("");

        receiverNameField.setText("");
        receiverAddressField.setText("");
        receiverPhoneField.setText("");

        weightField.setText("");

        typeBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);

        selectedCourier = null;

        setFieldsEnabled(false);
    }
}