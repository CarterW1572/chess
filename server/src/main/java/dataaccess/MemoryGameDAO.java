package dataaccess;

import model.GameData;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    private final HashMap<Integer, GameData> games;

    public MemoryGameDAO() {
        games = new HashMap<>();
    }

    public void clear() {
        games.clear();
    }

    public void addGame(GameData game) {
        games.put(game.gameID(), game);
    }

    public GameData findGame(int gameID) {
        return games.get(gameID);
    }
}
