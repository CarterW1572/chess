package dataaccess;

import model.*;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

    private final HashMap<String, UserData> users;
    private final HashMap<String, AuthData> authTokens;

    public MemoryUserDAO() {
        users = new HashMap<>();
        authTokens = new HashMap<>();
    }

    public void clear() {
        users.clear();
    }

    public void addUserData(UserData userData) {
        users.put(userData.username(), userData);
    }

    public UserData findUserData(String username) {
        return users.get(username);
    }
}
