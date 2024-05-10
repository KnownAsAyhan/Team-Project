package repository;

import java.io.*;
import java.util.*;

import model.User;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users;
    private String userFilePath; // File path for storing user registration information

    public InMemoryUserRepository(String userFilePath) {
        this.users = new ArrayList<>();
        this.userFilePath = userFilePath;
        loadUsersFromFile(); // Load users from CSV file when the repository is initialized
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> foundUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
        return foundUser.orElse(null);
    }

    @Override
    public void save(User user) {
        users.add(user);
        saveUsersToFile(); // Save users to CSV file after adding a new user
    }

    @Override
    public void delete(User user) {
        users.remove(user);
        saveUsersToFile(); // Save users to CSV file after deleting a user
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Assuming CSV format: username,password
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
