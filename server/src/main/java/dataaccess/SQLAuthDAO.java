package dataaccess;

import model.AuthData;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void addAuthData(AuthData authData) {

    }

    @Override
    public AuthData findAuthData(String authToken) {
        return null;
    }

    @Override
    public void deleteAuthData(String authToken) {

    }
}
