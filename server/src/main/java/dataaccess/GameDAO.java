package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.HashMap;

public interface GameDAO {
    void clear() throws DataAccessException;
    void addGame(GameData game) throws DataAccessException;
    GameData findGame(int gameID) throws DataAccessException;
    void updateGame(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException;
    HashMap<Integer, GameData> getAllGames() throws DataAccessException;
}
