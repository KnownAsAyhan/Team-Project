package FrontendAndLoginRegister;

import javax.swing.*;

import GeneralDB.GeneralDatabase;
import PersonalDB.PersonalDatabase;
import services.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private UserService userService;
    private PersonalDatabase personalDatabase;
    private GeneralDatabase generalDatabase;

    public RegisterFrame(UserService userService, PersonalDatabase personalDatabase, GeneralDatabase generalDatabase) {
        super("Register");
        this.userService = userService;
        this.personalDatabase = personalDatabase;
        this.generalDatabase = generalDatabase;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false); // Prevent resizing

        initComponents();
        addComponentsToPanel();
    }

    private void initComponents() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean registered = userService.register(username, password);
                if (registered) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration successful for username: " + username);
                    dispose(); // Close the register window
                    LoginFrame loginFrame = new LoginFrame(userService, personalDatabase, generalDatabase); // Open the login window
                    loginFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration failed. Username already exists.");
                }
            }
        });
    }

    private void addComponentsToPanel() {
        JPanel registerPanel = new JPanel(new GridLayout(3, 1));
        registerPanel.add(new JLabel("Username:"));
        registerPanel.add(usernameField);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);

        registerPanel.add(buttonPanel);

        add(registerPanel);
    }
}