package LightsOut;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
    private boolean [][] grid;
    private int n,m;

    public State(int n, int m ){
        this.n = n;
        this.m = m;
        grid = new boolean[n][m];
        for (int i = 0;i < n; i++) {
            for (int j = 0;j < m; j++) {
                if(Math.random()>0.5)
                    grid[i][j]=true;

            }
        }
    }

    public State(int n, int m ,String[] rows) {
        this.n = n;
        this.m = m;
        grid = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char cell = rows[i].charAt(j);
                grid[i][j] = (cell == '+');
            }
        }
    }
    public State(State other) {
        this.n = other.n;
        this.m = other.m;
        this.grid = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.grid[i][j] = other.grid[i][j];
            }
        }
    }
    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public boolean getCell(int x, int y) {
        return grid[x][y];
    }

    public boolean[][] turnOffCell(int x, int y) {
        this.grid[x][y]= !this.grid[x][y];
        if (x< grid.length -1 )
            this.grid[x+1][y]= !this.grid[x+1][y];
        if(x>0)
            this.grid[x-1][y]= !this.grid[x-1][y];
        if (y<grid[0].length-1)
            this.grid[x][y+1]= !this.grid[x][y+1];
        if (y>0)
            this.grid[x][y-1]= !this.grid[x][y-1];
        return this.grid ;
    }


    public boolean[][] turnoff(int x, int y ){
      //  System.out.println("Turn off light at (" + x + ", " + y + ")");
        return  this.turnOffCell(x, y);
    }

    public boolean touchable (int x,int y){
        if((x>=0&& x<= this.n-1)&&(y>=0 &&y<=this.m-1))
            return true;
        else  return false ;

    }
    public boolean solved(){
        for (int i=0; i< this.n;i++)
            for (int j=0;j< this.m;j++)
                if(grid[i][j])
                    return false;
        return true;
    }


    public int countTurnedOffCells() {
        int count = 0;
        boolean[][] grid = this.grid;
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                if (!grid[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public ArrayList<State> AllChildren() {
        ArrayList<State> grids= new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                State newState = new State(new State(this));
                newState.turnoff(i, j);
                grids.add(newState);
//                grids.add(newState);
            }
        }
        return grids;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        State other = (State) obj;
        return Arrays.deepEquals(grid, other.grid);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                sb.append(grid[i][j] ? "+" : "-");
            }
            sb.append("\n");

        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.grid);
    }
    //
}
