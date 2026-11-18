package couriermanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewCourier extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField searchField;

    Color navy = new Color(20, 50, 90);
    Color background = new Color(240, 244, 250);

    public ViewCourier() {

        setTitle("View Courier");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 125));

        java.net.URL logoURL = getClass().getResource("PIETlogo.jpeg");

        if (logoURL != null) {
            ImageIcon icon = new ImageIcon(logoURL);
            Image image = icon.getImage().getScaledInstance(
                    400, 90, Image.SCALE_SMOOTH);
            header.add(new JLabel(new ImageIcon(image)));
        }

        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 15, 35));

        JLabel title = new JLabel("COURIER RECORDS");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(title, BorderLayout.NORTH);

        // Search Panel
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton searchButton = createButton("SEARCH", navy);
        JButton viewAllButton = createButton("VIEW ALL", navy);
        JButton refreshButton = createButton("REFRESH", new Color(90, 100, 115));
        JButton backButton = createButton("BACK", new Color(170, 45, 45));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchPanel.setBackground(background);

        searchPanel.add(new JLabel("Search by Courier ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(viewAllButton);
        searchPanel.add(refreshButton);

        // Table
        String[] columns = {
                "Courier ID", "Sender", "Receiver",
                "Type", "Weight (kg)", "Booking Date", "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(navy);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 215)));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));
        centerPanel.setBackground(background);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Back Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Project Made By: Pahal Jain | B3 Batch");
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(navy);
        footerPanel.setPreferredSize(new Dimension(1000, 45));
        footerPanel.add(footer);

        add(footerPanel, BorderLayout.SOUTH);

        // Button Actions
        searchButton.addActionListener(e -> searchCourier());

        searchField.addActionListener(e -> searchCourier());

        viewAllButton.addActionListener(e -> loadAllCouriers());

        refreshButton.addActionListener(e -> {
            searchField.setText("");
            loadAllCouriers();
        });

        backButton.addActionListener(e -> {
            new DashboardFrame();
            dispose();
        });

        loadAllCouriers();

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 38));

        return button;
    }

    // Load All Couriers from MySQL
    private void loadAllCouriers() {

        model.setRowCount(0);

        java.util.ArrayList<Courier> list = CourierDAO.getAllCouriers();

        for (Courier courier : list) {
            addCourierRow(courier);
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No courier records found.");
        }
    }

    // Search Courier from MySQL
    private void searchCourier() {

        String id = searchField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a Courier ID.");
            return;
        }

        model.setRowCount(0);

        Courier courier = CourierDAO.getCourierById(id);

        if (courier != null) {

            addCourierRow(courier);

        } else {

            JOptionPane.showMessageDialog(this,
                    "Courier record not found.");
        }
    }

    // Add Record to Table
    private void addCourierRow(Courier courier) {

        model.addRow(new Object[]{
                courier.courierId,
                courier.senderName,
                courier.receiverName,
                courier.courierType,
                courier.weight,
                courier.bookingDate,
                courier.deliveryStatus
        });
    }
}