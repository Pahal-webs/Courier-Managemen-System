package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DeleteCourier extends JFrame {

    JTextField searchField;
    JLabel senderLabel, receiverLabel, typeLabel, weightLabel, statusLabel;

    Courier selectedCourier;

    Color navy = new Color(20, 50, 90);
    Color background = new Color(240, 244, 250);

    JButton deleteButton;

    public DeleteCourier() {

        setTitle("Delete Courier");
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

        JLabel title = new JLabel("DELETE COURIER");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        mainPanel.add(title, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(background);

        JPanel card = new JPanel(new GridLayout(6, 2, 20, 20));
        card.setPreferredSize(new Dimension(650, 350));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 215)),
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));

        searchField = new JTextField(15);

        JButton searchButton = createButton("SEARCH", navy);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        card.add(createLabel("Courier ID:"));
        card.add(searchPanel);

        senderLabel = new JLabel("-");
        receiverLabel = new JLabel("-");
        typeLabel = new JLabel("-");
        weightLabel = new JLabel("-");
        statusLabel = new JLabel("-");

        card.add(createLabel("Sender Name:"));
        card.add(senderLabel);

        card.add(createLabel("Receiver Name:"));
        card.add(receiverLabel);

        card.add(createLabel("Courier Type:"));
        card.add(typeLabel);

        card.add(createLabel("Weight (kg):"));
        card.add(weightLabel);

        card.add(createLabel("Delivery Status:"));
        card.add(statusLabel);

        centerPanel.add(card);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        deleteButton = createButton("DELETE COURIER",
                new Color(170, 45, 45));

        JButton clearButton = createButton("CLEAR",
                new Color(90, 100, 115));

        JButton backButton = createButton("BACK", navy);

        deleteButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);
        buttonPanel.add(deleteButton);
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

        // Search Action
        searchButton.addActionListener(e -> searchCourier());

        searchField.addActionListener(e -> searchCourier());

        // Delete Action - MySQL Connectivity
        deleteButton.addActionListener(e -> {

            if (selectedCourier == null) {
                JOptionPane.showMessageDialog(this,
                        "Please search for a courier first.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this courier?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                boolean deleted = CourierDAO.deleteCourier(
                        selectedCourier.courierId
                );

                if (deleted) {

                    JOptionPane.showMessageDialog(this,
                            "Courier deleted successfully!");

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Unable to delete courier. Please check database connection.");
                }
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

            clearDetails();
            return;
        }

        senderLabel.setText(selectedCourier.senderName);
        receiverLabel.setText(selectedCourier.receiverName);
        typeLabel.setText(selectedCourier.courierType);
        weightLabel.setText(selectedCourier.weight);
        statusLabel.setText(selectedCourier.deliveryStatus);

        deleteButton.setEnabled(true);
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(navy);

        return label;
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(165, 40));

        return button;
    }

    private void clearDetails() {

        senderLabel.setText("-");
        receiverLabel.setText("-");
        typeLabel.setText("-");
        weightLabel.setText("-");
        statusLabel.setText("-");

        selectedCourier = null;
        deleteButton.setEnabled(false);
    }

    private void clearFields() {

        searchField.setText("");
        clearDetails();
    }
}