package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

    private HashMap<String, UserData> users;

    public MemoryUserDAO() {
        users = new HashMap<>();
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
