package dataaccess;

import model.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    void addUserData(UserData userData);
    UserData findUserData(UserData userData);
}
