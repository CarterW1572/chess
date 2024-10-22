package dataaccess;

import model.*;

public interface GameDAO {
    void clear() throws DataAccessException;
    void addGame(GameData game);
    GameData findGame(int gameID);
}
