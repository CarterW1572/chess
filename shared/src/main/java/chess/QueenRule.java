package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenRule extends PieceRule {

    private final ChessGame.TeamColor teamColor;

    QueenRule(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        BishopRule diagonalMoves = new BishopRule(teamColor);
        moves.addAll(diagonalMoves.getPossibleMoves(board, position));
        RookRule rookMoves = new RookRule(teamColor);
        moves.addAll(rookMoves.getPossibleMoves(board, position));
        return moves;
    }
}
