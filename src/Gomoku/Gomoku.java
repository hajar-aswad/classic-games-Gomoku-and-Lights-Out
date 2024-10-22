package Gomoku;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Gomoku {
    private char[][] grid;
    private int[] topPieceIndex;
    private int width;
    private int height;
    private int numOfPiecesToWin;
    private int fills;
    private int lastColumnPlayed = -1;

    public Gomoku(int width, int height, int numOfPiecesToWin) {
        fills = 0;
        this.width = width;
        this.height = height;
        this.numOfPiecesToWin = numOfPiecesToWin;
        grid = new char[height][width];
        topPieceIndex = new int[width];
        for (int i = 0; i < topPieceIndex.length; i++) {
            topPieceIndex[i] = height;
        }
        for (char[] grid1 : grid) {
            for (int j = 0; j < grid1.length; j++) {
                grid1[j] = ' ';
            }
        }
    }

    public Gomoku(Gomoku board) {
        grid = new char[board.height][board.width];
        topPieceIndex = new int[board.width];
        System.arraycopy(board.topPieceIndex, 0, topPieceIndex, 0, this.topPieceIndex.length);
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(board.grid[i], 0, this.grid[i], 0, board.width);
        }
        this.fills = board.fills;
        this.height = board.height;
        this.width = board.width;
        this.numOfPiecesToWin = board.numOfPiecesToWin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Gomoku> GenerateMoves(char nextPlayer) {
        List<Gomoku> nextBoards = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            if (topPieceIndex[i] != 0) {
                Gomoku nextBoard = new Gomoku(this);
                nextBoard.play(nextPlayer, i);
                nextBoards.add(nextBoard);
            }
        }
        return nextBoards;
    }

    public boolean play(char player, int col) {
        if (topPieceIndex[col] != 0) {
            topPieceIndex[col] -= 1;
            grid[topPieceIndex[col]][col] = player;
            fills++;
            lastColumnPlayed = col;
            return true;
        }
        return false;
    }

    /**
     * how good is the board for this player?
     *
     * @param player
     * @return
     *
     *
     */

    public int evaluate(char player) {
//        if(isWin('x'))return Integer.MIN_VALUE;
//        else if(isWin('o'))return Integer.MAX_VALUE;
//        else return 0;

        int counto = count('o');

        int countx = count('x');

        if (isWin('o'))
            return Integer.MAX_VALUE;

        else if (isWin('x'))
            return Integer.MIN_VALUE;

        else if (isWithdraw() || counto == countx)
            return 0;

        else if (counto > countx)
            return counto;

        else if (counto < countx)
            return -1 * countx;

        return 0;

    }


    public int count(char player) {
        int maxAll = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] != player)
                    continue;

                int countUpDown = Sequence(player, i, j, +1, 0, 0)
                        + Sequence(player, i, j, -1, 0, 0) - 1;

                int countRightLeft = Sequence(player, i, j, 0, +1, 0)
                        + Sequence(player, i, j, 0, -1, 0) - 1;

                int maxUpDownRightLeft = Math.max(countUpDown, countRightLeft);

                int countDiagonalRight = Sequence(player, i, j, -1, +1, 0)
                        + Sequence(player, i, j, +1, -1, 0) - 1;

                int countDiagonalLeft = Sequence(player, i, j, -1, -1, 0)
                        + Sequence(player, i, j, +1, +1, 0) - 1;

                int maxDiagonalRightLeft = Math.max(countDiagonalRight, countDiagonalLeft);

                int maxSequence = Math.max(maxUpDownRightLeft, maxDiagonalRightLeft);

                maxAll = Math.max(maxAll, maxSequence);
            }
        }

        // System.out.println(maxAll);
        return maxAll;
    }

    public int Sequence(char player, int currentRow, int currentColumn, int moveI, int moveJ, int counter) {
        if (currentRow < 0 || currentColumn < 0
                || currentRow >= width || currentColumn >= height
                || grid[currentRow][currentColumn] != player)
            return counter;

        counter = 1 + Sequence(player, currentRow + moveI, currentColumn + moveJ, moveI, moveJ, 0);
        return counter;
    }

    /**
     * checks if the game is withdraw
     *
     * @return
     */
    public boolean isWithdraw() {
        return (fills == width * height);
    }

    /**
     * checks if player putting last piece makes him win (connect four pieces)
     *
     * @param player
     * @return true if win
     */
    public boolean isWinWithLastPiece(char player) {
        int col = this.lastColumnPlayed;
        return (isWinInColumn(player, col) || isWinInRow(player, col)
                || isWinInDiagonal_1(player, col) || isWinInDiagonal_2(player, col));
    }


    public boolean isWin(char player) {
        for (int col = 0; col < width; col++) {
            if (isWinInColumn(player, col)) {
                return true;
            } else if (isWinInRow(player, col)) {
                return true;
            } else if (isWinInDiagonal_1(player, col)) {
                return true;
            } else if (isWinInDiagonal_2(player, col)) {
                return true;
            }
        }
        return false;
    }


    public boolean isFinished() {
        return (isWin('x') || isWin('o') || isWithdraw());
    }



    //row is empty
    private boolean isWinInColumn(char player, int col) {
        int row = topPieceIndex[col];
        if (row == height) {
            return false;
        }
        // the cell itself
        if (grid[row][col] != player) {
            return false;
        }
        int count = 1;
        // check cells below
        try {
            for (int i = row + 1; i < height; i++) {
                if (grid[i][col] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return (count >= numOfPiecesToWin);
    }

    private boolean isWinInRow(char player, int col) {
        //collect row
        int row = topPieceIndex[col];
        //row is empty
        if (row == height) {
            return false;
        }
        // the cell itself
        if (grid[row][col] != player) {
            return false;
        }
        int count = 1;
        // cells befor
        try {
            for (int i = col - 1; i >= 0; i--) {
                if (grid[row][i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        // cells after
        try {
            for (int i = col + 1; i < width; i++) {
                if (grid[row][i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return (count >= numOfPiecesToWin);

    }

    private boolean isWinInDiagonal_1(char player, int col) {
        //collect diagonal
        int row = topPieceIndex[col];
        //row is empty
        if (row == height) {
            return false;
        }
        // the cell itself
        if (grid[row][col] != player) {
            return false;
        }
        int count = 1;
        // cells befor
        try {
            for (int i = 1;; i++) {
                if (grid[row - i][col - i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        // cells after
        try {
            for (int i = 1;; i++) {
                if (grid[row + i][col + i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return (count >= numOfPiecesToWin);

    }

    private boolean isWinInDiagonal_2(char player, int col) {
        //collect diagonal
        int row = topPieceIndex[col];
        //row is empty
        if (row == height) {
            return false;
        }
        // the cell itself
        if (grid[row][col] != player) {
            return false;
        }
        int count = 1;
        // cells befor
        try {
            for (int i = 1;; i++) {
                if (grid[row - i][col + i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        // cells after
        try {
            for (int i = 1;; i++) {
                if (grid[row + i][col - i] == player) {
                    count++;
                } else {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return (count >= numOfPiecesToWin);

    }

    private char otherPlayer(char player) {
        if (player == 'x') {
            return 'o';
        }
        return 'x';
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sb.append(" | ");
            for (int j = 0; j < width; j++) {
                sb.append(grid[i][j]);
                sb.append(" | ");
            }
            sb.append('\n');
        }
        sb.append(" ");
        for (int i = 1; i < height; i++) {
            sb.append("-----");
        }
        sb.append("\n | ");
        for (int i = 1; i <= height; i++) {
            sb.append(i);
            sb.append(" | ");
        }

        return sb.toString();
    }
}
