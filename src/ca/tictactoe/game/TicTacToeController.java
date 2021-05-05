package ca.tictactoe.game;

import ca.tictactoe.ui.TicTacToe;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * The controller class for Tic Tac Toe game.
 * When events occur in our game, this class is responsible for updating the game state and checking for end game conditions.
 */
public class TicTacToeController {

    private TicTacToe gameUI;
    private int[][] gameBoard = new int[4][4];
    private int numMovesMade = 0;
    private boolean gameOver = false;
    private final String xPlayerName = "Sonic"; // x player corresponds to an id 1
    private final String oPlayerName = "Knuckles"; // o player corresponds to an id 2
    private String currentPlayerTurn = xPlayerName;

    public void clickGrid(GridPane gridPane, MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridPane) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse clicked col: " + colIndex + " And row: " + rowIndex);
            updateGameBoard(rowIndex, colIndex);
        }
    }

    private void updateGameBoard(Integer rowIndex, Integer colIndex) {
        if (gameBoard[rowIndex][colIndex] != 0 || gameOver) {
            return;
        }
        int result;
        numMovesMade++;
        if (currentPlayerTurn.equals(xPlayerName)) {
            gameBoard[rowIndex][colIndex] = 1;
            gameUI.changeGridImage(xPlayerName, rowIndex, colIndex);
            result = checkGameOver(1, rowIndex, colIndex);
        } else {
            gameBoard[rowIndex][colIndex] = 2;
            gameUI.changeGridImage(oPlayerName, rowIndex, colIndex);
            result = checkGameOver(2, rowIndex, colIndex);
        }
        if (result != 2) {
            displayGameOver(result, currentPlayerTurn);
            return;
        }
        if (currentPlayerTurn.equals(xPlayerName)) {
            currentPlayerTurn = oPlayerName;
        } else {
            currentPlayerTurn = xPlayerName;
        }
        gameUI.changeLabelText(String.format("It is %s's turn!", currentPlayerTurn));
        displayBoard();
    }

    private void displayGameOver(int result, String currentPlayerTurn) {
        gameOver = true;
        if (result == 0) {
            gameUI.changeLabelText("This game is a tie.");
        } else if (result == 1) {
            gameUI.changeLabelText("The winner is " + currentPlayerTurn);
        }
    }

    /**
     * returns an integer representing how the game state is currently
     *  0 = game is a tie since board is filled
     *  1 = game is over and the player has just won
     *  2 = game is not over, and player has not made a winning move
     */
    public Integer checkGameOver(Integer player, Integer row, Integer col) {
        int n = gameBoard.length;
        if (numMovesMade == n*n) {
            return 0;
        }

        for (int i = 0; i < n; i++) {
            if (gameBoard[i][col] != player)
                break; // if this row is occupied by opponent or still empty - not a winning move on this row
            if (i == n - 1) {
                return 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if (gameBoard[row][i] != player)
                break; // if this col is occupied by opponent or still empty - not a winning move on this column
            if (i == n - 1) {
                return 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if (gameBoard[i][i] != player)
                break; // this diagonal '\' is occupied by opponent or still empty - not a winning move on this diagonal
            if (i == n - 1) {
                return 1;
            }
        }

        for (int i = 0; i < n; i++) {
            if (gameBoard[i][n-1-i] != player) {
                break; // this diagonal '/' is occupied by opponent or still empty - not a winning move on this diagonal
            }
            if (i == n - 1) {
                return 1;
            }
        }

        return 2;
    }

    private void displayBoard() {
        for (int[] x : gameBoard) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public void setGameUI(TicTacToe gameUI) {
        this.gameUI = gameUI;
    }

    public void resetGame() {
        gameBoard = new int[4][4];
        numMovesMade = 0;
        currentPlayerTurn = xPlayerName;
        gameOver = false;
        gameUI.resetPictures();
        gameUI.changeLabelText(String.format("It is %s's turn!", currentPlayerTurn));
    }

    public String getCurrentPlayer() {
        return currentPlayerTurn;
    }

    public String getxPlayerName() {
        return xPlayerName;
    }

    public String getoPlayerName() {
        return oPlayerName;
    }
}
