package websocket.commands;

import chess.ChessMove;

import java.util.Objects;

public class MakeMoveCommand {
    private final String authToken;
    private final Integer gameID;
    private final ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        this.authToken = authToken;
        this.gameID = gameID;
        this.move = move;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MakeMoveCommand)) {
            return false;
        }
        MakeMoveCommand that = (MakeMoveCommand) o;
        return Objects.equals(getMove(), that.getMove()) &&
                Objects.equals(getAuthToken(), that.getAuthToken()) &&
                Objects.equals(getGameID(), that.getGameID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMove(), getAuthToken(), getGameID());
    }
}
