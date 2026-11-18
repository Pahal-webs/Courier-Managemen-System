package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("Courier Management System - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color navy = new Color(20, 50, 90);
        Color background = new Color(240, 244, 250);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // Header Panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 145));

        URL logoURL = getClass().getResource("PIETlogo.jpeg");

        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            Image image = originalIcon.getImage().getScaledInstance(
                    420, 95, Image.SCALE_SMOOTH);

            JLabel logoLabel = new JLabel(new ImageIcon(image));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            header.add(logoLabel, BorderLayout.CENTER);
        } else {
            JLabel logoLabel = new JLabel(
                    "POORNIMA INSTITUTE OF ENGINEERING AND TECHNOLOGY");
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            header.add(logoLabel, BorderLayout.CENTER);
        }

        mainPanel.add(header, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(background);

        JLabel title = new JLabel("COURIER MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));

        JLabel welcome = new JLabel("Welcome, Admin!");
        welcome.setFont(new Font("Arial", Font.PLAIN, 19));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        JPanel headingPanel = new JPanel(new GridLayout(2, 1));
        headingPanel.setBackground(background);
        headingPanel.add(title);
        headingPanel.add(welcome);

        centerPanel.add(headingPanel, BorderLayout.NORTH);

        // Dashboard Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 25, 25));
        buttonPanel.setBackground(background);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 100, 50, 100));

        JButton addButton = createButton("ADD COURIER", navy);
        JButton viewButton = createButton("VIEW COURIER", navy);
        JButton updateButton = createButton("UPDATE COURIER", navy);
        JButton deleteButton = createButton("DELETE COURIER", navy);
        JButton passwordButton = createButton("CHANGE PASSWORD", navy);
        JButton logoutButton = createButton("LOGOUT", new Color(170, 45, 45));

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(passwordButton);
        buttonPanel.add(logoutButton);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Project Made By: Pahal Jain | B3 Batch");

        footer.setFont(new Font("Arial", Font.PLAIN, 14));
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(navy);
        footerPanel.setPreferredSize(new Dimension(1000, 45));
        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button Actions
        addButton.addActionListener(e -> new AddCourier());

        viewButton.addActionListener(e -> new ViewCourier());

        updateButton.addActionListener(e -> new UpdateCourier());

        deleteButton.addActionListener(e -> new DeleteCourier());

        passwordButton.addActionListener(e -> new ChangePassword());

        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame();
                dispose();
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 17));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20));

        return button;
    }
}