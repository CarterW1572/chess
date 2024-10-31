package dataaccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataaccess.SQLUserDAO;
import model.UserData;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE authData";
        SQLUserDAO.executeUpdate(statement);
    }

    @Override
    public void addAuthData(AuthData authData) throws DataAccessException {
        var authToken = authData.authToken();
        var username = authData.username();
        var statement = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
        SQLUserDAO.executeUpdate(statement, authToken, username);
    }

    @Override
    public AuthData findAuthData(String authToken) {
        return null;
    }

    @Override
    public void deleteAuthData(String authToken) {

    }

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken, username);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """
    };

    private void configureDatabase() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
