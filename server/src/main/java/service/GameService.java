package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;
import requestObjects.CreateGameRequest;
import resultObjects.CreateGameResult;
import java.util.Random;

public class GameService {
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public CreateGameResult createGame(String authToken, CreateGameRequest createGameRequest) {
        if (authDAO.findAuthData(authToken) == null) {
            throw new UnauthorizedException("{ \"message\": \"Error: unauthorized\" }");
        }
        Random random = new Random();
        String gameName = createGameRequest.gameName();
        int gameID = random.nextInt(Integer.MAX_VALUE);
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(gameID, null, null, gameName, game);
        gameDAO.addGame(gameData);
        return new CreateGameResult(gameID);
    }
}
