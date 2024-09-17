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

    private ChessGame.TeamColor pieceColor;
    private PieceType type;
    private boolean rightEdge;
    private boolean leftEdge;
    private boolean topEdge;
    private boolean bottomEdge;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        rightEdge = false;
        leftEdge = false;
        topEdge = false;
        bottomEdge = false;
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
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        /*if (myPosition.getRow() >= 7) {
            topEdge = true;
        }
        if (myPosition.getRow() <= 0) {
            bottomEdge = true;
        }
        if (myPosition.getColumn() >= 7) {
            rightEdge = true;
        }
        if (myPosition.getColumn() <= 0) {
            leftEdge = true;
        }*/
        switch (getPieceType()) {
            case KING:
                /*if (!topEdge) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())));
                    if (!rightEdge) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1)));
                    }
                    if (!leftEdge) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1)));
                    }
                }
                if (!bottomEdge) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())));
                    if (!rightEdge) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1)));
                    }
                    if (!leftEdge) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1)));
                    }
                }
                if (!rightEdge) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1)));
                }
                if (!leftEdge) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1)));
                }*/
            case QUEEN:

            case BISHOP:
                BishopRule bishop = new BishopRule(getTeamColor());
                moves = (ArrayList<ChessMove>) bishop.getPossibleMoves(board, myPosition);
            case KNIGHT:

            case ROOK:

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
