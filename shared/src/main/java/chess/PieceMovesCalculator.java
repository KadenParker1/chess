package chess;

import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator{
    private final ChessBoard board;
    private final ChessPosition myPosition;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;

    }

    public Collection<ChessMove> calculateBishopMoves(ChessBoard board, ChessPosition myPosition) {

    }

    public Collection<ChessMove> calculatePawnMoves(ChessBoard board, ChessPosition myPosition) {

    }
    public Collection<ChessMove> calculateRookMoves(ChessBoard board, ChessPosition myPosition) {

    }
    public Collection<ChessMove> calculateKingMoves(ChessBoard board, ChessPosition myPosition) {

    }
    public Collection<ChessMove> calculateQueenMoves(ChessBoard board, ChessPosition myPosition) {

    }
    public Collection<ChessMove> calculateKnightMoves(ChessBoard board, ChessPosition myPosition) {

    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return calculateRookMoves(ChessBoard board, ChessPosition myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            return calculatePawnMoves(ChessBoard board, ChessPosition myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return calculateKnightMoves(ChessBoard board, ChessPosition myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            return calculateKingMoves(ChessBoard board, ChessPosition myPosition);

        }

        else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return calculateQueenMoves(ChessBoard board, ChessPosition myPosition);
        }

        else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            return calculateBishopMoves(ChessBoard board, ChessPosition myPosition);
        }


    }


}