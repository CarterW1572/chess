package dataaccess;

import model.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    boolean findUserData(UserData userData) throws DataAccessException;
}
