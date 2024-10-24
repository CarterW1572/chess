package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.HashMap;

public interface GameDAO {
    void clear() throws DataAccessException;
    void addGame(GameData game);
    GameData findGame(int gameID);
    void updateGame(int gameID, String username, ChessGame.TeamColor color);
    HashMap<Integer, GameData> getAllGames();
}
