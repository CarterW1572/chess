package dataaccess;

import model.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    UserData findUserData(UserData userData);
}
