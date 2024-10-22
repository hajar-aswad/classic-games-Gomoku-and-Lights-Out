package Gomoku;


import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Control {
    char computer = 'o';
    char human = 'x';
    Gomoku board = new Gomoku(5, 5, 3);

    public void play() {
        System.out.println(board);
        while (true) {
            humanPlay();
            System.out.println(board);

            if (board.isWin(human)) {
                System.out.println("Human wins");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }
            computerPlay();
            System.out.println("_____Computer Turn______");
            System.out.println(board);
            if (board.isWin(computer)) {
                System.out.println("Computer wins!");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }
        }
    }

    //         ************** YOUR CODE HERE ************            \\
    private void computerPlay() {
        // call maxMove
   //     board = (Gomoku) maxMove(board, Integer.MIN_VALUE, Integer.MAX_VALUE).get(1); // alpha beta
//        maxMove(board).get(0) ; ///// this . board
// this.board= (Gomoku)maxMove(board).get(0);


        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        board = (Gomoku) maxMove(board, alpha, beta, 10).get(1);
    }


    /**
     * Human plays
     *
     * @return the column the human played in
     */
    private void humanPlay() {
        Scanner s = new Scanner(System.in);
        int col;
        while (true) {
            System.out.print("Enter column: ");
            col = s.nextInt();
            System.out.println();
            if ((col > 0) && (col - 1 < board.getWidth())) {
                if (board.play(human, col - 1)) {
                    return;
                }
                System.out.println("Invalid Column: Column " + col + " is full!, try agine");
            }
            System.out.println("Invalid Column: out of range " + board.getWidth() + ", try agine");
        }
    }

    // ************** YOUR CODE HERE ************            \\
    //function MaxMove list of  objects
    List<Object>maxMove(Gomoku b) {
        List<Object> bestcild=null;
         int value= Integer.MIN_VALUE;
        if(b.isFinished()){
            bestcild=new LinkedList<>();
            bestcild.add(b);
            bestcild.add(b.evaluate(computer));
            return bestcild;
        }
        List<Gomoku> children =  b.GenerateMoves(computer);//////// MIN MOVE  GENERATE L USER PLAY
        for (Gomoku child:children) {
            int eval =  ((Gomoku)minMove(child).get(1)).evaluate(computer);
            if (eval > value) {
                bestcild.set(0,child);
                value = eval;
                bestcild.set(1,value);
            }

        }

        return bestcild;
    }
    List<Object>minMove(Gomoku b) {
        List<Object> bestcild=null;
        int value= Integer.MAX_VALUE;
        if(b.isFinished()){
            bestcild=new LinkedList<>();
            bestcild.add(b);
            bestcild.add(b.evaluate(computer));
            return bestcild;
        }
        List<Gomoku> children =  b.GenerateMoves(human);
        for (Gomoku child:children) {
            int eval =  ((Gomoku)maxMove(child).get(1)).evaluate(computer);
            if (eval < value) {
                bestcild.set(0,child);
                value = eval;
                bestcild.set(1,value);
            }

        }

        return bestcild;
    }






    private List<Object> maxMove(Gomoku b, int alpha, int beta) {
        List<Object> bestchild = new LinkedList<>();
        List<Gomoku> nextStates = b.GenerateMoves(computer);

        if (b.isFinished()) {
            bestchild.add(b.evaluate(computer));
            bestchild.add(b);
            return bestchild;
        }

        int max = Integer.MIN_VALUE;
        int index = 0;

        for (int i = 0; i < nextStates.size(); i++) {
            if ((int) minMove(nextStates.get(i), alpha, beta).get(0) > max) {
                max = (int) minMove(nextStates.get(i), alpha, beta).get(0);
                index = i;
            }
            alpha = Math.max(max, alpha);
            if (beta <= alpha)
                break;
        }

        bestchild.add(max);
        bestchild.add(nextStates.get(index));
        return bestchild;
    }

    private List<Object> minMove(Gomoku b, int alpha, int beta) {
        List<Object> bestchild = new LinkedList<>();
        List<Gomoku> nextStates = b.GenerateMoves(human);

        if (b.isFinished()) {
            bestchild.add(b.evaluate(computer));
            bestchild.add(b);
            return bestchild;
        }

        int min = Integer.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < nextStates.size(); i++) {
            if ((int) maxMove(nextStates.get(i), alpha, beta).get(0) < min) {
                min = (int) maxMove(nextStates.get(i), alpha, beta).get(0);
                index = i;
            }
            beta = Math.min(min, beta);
            if (beta <= alpha)
                break;
        }

        bestchild.add(min);
        bestchild.add(nextStates.get(index));
        return bestchild;
    }

    private List<Object> maxMove(Gomoku b, int alpha, int beta, int depth) {
        List<Object> list = new LinkedList<>();
        List<Gomoku> nextStates = b.GenerateMoves(computer);
        if (b.isFinished() || depth <= 0) {
            list.add(b.evaluate(computer));
            list.add(b);
            return list;
        }

        int max = Integer.MIN_VALUE;
        int count = 0;

        for (int i = 0; i < nextStates.size(); i++) {
            var eval = minMove(nextStates.get(i), alpha, beta, depth - 1);

            if ((int) eval.get(0) > max) {
                max = (int) eval.get(0);
                count = i;
            }
            alpha = Math.max((int) eval.get(0), alpha);
            if (beta <= alpha)
                break;
        }

        list.add(max);
        list.add(nextStates.get(count));
        return list;
    }

    private List<Object> minMove(Gomoku b, int alpha, int beta, int depth) {

        List<Object> list = new LinkedList<>();
        List<Gomoku> nextStates = b.GenerateMoves(human);

        if (b.isFinished() || depth <= 0) {
            list.add(b.evaluate(computer));
            list.add(b);
            return list;
        }

        int min = Integer.MAX_VALUE;
        int count = 0;

        for (int i = 0; i < nextStates.size(); i++) {
            var eval = maxMove(nextStates.get(i), alpha, beta, depth - 1);

            if ((int) eval.get(0) < min) {
                min = (int) eval.get(0);
                count = i;
            }
            beta = Math.min((int) eval.get(0), beta);
            if (beta <= alpha)
                break;
        }

        list.add(min);
        list.add(nextStates.get(count));
        return list;
    }

    public static void main(String[] args) {
        Control g = new Control();
        g.play();
    }
}
