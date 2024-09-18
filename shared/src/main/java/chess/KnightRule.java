package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightRule extends PieceRule {

    private final ChessGame.TeamColor teamColor;

    public KnightRule(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (position.getRow() > 1 && position.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow()-1, position.getColumn())) == null || !board.getPiece(new ChessPosition(position.getRow()-1, position.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()-1, position.getColumn())));
            }
        }
        if (position.getRow() > 0 && position.getColumn() > 1) {
            if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()-1)) == null || !board.getPiece(new ChessPosition(position.getRow(), position.getColumn()-1)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn()-1)));
            }
        }
        if (position.getRow() > 1 && position.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow()-1, position.getColumn()+2)) == null || !board.getPiece(new ChessPosition(position.getRow()-1, position.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()-1, position.getColumn()+2)));
            }
        }
        if (position.getRow() > 0 && position.getColumn() < 6) {
            if (board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+3)) == null || !board.getPiece(new ChessPosition(position.getRow(), position.getColumn()+3)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow(), position.getColumn()+3)));
            }
        }
        if (position.getRow() < 6 && position.getColumn() > 0) {
            if (board.getPiece(new ChessPosition(position.getRow()+3, position.getColumn())) == null || !board.getPiece(new ChessPosition(position.getRow()+3, position.getColumn())).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+3, position.getColumn())));
            }
        }
        if (position.getRow() < 7 && position.getColumn() > 1) {
            if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()-1)) == null || !board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()-1)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2, position.getColumn()-1)));
            }
        }
        if (position.getRow() < 6 && position.getColumn() < 7) {
            if (board.getPiece(new ChessPosition(position.getRow()+3, position.getColumn()+2)) == null || !board.getPiece(new ChessPosition(position.getRow()+3, position.getColumn()+2)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+3, position.getColumn()+2)));
            }
        }
        if (position.getRow() < 7 && position.getColumn() < 6) {
            if (board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+3)) == null || !board.getPiece(new ChessPosition(position.getRow()+2, position.getColumn()+3)).getTeamColor().equals(teamColor)) {
                moves.add(new ChessMove(position, new ChessPosition(position.getRow()+2, position.getColumn()+3)));
            }
        }
        return moves;
    }
}
