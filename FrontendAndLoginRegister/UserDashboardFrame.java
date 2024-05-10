package FrontendAndLoginRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import PersonalDB.*;
import GeneralDB.*;
import services.*;

public class UserDashboardFrame extends JFrame {
    private JButton logoutButton;
    private JButton personalDatabaseButton;
    private JButton generalDatabaseButton;
    private UserService userService;
    private PersonalDatabase personalDatabase;
    private GeneralDatabase generalDatabase;

    // Constructor that takes UserService, PersonalDatabase, and GeneralDatabase as parameters
    public UserDashboardFrame(UserService userService, PersonalDatabase personalDatabase, GeneralDatabase generalDatabase) {
        super("Dashboard");
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
        logoutButton = new JButton("Logout");
        personalDatabaseButton = new JButton("Personal Database");
        generalDatabaseButton = new JButton("General Database");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UserDashboardFrame.this, "Logged out successfully");
                dispose(); // Close the dashboard frame
                // Redirect to login frame with userService
                LoginFrame loginFrame = new LoginFrame(userService, personalDatabase, generalDatabase);
                loginFrame.setVisible(true);
            }
        });

        personalDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (personalDatabase != null) {
                    PersonalDatabaseGUI personalDatabaseGUI = new PersonalDatabaseGUI(personalDatabase);
                } else {
                    JOptionPane.showMessageDialog(UserDashboardFrame.this, "Personal Database is not available.");
                }
            }
        });
        
        generalDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (generalDatabase != null && personalDatabase != null) {
                    GeneralDatabaseGUI generalDatabaseGUI = new GeneralDatabaseGUI(generalDatabase, personalDatabase,false);
                } else {
                    JOptionPane.showMessageDialog(UserDashboardFrame.this, "General Database is not available.");
                }
            }
        });

        
    }

    private void addComponentsToPanel() {
        JPanel dashboardPanel = new JPanel(new GridLayout(3, 1));
        dashboardPanel.add(logoutButton);
        dashboardPanel.add(personalDatabaseButton);
        dashboardPanel.add(generalDatabaseButton);

        add(dashboardPanel);
    }
}