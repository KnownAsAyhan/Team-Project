package FrontendAndLoginRegister;

import javax.swing.*;

import GeneralDB.GeneralDatabase;
import PersonalDB.PersonalDatabase;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import services.*;

public class LoginFrame extends JFrame {
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;
    private PersonalDatabase personalDatabase;
    private GeneralDatabase generalDatabase;

    public LoginFrame(UserService userService, PersonalDatabase personalDatabase, GeneralDatabase generalDatabase) {
        super("Login");
        this.userService = userService;
        this.personalDatabase = personalDatabase;
        this.generalDatabase = generalDatabase;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false); // Prevent resizing

        initComponents();
        addComponentsToPanel();
        add(loginPanel);
    }

    private void initComponents() {
        loginPanel = new JPanel(new GridLayout(3, 1));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = userService.authenticate(username, password);

                if (user != null) {
                    personalDatabase.setUser(username); // Set the user and load data
                    dispose(); // Close the login frame

                    UserDashboardFrame userDashboard = new UserDashboardFrame(userService, personalDatabase, generalDatabase);
                    userDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login failed. Please check your credentials.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame registerFrame = new RegisterFrame(userService, personalDatabase, generalDatabase);
                registerFrame.setVisible(true);
                dispose(); // Close the login frame
            }
        });
    }

    private void addComponentsToPanel() {
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginPanel.add(buttonPanel);
    }
}