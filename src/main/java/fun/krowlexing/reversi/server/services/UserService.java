package fun.krowlexing.reversi.server.services;

import fun.krowlexing.reversi.server.exceptions.PersistenceException;
import fun.krowlexing.reversi.server.exceptions.UserExistsException;
import fun.krowlexing.reversi.server.repos.UserRepository;

import java.sql.SQLException;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void create(String username, String password) throws PersistenceException, UserExistsException {
        try {
            var exists = repo.exists(username);
            if (exists) {
                repo.insert(username, password);
            } else {
                throw new UserExistsException();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to create user: " + e.getMessage());
        }
    }

    public int getId(String username, String password) throws PersistenceException{
        try {
            return repo.getId(username, password);
        } catch (SQLException e) {
            throw new PersistenceException("Failed to check user: " + e.getMessage());
        }
    }
}
