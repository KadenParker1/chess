package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor teamTurn;
    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return Collections.emptyList();
        }
        TeamColor color = piece.getTeamColor();
        Collection<ChessMove> possible_moves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> legal_moves = new ArrayList<>();

        for (ChessMove individual_move : possible_moves) {
            ChessGame fakeGame = new ChessGame();
            ChessBoard copy = copyBoard(board);
            fakeGame.setBoard(copy);
            fakeGame.applyMoveHelperFunction(copy, individual_move);
            if (!fakeGame.isInCheck(color)){
                legal_moves.add(individual_move);
            }
        }

        return legal_moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece piece = board.getPiece(start);
        Collection<ChessMove> legalMoves = validMoves(start);
        if (legalMoves == null | !legalMoves.contains(move)){
            throw new InvalidMoveException("Move is Not Valid");
        }
        board.addPiece(end, piece);
        board.addPiece(start, null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king_position = null;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j<=8; j++) {
                chess.ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor()==teamColor){
                    king_position = new ChessPosition(i, j);
                }
            }
            }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j<=8; j++) {
                ChessPosition checkPosition = new ChessPosition(i, j);
                chess.ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(board, checkPosition);
                    Set<ChessPosition> endPositionSet = new HashSet<>();
                    for (ChessMove move : moves) {
                        endPositionSet.add(move.getEndPosition());
                    }
                    if (endPositionSet.contains(king_position)){
                        return true;
                    }

                }
            }
        }



        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition king_position = null;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j<=8; j++) {
                chess.ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor()==teamColor){
                    king_position = new ChessPosition(i, j);
                }
            }
        }
        // need to add way to escape checkmate using other pieces
        if (isInCheck(teamColor) && validMoves(king_position) == null) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        if (isInCheck(teamColor)) {
            return false;
        }
        else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j<=8; j++) {
                    if (validMoves(new ChessPosition(i, j)) != null) {
                        return false;

                    }
                }
            }
            return true;
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
    return board;
    }

    private ChessBoard copyBoard(ChessBoard original){
        ChessBoard copy = new ChessBoard();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j<=8; j++) {
                chess.ChessPosition attemped_pos = new ChessPosition(i, j);
                if (original.getPiece(attemped_pos) != null) {
                    ChessPiece piece = original.getPiece(attemped_pos);
                    copy.addPiece(attemped_pos, piece);
                }
            }
        }
        return copy;
    }

    private void applyMoveHelperFunction(ChessBoard board, ChessMove move){
        ChessPiece piece = board.getPiece(move.getStartPosition());
        board.addPiece(move.getEndPosition(), piece);
        board.addPiece(move.getStartPosition(), null);
    }
}
