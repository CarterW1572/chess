package dataaccess;

import model.AuthData;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    private HashMap<String, AuthData> authTokens;

    public MemoryAuthDAO() {
        authTokens = new HashMap<>();
    }

    public void clear() {
        authTokens.clear();
    }

    public void addAuthData(AuthData authData) {
        authTokens.put(authData.authToken(), authData);
    }

    public AuthData findAuthData(String authToken) {
        return authTokens.get(authToken);
    }

    public void deleteAuthData(String authToken) {
        authTokens.remove(authToken);
    }
}
