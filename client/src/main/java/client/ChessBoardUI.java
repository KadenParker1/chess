package client;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final String EMPTY = "   ";

    public ChessBoardUI(ChessBoard board, ChessGame.TeamColor color){

    }

    public static void main(String[] args){
        ChessGame game = new ChessGame();
        drawBoard(game.getBoard(), ChessGame.TeamColor.WHITE);
    }

    public static void drawBoard(ChessBoard board, ChessGame.TeamColor color){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeader(out, color);
        if (color == ChessGame.TeamColor.WHITE){
            for (int row = 8; row >= 1; row--){
                drawRow(out, board, row, color);
            }
        }
        else {
            for (int row = 1; row <= 8; row++){
                drawRow(out, board, row, color);
            }

        }
        drawHeader(out, color);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

    }
    public static void drawHeader(PrintStream out, ChessGame.TeamColor color){
        setBlack(out);
        out.print("   ");
        String [] headers = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        if (color == ChessGame.TeamColor.WHITE){
            for (int i = 0; i < 8; i++) {
                out.print(headers[i]);
            }
        }
        else {
            for (int i = 7; i >= 0; i--) {
                out.print(headers[i]);
            }
        }
        out.println(SET_BG_COLOR_BLACK);
    }
    public static void drawRow(PrintStream out, ChessBoard board, int row, ChessGame.TeamColor color){
        setBlack(out);
        out.print(" " + row + " ");
        if (color == ChessGame.TeamColor.BLACK) {
            for (int col = 8; col >= 1; col--){
                drawSquare(out, board, row, col);
            }
        } else{
            for (int col = 1; col <= 8; col++){
                drawSquare(out, board, row, col);
            }
        }
        out.print(" " + row + " ");
        out.println(SET_BG_COLOR_BLACK);
    }
    public static void drawSquare(PrintStream out, ChessBoard board, int row, int col){
        if ((row + col) % 2 == 0) {
            out.print(SET_BG_COLOR_BLACK);
        } else {
            out.print(SET_BG_COLOR_LIGHT_GREY);
        }
        ChessPiece piece = board.getPiece(new ChessPosition(row, col));
        if (piece == null){
            out.print(EMPTY);
        } else{
            printPiece(out, piece);
        }
    }
    public static void printPiece(PrintStream out, ChessPiece piece){
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            out.print(SET_TEXT_COLOR_WHITE);
        }
        else{
            out.print(SET_TEXT_COLOR_MAGENTA);
        }
        String symbol = switch (piece.getPieceType()) {
            case KING -> " K ";
            case PAWN -> " P ";
            case ROOK -> " R ";
            case KNIGHT -> " N ";
            case QUEEN -> " Q ";
            case BISHOP -> " B ";
        };
        out.print(symbol);
    }

    private static void setBlack(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
}
