package client;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;
    private static final String EMPTY = "  ";
    private ChessBoard board;
    private ChessGame.TeamColor color;

    public ChessBoardUI(ChessBoard board, ChessGame.TeamColor color){
        this.board = board;
        this.color = color;
    }

    public static void main(String[] args){
        drawBoard(new ChessGame(this.board, this.color);
    }

    public static void drawBoard(ChessBoard board, ChessGame.TeamColor color){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }

}
