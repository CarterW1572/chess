package chess;

import java.util.Collection;

public abstract class PieceRule {

    public PieceRule() {}

    public abstract Collection<ChessMove> getPossibleMoves(ChessBoard board, ChessPosition position);
}
