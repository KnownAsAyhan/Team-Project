package services;

import model.User;
import repository.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(String username, String password) {
        // Check if the username already exists
        if (userRepository.findByUsername(username) != null) {
            return false; // Registration failed - username already exists
        }

        // Create a new user and save it to the repository
        User newUser = new User(username, password);
        userRepository.save(newUser);
        return true; // Registration successful
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return the authenticated user
        }
        return null; // Return null if authentication fails
    }
}