package dataaccess;

import chess.ChessGame;
import model.GameData;
import requestObjects.JoinGameRequest;

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

    public void updateGame(int gameID, String username, ChessGame.TeamColor color) {
        var game = games.get(gameID);
        if (color == ChessGame.TeamColor.WHITE) {
            var blackUsername = game.blackUsername();
            var gameName = game.gameName();
            var chessGame = game.game();
            var newGame = new GameData(gameID, username, blackUsername, gameName, chessGame);
            games.remove(gameID);
            games.put(gameID, newGame);
        }
        else {
            var whiteUsername = game.whiteUsername();
            var gameName = game.gameName();
            var chessGame = game.game();
            var newGame = new GameData(gameID, whiteUsername, username, gameName, chessGame);
            games.remove(gameID);
            games.put(gameID, newGame);
        }
    }
}
