package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void addAuthData(AuthData authData) throws DataAccessException;
    AuthData findAuthData(String authToken) throws DataAccessException;
    void deleteAuthData(String authToken) throws DataAccessException;
}
