package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ChangePassword extends JFrame {

    JPasswordField currentPasswordField;
    JPasswordField newPasswordField;
    JPasswordField confirmPasswordField;

    Color navy = new Color(20, 50, 90);
    Color background = new Color(240, 244, 250);

    public ChangePassword() {

        setTitle("Change Password");
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

        JLabel title = new JLabel("CHANGE PASSWORD");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(navy);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));

        mainPanel.add(title, BorderLayout.NORTH);

        // Password Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(500, 330));
        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 200, 215)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        currentPasswordField = new JPasswordField(18);
        newPasswordField = new JPasswordField(18);
        confirmPasswordField = new JPasswordField(18);

        addField(formPanel, gbc, 0,
                "Current Password:", currentPasswordField);

        addField(formPanel, gbc, 1,
                "New Password:", newPasswordField);

        addField(formPanel, gbc, 2,
                "Confirm Password:", confirmPasswordField);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(background);
        centerPanel.add(formPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JButton changeButton = createButton(
                "CHANGE PASSWORD", navy);

        JButton clearButton = createButton(
                "CLEAR", new Color(90, 100, 115));

        JButton backButton = createButton(
                "BACK", new Color(170, 45, 45));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);

        buttonPanel.add(changeButton);
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

        // Change Password Action - MySQL Connectivity
        changeButton.addActionListener(e -> {

            String currentPassword =
                    new String(currentPasswordField.getPassword());

            String newPassword =
                    new String(newPasswordField.getPassword());

            String confirmPassword =
                    new String(confirmPasswordField.getPassword());

            if (currentPassword.isEmpty()
                    || newPassword.isEmpty()
                    || confirmPassword.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all the fields.");
            }

            else if (!CourierDAO.verifyAdminPassword(currentPassword)) {

                JOptionPane.showMessageDialog(this,
                        "Current password is incorrect.");
            }

            else if (newPassword.length() < 4) {

                JOptionPane.showMessageDialog(this,
                        "New password must contain at least 4 characters.");
            }

            else if (!newPassword.equals(confirmPassword)) {

                JOptionPane.showMessageDialog(this,
                        "New passwords do not match.");
            }

            else {

                boolean changed =
                        CourierDAO.updateAdminPassword(newPassword);

                if (changed) {

                    JOptionPane.showMessageDialog(this,
                            "Password changed successfully!");

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Unable to change password. Please try again.");
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

    private void addField(JPanel panel, GridBagConstraints gbc,
                          int row, String labelText,
                          JPasswordField field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(field, gbc);
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

    private void clearFields() {

        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}