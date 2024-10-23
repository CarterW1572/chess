package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;
import requestObjects.CreateGameRequest;
import requestObjects.JoinGameRequest;
import resultObjects.CreateGameResult;
import resultObjects.ListGameResult;
import server.BadRequestException;

import java.util.ArrayList;
import java.util.HashMap;

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

    public CreateGameResult createGame(String authToken, CreateGameRequest createGameRequest) throws UnauthorizedException {
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

    public void joinGame(String authToken, JoinGameRequest joinGameRequest) throws UnauthorizedException, BadRequestException, DataAccessException {
        if (authDAO.findAuthData(authToken) == null) {
            throw new UnauthorizedException("{ \"message\": \"Error: unauthorized\" }");
        }
        GameData game = gameDAO.findGame(joinGameRequest.gameID());
        if (game == null || joinGameRequest.playerColor() == null) {
            throw new BadRequestException("{ \"message\": \"Error: bad request\" }");
        }
        if ((game.whiteUsername() != null && joinGameRequest.playerColor() == ChessGame.TeamColor.WHITE) ||
            (game.blackUsername() != null && joinGameRequest.playerColor() == ChessGame.TeamColor.BLACK)) {
            throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
        }
        gameDAO.updateGame(joinGameRequest.gameID(), authDAO.findAuthData(authToken).username(), joinGameRequest.playerColor());
    }

    public ArrayList<ListGameResult> listGames(String authToken) {
        if (authDAO.findAuthData(authToken) == null) {
            throw new UnauthorizedException("{ \"message\": \"Error: unauthorized\" }");
        }
        HashMap<Integer, GameData> games = gameDAO.getAllGames();
        var gameList = new ArrayList<ListGameResult>();
        for (GameData gameData : games.values()) {
            var gameID = gameData.gameID();
            var whiteUsername = gameData.whiteUsername();
            var blackUsername = gameData.blackUsername();
            var gameName = gameData.gameName();
            var newGameData = new ListGameResult(gameID, whiteUsername, blackUsername, gameName);
            gameList.add(newGameData);
        }
        return gameList;
    }
}
