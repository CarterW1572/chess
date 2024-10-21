package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import server.BadRequestException;

import java.util.Random;

import javax.xml.crypto.Data;

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
            throw new BadRequestException("Error: bad request");
        }
        if (userDAO.findUserData(user) == null) {
            String username = user.username();
            String authToken = generateAuthToken();
            return new AuthData(authToken, username);
        }
        else {
            throw new DataAccessException("Error: already taken");
        }
    }
}
