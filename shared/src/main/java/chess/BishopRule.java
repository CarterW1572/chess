package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopRule extends PieceRule {

    private final ChessGame.TeamColor teamColor;

    public BishopRule(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position) {
        ChessPosition newPosition = new ChessPosition(position.getRow()+1, position.getColumn()+1);
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        while (newPosition.getRow() > 0 && newPosition.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(newPosition.getRow(), newPosition.getColumn())) == null) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow(), newPosition.getColumn())));
                newPosition = new ChessPosition(newPosition.getRow(), newPosition.getColumn());
            }
            else if (!board.getPiece(new ChessPosition(newPosition.getRow(), newPosition.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow(), newPosition.getColumn())));
                break;
            }
            else {
                break;
            }
        }
        newPosition = new ChessPosition(position.getRow()+1, position.getColumn()+1);
        while (newPosition.getRow() > 0 && newPosition.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(newPosition.getRow(), newPosition.getColumn()+2)) == null) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow(), newPosition.getColumn()+2)));
                newPosition = new ChessPosition(newPosition.getRow(), newPosition.getColumn()+2);
            }
            else if (!board.getPiece(new ChessPosition(newPosition.getRow(), newPosition.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow(), newPosition.getColumn()+2)));
                break;
            }
            else {
                break;
            }
        }
        newPosition = new ChessPosition(position.getRow()+1, position.getColumn()+1);
        while (newPosition.getRow() < 7 && newPosition.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(newPosition.getRow()+2, newPosition.getColumn())) == null) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow()+2, newPosition.getColumn())));
                newPosition = new ChessPosition(newPosition.getRow()+2, newPosition.getColumn());
            }
            else if (!board.getPiece(new ChessPosition(newPosition.getRow()+2, newPosition.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow()+2, newPosition.getColumn())));
                break;
            }
            else {
                break;
            }
        }
        newPosition = new ChessPosition(position.getRow()+1, position.getColumn()+1);
        while (newPosition.getRow() < 7 && newPosition.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(newPosition.getRow()+2, newPosition.getColumn()+2)) == null) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow()+2, newPosition.getColumn()+2)));
                newPosition = new ChessPosition(newPosition.getRow()+2, newPosition.getColumn()+2);
            }
            else if (!board.getPiece(new ChessPosition(newPosition.getRow()+2, newPosition.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(newPosition.getRow()+2, newPosition.getColumn()+2)));
                break;
            }
            else {
                break;
            }
        }
        return moves;
    }
}
