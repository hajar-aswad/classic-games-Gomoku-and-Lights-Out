package LightsOut;



import java.lang.reflect.GenericArrayType;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the game menu:");
            System.out.println("1. Start the game");
            System.out.println("2. Enter the game manually");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            try {
                choice = input.nextInt();
                State game = null;
                Node node=null;
                switch (choice) {
                    case 1:
                        StrategySolver.UserPlay(input);
                        break;
                    case 2:
                        State manualGrid =  new State(StrategySolver.enterGridManually(input)) ;
                        if (manualGrid != null) {
                            System.out.println("1. Play the game");
                            System.out.println("2. show sloution by Dfs ");
                            System.out.println("3. show sloution by Bfs ");
                            System.out.println("4. show sloution by Ucs ");
                            System.out.println("5. show sloution by Hill climming ");
                            System.out.println("6. show sloution by A *");
                            System.out.println("7. show sloution by minimax ");
                            System.out.println("8. Exit");
                            System.out.println("Enter your choice: ");
                            int playChoice = input.nextInt();
                            switch (playChoice) {
                                case 1:
                                    StrategySolver.UserPlay(input,manualGrid);
                                    break;
                                case 2:
                                    game = new State(manualGrid);
                                    node =new Node(game,null, null,0);
                                    Node solution =   StrategySolver.dfsNode(node);

                                    break;
                                case 3:
                                    game = new State(manualGrid);
                                    node=new Node(game,null,null,0);
                                    Node solution1 =   StrategySolver.bfsNode(node);

                                    break;
                                case 4:
                                    game = new State(manualGrid);
                                    node=new Node(game,null,null,0);
                                    int solution2 = StrategySolver.UCS(node);

                                    break;
                                case 5:
                                    game = new State(manualGrid);
                                    node=new Node(game,null,null,0);
                                    int solution3 = StrategySolver.hillClim(node);
                                    break;
                                case 6:
                                    game = new State(manualGrid);
                                    node=new Node(game,null,null,0);
                                    int solution4 = StrategySolver.hillClimbing(node);
                                    break;
                                case 7:
                                    game = new State(manualGrid);
                                    node=new Node(game,null,null,0);
                                    int solution5 = StrategySolver.miniMax(node,0,true);
                                    break;
                                case 8:
                                     System.out.println("Exiting the game.");
                                     System.exit(0);
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please enter 1 to play or 2 to exit.");
                                    break;
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 to start the game, 2 to enter the game manually, or 3 to exit.");
                        break;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // Clear the buffer
                choice = 0;
            }
        } while (true);
    }
}

