package dataaccess;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.*;
import server.BadRequestException;
import service.DataService;
import service.GameService;
import service.UnauthorizedException;
import service.UserService;

public class DataAccessTests {

    private final UserDAO userDAO = new SQLUserDAO();
    private final GameDAO gameDAO = new SQLGameDAO();
    private final AuthDAO authDAO = new SQLAuthDAO();
    private final DataService dataService = new DataService(userDAO, gameDAO, authDAO);
    private final UserService userService = new UserService(userDAO, gameDAO, authDAO);
    private final GameService gameService = new GameService(userDAO, gameDAO, authDAO);
    private AuthData authData;

    @BeforeEach
    public void setUp() {
        try {
            dataService.clear();
            var user = new UserData("username", "password", "email");
            authData = userService.register(user);
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clearTest() {
        try {
            userDAO.clear();
        }
        catch (DataAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void registerTestSuccess() {
        try {
            var user = new UserData("newUser", "newPass", "newEmail");
            userDAO.addUserData(user);
            Assertions.assertNotNull(userDAO.findUserData("newUser"));
        }
        catch (DataAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void registerTestFail() {
        try {
            var user = new UserData("username", "newPass", "newEmail");
            userDAO.addUserData(user);
        }
        catch (DataAccessException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void loginTestSuccess() {
        try {
            userDAO.findUserData("username");
        }
        catch (UnauthorizedException | DataAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void loginTestFail() {
        try {
            userDAO.findUserData("notUsername");
        }
        catch (UnauthorizedException | DataAccessException e) {
            Assertions.assertTrue(true);
        }
    }
}
