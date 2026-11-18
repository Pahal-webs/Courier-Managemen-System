package couriermanagement;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Courier Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 244, 250));

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1000, 180));
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        URL logoURL = getClass().getResource("PIETlogo.jpeg");

        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            Image image = originalIcon.getImage().getScaledInstance(
                    500, 112, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(image));
            header.add(logoLabel);
        } else {
            JLabel logoError = new JLabel(
                    "Poornima Institute of Engineering and Technology");
            logoError.setFont(new Font("Arial", Font.BOLD, 22));
            header.add(logoError);
        }

        mainPanel.add(header, BorderLayout.NORTH);

        // Login Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 244, 250));

        JPanel loginBox = new JPanel(null);
        loginBox.setPreferredSize(new Dimension(420, 350));
        loginBox.setBackground(Color.WHITE);
        loginBox.setBorder(BorderFactory.createLineBorder(
                new Color(190, 200, 215), 1));

        JLabel title = new JLabel("COURIER MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(20, 50, 90));
        title.setBounds(25, 25, 370, 35);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(title);

        JLabel subtitle = new JLabel("ADMIN LOGIN");
        subtitle.setFont(new Font("Arial", Font.BOLD, 16));
        subtitle.setBounds(25, 75, 370, 30);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(subtitle);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(45, 125, 320, 25);
        loginBox.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField.setBounds(45, 155, 320, 35);
        loginBox.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setBounds(45, 205, 320, 25);
        loginBox.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setBounds(45, 235, 320, 35);
        loginBox.add(passwordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(20, 50, 90));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(45, 290, 150, 38);
        loginBox.add(loginButton);

        JButton clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBounds(215, 290, 150, 38);
        loginBox.add(clearButton);

        formPanel.add(loginBox);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "Project Made By: Pahal Jain | B3 Batch");
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 13));
        footer.setForeground(Color.WHITE);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(20, 50, 90));
        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Login Button Action - MySQL Connectivity
        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please enter username and password.");
            }

            else if (username.equals("admin")
                    && CourierDAO.verifyAdminPassword(password)) {

                JOptionPane.showMessageDialog(this,
                        "Login Successful!");

                new DashboardFrame();
                dispose();
            }

            else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password!");
            }
        });

        // Clear Button Action
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        setVisible(true);
    }
}