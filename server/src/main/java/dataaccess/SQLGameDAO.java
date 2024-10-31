package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;

public class SQLGameDAO implements GameDAO {
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void addGame(GameData game) {

    }

    @Override
    public GameData findGame(int gameID) {
        return null;
    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor color) {

    }

    @Override
    public HashMap<Integer, GameData> getAllGames() {
        return null;
    }
}
