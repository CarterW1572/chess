package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO {

    private final Gson serializer = new Gson();

    public SQLGameDAO() {
        try {
            String[] createGameTable = {
                    """
            CREATE TABLE IF NOT EXISTS gameData (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """
            };
            SQLUserDAO.configureDatabase(createGameTable);
        }
        catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE gameData";
        executeUpdate(statement);
    }

    @Override
    public void addGame(GameData game) throws DataAccessException {
        var gameID = game.gameID();
        var whiteUsername = game.whiteUsername();
        var blackUsername = game.blackUsername();
        var gameName = game.gameName();
        var chessGame = serializer.toJson(game.game());
        var statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, game) " +
                "VALUES (?, ?, ?, ?, ?)";
        executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    @Override
    public GameData findGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game" +
                    " FROM gameData WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGameData(rs);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException {
        var gameData = findGame(gameID);
        GameData newGame;
        if (color == ChessGame.TeamColor.WHITE) {
            newGame = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(),
                    gameData.game());
        }
        else {
            newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(),
                    gameData.game());
        }
        var statement = "DELETE FROM gameData WHERE gameID=?";
        executeUpdate(statement, gameID);
        addGame(newGame);
    }

    @Override
    public HashMap<Integer, GameData> getAllGames() throws DataAccessException {
        var games = new HashMap<Integer, GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gameData";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var gameData = readGameData(rs);
                        games.put(gameData.gameID(), gameData);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

    private GameData readGameData(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var game = rs.getString("game");
        var chessGame = new Gson().fromJson(game, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) {
                        ps.setString(i + 1, p);
                    }
                    else if (param instanceof Integer p) {
                        ps.setInt(i + 1, p);
                    }
                    else if (param instanceof ChessGame p) {
                        ps.setString(i + 1, p.toString());
                    }
                    else if (param == null) {
                        ps.setNull(i + 1, NULL);
                    }
                }
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
