package dataaccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() {
        try {
            String[] createAuthTable = {
                    """
            CREATE TABLE IF NOT EXISTS authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
            """
            };
            SQLUserDAO.configureDatabase(createAuthTable);
        }
        catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

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
    public AuthData findAuthData(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT authToken, username FROM authData WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuthData(rs);
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
    public void deleteAuthData(String authToken) throws DataAccessException {
        var statement = "DELETE FROM authData WHERE authToken=?";
        SQLUserDAO.executeUpdate(statement, authToken);
    }

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken, username);
    }

    /*private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createAuthTable) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }*/
}
