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

    public void addAuthData(AuthData authData) {
        authTokens.put(authData.authToken(), authData);
    }

    public UserData findUserData(String username) {
        return users.get(username);
    }

    public AuthData findAuthData(String authToken) {
        return authTokens.get(authToken);
    }

    public void deleteAuthData(String authToken) {
        authTokens.remove(authToken);
    }
}
