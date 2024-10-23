package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingRule extends PieceRule {

    private final ChessGame.TeamColor teamColor;

    public KingRule(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (position.getRow() > 0 && position.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn())) == null ||
                        !board.getPiece(new ChessPosition(position.getRow(),
                        position.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn())));
            }
        }
        if (position.getRow() < 7 && position.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+2)) == null ||
                    !board.getPiece(new ChessPosition(position.getRow()+2,
                    position.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position,
                        new ChessPosition(position.getRow()+2, position.getColumn()+2)));
            }
        }
        if (position.getRow() > 0 && position.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+2)) == null ||
                    !board.getPiece(new ChessPosition(position.getRow(),
                    position.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn()+2)));
            }
        }
        if (position.getRow() < 7 && position.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn())) == null ||
                    !board.getPiece(new ChessPosition(position.getRow()+2,
                    position.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2, position.getColumn())));
            }
        }
        if (position.getRow() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+1)) == null ||
                    !board.getPiece(new ChessPosition(position.getRow(),
                    position.getColumn()+1)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn()+1)));
            }
        }
        if (position.getRow() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+1)) == null ||
                    !board.getPiece(new ChessPosition(position.getRow()+2,
                    position.getColumn()+1)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2,
                        position.getColumn()+1)));
            }
        }
        if (position.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow()+1, position.getColumn())) == null ||
                    !board.getPiece(new ChessPosition(position.getRow()+1,
                    position.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+1, position.getColumn())));
            }
        }
        if (position.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow()+1, position.getColumn()+2)) == null ||
                    !board.getPiece(new ChessPosition(position.getRow()+1,
                    position.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+1,
                        position.getColumn()+2)));
            }
        }
        return moves;
    }
}
