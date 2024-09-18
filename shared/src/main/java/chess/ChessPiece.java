package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        switch (this.getPieceType()) {
            case KING:
                KingRule king = new KingRule(getTeamColor());
                moves.addAll(king.getPossibleMoves(board, myPosition));
                break;
            case QUEEN:
                QueenRule queen = new QueenRule(getTeamColor());
                moves.addAll(queen.getPossibleMoves(board, myPosition));
                break;
            case BISHOP:
                BishopRule bishop = new BishopRule(getTeamColor());
                moves.addAll(bishop.getPossibleMoves(board, myPosition));
                break;
            case KNIGHT:

            case ROOK:
                RookRule rook = new RookRule(getTeamColor());
                moves.addAll(rook.getPossibleMoves(board, myPosition));
                break;
            case PAWN:

        }
        return moves;
    }

    @Override
    public String toString() {
        return "(" + pieceColor + "," + type + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChessPiece that = (ChessPiece) obj;
        return pieceColor.equals(that.pieceColor) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return (71 * pieceColor.hashCode()) + type.hashCode();
    }
}
