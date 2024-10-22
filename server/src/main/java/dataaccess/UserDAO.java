package dataaccess;

import model.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    void addUserData(UserData userData);
    void addAuthData(AuthData authData);
    UserData findUserData(String username);
    AuthData findAuthData(String authToken);
    void deleteAuthData(String authToken);
}
