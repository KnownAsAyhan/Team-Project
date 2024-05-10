package repository;

import model.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
    void delete(User user);
}