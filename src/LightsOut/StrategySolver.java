package LightsOut;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;
public class StrategySolver {
    private State initialstate;
public void v (){
    hillClimbing(null);
}

    public static void UserPlay(Scanner input) {
        System.out.println(" Enter number of columns");
        int n = input.nextInt();
        System.out.println(" Enter number of rows ");
        int m = input.nextInt();
        State game = new State(n, m);
        System.out.println(game);

        while (!game.solved()) {
            try {
                System.out.println(" Enter your X's Index ");
                int x = input.nextInt();
                System.out.println(" Enter your Y's Index ");
                int y = input.nextInt();
                try {
                    if (!game.touchable(x, y))
                        System.out.println(" error message: the index you entered is out of the boundaries ");
                    else
                        game.turnoff(x, y);
                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println(" error message: the index you entered is out of the boundaries ");
                }
            } catch (InputMismatchException exception) {
                System.out.println(" error message: the index you entered is not an integer ");
                input.next(); // to clear the buffer
            }
            System.out.println(game);
        }
        System.out.println("congrats you won!!!!!!!!!!!!!");
    }
    public static int hillClim(Node initialNode) {
        PriorityQueue<Node> Queue = new
                PriorityQueue<>(Comparator.comparingInt(Node::heuristic));
        HashSet<State> visited = new HashSet<>();
        visited.add(initialNode.getState());
        Queue.add(initialNode);
        while (!Queue.isEmpty()) {
            Node currentState = Queue.poll();
            if (currentState.getState().solved()) {
                System.out.println("Solution found:");
                System.out.println(currentState);
                currentState.printPath1();
                System.out.println("VISITED IS 2183");

                return currentState.cost;
            }
            for (Node child :currentState.AllChildren()) {

                if (!visited.contains(child.getState())) {

                    visited.add(child.getState());
                    Queue.add(child);
                } else {
                    for (Node node : Queue)
                    {
                        if ( node.equals(child) && child.heuristic < node.heuristic )
                        {
                            Queue.remove(node);
                            Queue.add(child);
                        }
                    }
                }
            }
        }
        return 0;
    }
    public static void UserPlay(Scanner input, State manuallGrid) {

        State game = new State(manuallGrid);

        System.out.println(manuallGrid);

        while (!game.solved()) {
            try {
                System.out.println(" Enter your X's Index ");
                int x = input.nextInt();
                System.out.println(" Enter your Y's Index ");
                int y = input.nextInt();
                try {
                    if (!game.touchable(x, y))
                        System.out.println(" error message: the index you entered is out of the boundaries ");
                    else
                        game.turnoff(x, y);
                } catch (ArrayIndexOutOfBoundsException exception) {
                    System.out.println(" error message: the index you entered is out of the boundaries ");
                }
            } catch (InputMismatchException exception) {
                System.out.println(" error message: the index you entered is not an integer ");
                input.next(); // to clear the buffer
            }
            System.out.println(game);
        }
        System.out.println("congrats you won!!!!!!!!!!!!!");
    }

    public static State enterGridManually(Scanner input) {
        System.out.println("Enter the number of rows:");
        int n = input.nextInt();
        System.out.println("Enter the number of columns:");
        int m = input.nextInt();

        System.out.println("Enter the grid row by row using '+' for 'on' and '-' for 'off':");

        String[] rows = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            rows[i] = input.next();
        }

