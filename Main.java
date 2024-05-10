import repository.UserRepository;
import repository.InMemoryUserRepository;
import services.UserService;
import services.UserServiceImpl;  
import FrontendAndLoginRegister.LoginFrame;
import FrontendAndLoginRegister.RegisterFrame;  
import GeneralDB.GeneralDatabase;
import PersonalDB.PersonalDatabase;

public class Main {
    // In Main class
public static void main(String[] args) {
    String userFilePath = "users.csv"; 
    UserRepository userRepository = new InMemoryUserRepository(userFilePath);
    UserService userService = new UserServiceImpl(userRepository);
    
    // Create placeholder objects for PersonalDatabase and GeneralDatabase
    PersonalDatabase personalDatabase = new PersonalDatabase();
    GeneralDatabase 
    generalDatabase = new GeneralDatabase();

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            // Pass placeholder objects to LoginFrame constructor
            new LoginFrame(userService, personalDatabase, generalDatabase).setVisible(true);
        }
    });
}

}