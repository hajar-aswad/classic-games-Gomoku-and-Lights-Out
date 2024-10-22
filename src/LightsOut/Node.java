package LightsOut;

import java.util.ArrayList;
import java.util.List;
public class Node implements Comparable<Node>{
    private State state;
    private Node parent;
    private ArrayList<Node> children;
    private int depth;
    public int cost;
    public int heuristic;
    public  int onlights;
    public  int G;
    public static final char PLAYER_off = '-';
    public static final char PLAYER_on = '+';
    public Node(State state, Node parent, ArrayList<Node> children, int depth) {
        this.state = state;
        this.parent = parent;
        this.children = children;
        this.depth = depth;
        this.cost= computeCost();
        this.heuristic=heuristic();
        this.G=G();
    }
    public int computeCost(){
        if (this.parent==null)
            return 0;
        else return parent.cost+this.getCostFromFatherToChild();

    }

    public int G(){
        if (this.parent==null)
            return 0;
        else return this.heuristic+this.cost;

    }
    public int getG(){
        return this.G;
    }
    public int heuristic() {
        int lightsOnCount = 0;
        for (int i = 0; i < this.state.getN(); i++) {
            for (int j = 0; j < this.state.getM(); j++) {
                if (this.getState().getCell(i,j)) {
                    lightsOnCount++;
                }
            }
        }
        this.heuristic=lightsOnCount-this.heuristic1();
        return lightsOnCount;
    }

    public int heuristic1() {
        int a = 0;

        for (int i = 0; i < this.getState().getN(); i++) {
            for (int j = 0; j < this.getState().getM(); j++) {
                if (this.getState().getCell(i,j)) { // If the light is on
                    int neighborsOn = countNeighborsOn(i, j);
                    a += neighborsOn;
                }
            }
        }
        this.heuristic=a;
        return a;
    }

    public void printPath1() {
        Node current = this;
        while (current != null) {
            if(current.getDepth()<18)
            {  System.out.println("Depth: " + current.getDepth());}
            else if(current.getDepth()>36)
            {
                System.out.println("Depth: " + (current.getDepth()-17));
            }
            System.out.println("State:\n" + current.getState());
            System.out.println("---------------------------");
            current = current.getParent();
        }
    }
    private int countNeighborsOn(int row, int col) {
        int count = 0;
        State nextState = new State(this.getState());
        nextState.turnoff(row, col);
        if (row > 0 && !nextState.getCell(row - 1,col)){  count++;
        if (row < nextState.getN() - 1 &&(!nextState.getCell(row + 1,col))){  count++;
        if (col > 0 && (!nextState.getCell(row,col - 1) )){  count++;
        if (col <nextState.getM() - 1 &&( !nextState.getCell(row,col + 1)) ) {
            count++;
        }}}}
        return count;
    }
    // Getters
    public int getCostFromFatherToChild() {
        int count = 0;
        for (int i = 0; i < this.state.getN(); i++) {
            for (int j = 0; j < this.state.getM(); j++) {
                if (this.getState().getCell(i,j) && !getParentState().getCell(i,j)) {
                    count++;
                }
            }
        }
        this.onlights=count;
        return count;
    }

    public State getParentState() {
        return parent.getState();
    }
    public State getState() {
        return state;
    }
    public int getHeuristic(){
        return this.heuristic;
    }
    public int getCost(){
        return this.cost;
    }
    public Node getParent() {
        return parent;
    }
    public List<Node> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
        for (Node child : children) {
            child.setParent(this);
            child.setDepth(this.depth + 1);
        }
    }


    public ArrayList<Node> AllChildren() {
        ArrayList<Node> nodes = new ArrayList<>();
        List<State> childStates = this.state.AllChildren();

        for (State childState : childStates) {
            Node childNode = new Node(childState, this, null, this.depth + 1);
            nodes.add(childNode);
        }
        this.children=nodes;
        return nodes;
    }








    public boolean solved(){
        return this.state.solved();
    }


    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void printPath() {
        Node current = this;
        while (current != null) {
              System.out.println("Depth: " + current.getDepth());
            System.out.println("State:\n" + current.getState());
            System.out.println("---------------------------");
            current = current.getParent();
        }
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost,other.cost);
    }

//    public int staticEvaluation(int depth) {
//        if (this.currentState == Game.PLAYER_O) {
//            return 20 - depth;
//        } else if (this.currentState == Game.PLAYER_X) {
//            return (-20) + depth;
//        }
//        return 0;
//    }


//    public Game getBestMoveForCurrentPlayerMiniMax() {
//        boolean currentPlayerIsO = this.currentPlayer == Game.PLAYER_O;
//        int bestScore = currentPlayerIsO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        Game bestMove = null;
//        int currentDepth = this.countNonEmpties();
//
//        for (Game move : this.possibleMoves()) {
//            int score = AI.miniMax(move, currentDepth, !currentPlayerIsO);
//
//            if (currentPlayerIsO) {
//                if (score > bestScore) {
//                    bestScore = score;
//                    bestMove = move;
//                }
//            } else {
//                if (score < bestScore) {
//                    bestScore = score;
//                    bestMove = move;
//                }
//            }
//        }
//
//        return bestMove;
//    }
//
//    public Game getBestMoveForCurrentPlayerAlphaBeta() {
//        boolean currentPlayerIsO = this.currentPlayer == Game.PLAYER_O;
//        int bestScore = currentPlayerIsO ? Integer.MIN_VALUE : Integer.MAX_VALUE;
//        Game bestMove = null;
//        int currentDepth = this.countNonEmpties();
//
//        for (Game move : this.possibleMoves()) {
//            int score = AI.alphaBeta(move, currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, !currentPlayerIsO);
//
//            if (currentPlayerIsO) {
//                if (score > bestScore) {
//                    bestScore = score;
//                    bestMove = move;
//                }
//            } else {
//                if (score < bestScore) {
//                    bestScore = score;
//                    bestMove = move;
//                }
//            }
//        }
//
//        return bestMove;
//    }

}