        return new State(n, m, rows);
    }

    public static Node dfsNode(Node v) {
        System.out.println("Entering dfs method");
        Node node;
        int maxStackSize = 0;
        long startTime = System.currentTimeMillis();

        ArrayDeque<Node> s = new ArrayDeque<>();
        HashSet<State> visit = new HashSet<>();
        s.push(v);
        visit.add(v.getState());
        while (!s.isEmpty()) {
            //System.out.println("stack is not empty");
            node = s.pop();
            maxStackSize = Math.max(maxStackSize, s.size());
            if (node.getState().solved()) {
                long endTime = System.currentTimeMillis();
                System.out.println("length of visit(time complexity) is: " + visit.size());
                System.out.println(" number of nodes at the  solution level: " + s.size());
                System.out.println("maximum length of stack (Ram complexity) is: " + maxStackSize);
                System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
                System.out.println("length of solution path " + visit.size());
                System.out.println(node);
                node.printPath();

                return node;
            }
            ArrayList<Node> children = node.AllChildren();
            for (Node childstate : children) {
                if (!visit.contains(childstate.getState())) {
                    s.push(childstate);
                    visit.add(childstate.getState());

                }
            }
        }
        //   System.out.println(" exitinng  dfs method");

        System.out.println(v);
        return v;
    }


    public static Node bfsNode(Node initialState) {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        HashSet<State> visited = new HashSet<>();
        int maxQueueSize = 0;
        long startTime = System.currentTimeMillis();

        queue.add(initialState);
        visited.add(initialState.getState());
        while (!queue.isEmpty()) {
            Node currentState = queue.poll();
            maxQueueSize = Math.max(maxQueueSize, queue.size());

            if (currentState.solved()) {
                long endTime = System.currentTimeMillis();
                System.out.println("length of visit (time complexity)is: " + visited.size());
                System.out.println("length of queue(Ram complexity) is: " + queue.size());
                System.out.println("maximum length of queue (Ram complexity) is: " + maxQueueSize);
                System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
                currentState.printPath();
                return currentState; // Solution
            }

            List<Node> children = currentState.AllChildren();

            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    queue.add(child);
                    visited.add(child.getState());
                }
            }
        }
        return null; //
    }

    public static int UCS(Node initialGrid) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        HashSet<State> visited = new HashSet<>();
        visited.add(initialGrid.getState());
        priorityQueue.add(initialGrid);
        while (!priorityQueue.isEmpty()) {
            Node currentState = priorityQueue.poll();
            if (currentState.getState().solved()) {
                System.out.println("length of visit (time complexity)is: " + visited.size());
                System.out.println(currentState);
                currentState.printPath();
                System.out.println("found sloution ");
                return currentState.cost;
            }
            List<Node> children = currentState.AllChildren();
//            System.out.println( "CHILDREN SIZE "+children.size());
//            System.out.println(priorityQueue.size());
            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    priorityQueue.add(child);
                    visited.add(child.getState());
                } else {
                    for (Node ch : priorityQueue) {
                        if (child.getState().equals(ch.getState()) && child.getCost() < ch.getCost()) {// object from  pq
                            priorityQueue.remove(ch);
                            priorityQueue.add(child);
                            break;
                        }
                    }
                }
            }
        }
        return 0 ;
    }


    public static int hillClimbing(Node initialNode) {
        ArrayDeque<Node> Queue = new ArrayDeque<>();
        HashSet<State> visited = new HashSet<>();
        visited.add(initialNode.getState());
        Queue.add(initialNode);
        while (!Queue.isEmpty()) {
            Node currentState = Queue.poll();
            if (currentState.getState().solved()) {
                System.out.println("Solution found:");
                System.out.println(currentState);
                currentState.printPath();
                System.out.println("VISITED IS "+visited.size());


                return currentState.cost;
            }
            Node BestChild=null;
            int BestHeruistic=99999;
            List<Node> children = currentState.AllChildren();
            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    if(child.getHeuristic()<BestHeruistic)
                        BestHeruistic=child.getHeuristic();
                    BestChild=child;
                } }

            if(BestChild!=null) {
                Queue.remove(currentState);
                Queue.add(BestChild);
                visited.add(BestChild.getState());
            }


        }
        return 0;
    }
    public static int A_Star(Node initialNode) {
        PriorityQueue<Node> priorityQueue = new
                PriorityQueue<>(Comparator.comparingInt(Node::G));
        HashSet<State> visited = new HashSet<>();
        visited.add(initialNode.getState());
        priorityQueue.add(initialNode);
        while (!priorityQueue.isEmpty()) {
            Node currentState = priorityQueue.poll();
            if (currentState.getState().solved()) {
                System.out.println("Solution found:");
                System.out.println(currentState);
                currentState.printPath();
                return currentState.cost;
            }
            List<Node> children = currentState.AllChildren();
            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    priorityQueue.add(child);
                    visited.add(child.getState());
                } else {
                    for (Node ch : priorityQueue) {
                        if (child.getState().equals(ch.getState()) && child.getCost() < ch.getCost()) {// object from  pq
                            priorityQueue.remove(ch);
                            priorityQueue.add(child);
                            break;
                        }
                    }
                }
            }
        }
        return 0;
    }



        public static int miniMax(Node initialNode, int depth, boolean maximizing) {
            if (initialNode.solved()) {
                initialNode.getDepth();
                System.out.println(" DEPTH IS "+initialNode.getDepth() );
                return initialNode.getDepth() ;
            }

            if (maximizing) {
                int maxEval = Integer.MIN_VALUE;

                List<Node> moves = initialNode.AllChildren();
                for(Node move : moves) {
                    int eval = StrategySolver.miniMax(move, depth + 1, false);
                    maxEval = Math.max(maxEval, eval);
                }

                return maxEval;
            } else {
                int minEval = 99999;

                List<Node> moves = initialNode.AllChildren();
                for(Node move : moves) {
                    int eval = StrategySolver.miniMax(move, depth + 1, true);
                    minEval = Math.min(minEval, eval);
                }

                return minEval;
            }
        }

