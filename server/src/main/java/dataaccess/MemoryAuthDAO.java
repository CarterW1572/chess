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
}
