package dataaccess;

import model.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    void addUserData(UserData userData) throws DataAccessException;
    UserData findUserData(String username) throws DataAccessException;
}
