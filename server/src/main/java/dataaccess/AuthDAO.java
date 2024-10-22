package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void addAuthData(AuthData authData);
    AuthData findAuthData(String authToken);
    void deleteAuthData(String authToken);
}