//        public static int alphaBeta(Node s, int depth,int alpha, int beta, boolean maximizing) {
//            if (s.solved()) {
//                return s.getDepth();
//            }
//
//            if (maximizing) {
//                int maxEval = Integer.MIN_VALUE;
//
//                List<Node> moves = s.AllChildren();
//                for(Node move : moves) {
//                    int eval = StrategySolver.alphaBeta(move, depth + 1, alpha, beta, false);
//                    maxEval = Math.max(maxEval, eval);
//
//                    alpha = Math.max(alpha, eval);
//                    if (beta <= alpha) {
//                        break;
//                    }
//                }
//
//                return maxEval;
//            } else {
//                int minEval = Integer.MAX_VALUE;
//
//                List<Node> moves = s.AllChildren();
//                for(Node move : moves) {
//                    int eval = StrategySolver.alphaBeta(move, depth + 1, alpha, beta, true);
//                    minEval = Math.min(minEval, eval);
//
//                    beta = Math.min(beta, eval);
//                    if (beta <= alpha) {
//                        break;
//                    }
//                }
//
//                return minEval;
//            }
//        }



//
//
//
//    public static void playWithAIMiniMax(Game state) {
//        Game current = state;
//
//        while (!current.isFinalState()) {
//            current.print();
//            System.out.println(current.currentPlayer + "'s turn.");
//
//            if (current.currentPlayer == Game.PLAYER_O) {
//                System.out.println("Enter x: ");
//                int x = scanner.nextInt();
//                System.out.println("Enter y: ");
//                int y = scanner.nextInt();
//
//                if (current.canSetSquare(x, y)) {
//                    current = current.setSquare(x, y);
//                } else {
//                    System.out.println("Not valid move.");
//                }
//            } else {
//                current = current.getBestMoveForCurrentPlayerMiniMax();
//            }
//        }
//
//        current.print();
//        System.out.println("The winner is: " + current.getWinner() + ".");
//    }
//
//
//    public static void playWithAIAlphaBeta(Game state) {
//        Game current = state;
//
//        while (!current.isFinalState()) {
//            current.print();
//            System.out.println(current.currentPlayer + "'s turn.");
//
//            if (current.currentPlayer == Game.PLAYER_O) {
//                System.out.println("Enter x: ");
//                int x = scanner.nextInt();
//                System.out.println("Enter y: ");
//                int y = scanner.nextInt();
//
//                if (current.canSetSquare(x, y)) {
//                    current = current.setSquare(x, y);
//                } else {
//                    System.out.println("Not valid move.");
//                }
//            } else {
//                current = current.getBestMoveForCurrentPlayerAlphaBeta();
//            }
//        }
//
//        current.print();
//        System.out.println("The winner is: " + current.getWinner() + ".");
//    }
//
//    public static void playAIvsAIMiniMax(Game state) {
//        Game current = state;
//
//        while (!current.isFinalState()) {
//            current.print();
//            System.out.println(current.currentPlayer + "'s turn.");
//
//            current = current.getBestMoveForCurrentPlayerMiniMax();
//        }
//
//        current.print();
//        System.out.println("The winner is: " + current.getWinner() + ".");
//    }
//
//    public static void playAIvsAIAlphaBeta(Game state) {
//        Game current = state;
//
//        while (!current.isFinalState()) {
//            current.print();
//            System.out.println(current.currentPlayer + "'s turn.");
//
//            current = current.getBestMoveForCurrentPlayerAlphaBeta();
//        }
//
//        current.print();
//        System.out.println("The winner is: " + current.getWinner() + ".");
//    }
//




}