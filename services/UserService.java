package services;

import model.User;

public interface UserService {
    boolean register(String username, String password);
    User authenticate(String username, String password);
}