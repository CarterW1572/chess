package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import server.BadRequestException;

import java.util.Random;

public class UserService {
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    private String generateAuthToken() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder authToken = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            authToken.append(randomChar);
        }
        return authToken.toString();
    }

    public AuthData register(UserData user) throws DataAccessException {
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestException("{ \"message\": \"Error: bad request\" }");
        }
        if (userDAO.findUserData(user.username()) == null) {
            String username = user.username();
            String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
            UserData newUser = new UserData(user.username(), hashedPassword, user.email());
            String authToken = generateAuthToken();
            userDAO.addUserData(newUser);
            var authData = new AuthData(authToken, username);
            authDAO.addAuthData(authData);
            return authData;
        }
        else {
            throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
        }
    }

    public AuthData login(LoginRequest loginReq) throws DataAccessException {
        if (userDAO.findUserData(loginReq.username()) != null &&
                BCrypt.checkpw(loginReq.password(), userDAO.findUserData(loginReq.username()).password())) {
            String authToken = generateAuthToken();
            var authData = new AuthData(authToken, loginReq.username());
            authDAO.addAuthData(authData);
            return authData;
        }
        throw new UnauthorizedException("{ \"message\": \"Error: unauthorized\" }");
    }

    public void logout(String authToken) throws DataAccessException {
        if (authDAO.findAuthData(authToken) == null) {
            throw new UnauthorizedException("{ \"message\": \"Error: unauthorized\" }");
        }
        authDAO.deleteAuthData(authToken);
    }
}
