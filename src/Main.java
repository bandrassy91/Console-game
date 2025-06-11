import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Maze Game!");
        System.out.println("Use W (up), A (left), S (down), D (right) to move. Type 'quit' to exit.");

        // Create the maze and load from file
        Maze maze = new Maze(15, 15); // Default dimensions, will be overridden by file

        // Get the current directory and file path
        String filePath = "palya.txt";
        File file = new File(filePath);

        if (maze.loadFromFile(filePath)) {
            System.out.println("Successfully loaded maze from " + filePath);
        }
        // Create the player at the starting position (1,1) or from the file
        int[] startPosition = maze.getPlayerStartPosition();
        Player player = new Player(startPosition[0], startPosition[1]);

        // Game loop
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display the maze with the player
            maze.display(player);

            // Check if player reached the exit
            if (maze.isExit(player.getX(), player.getY())) {
                System.out.println("Congratulations! You've reached the exit!");
                running = false;
                continue;
            }

            // Get player input
            System.out.print("Enter your move (W/A/S/D): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Thanks for playing!");
                running = false;
            } else if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) {
                // Store original position for debugging
                int originalX = player.getX();
                int originalY = player.getY();

                // Calculate new position
                int newX = originalX;
                int newY = originalY;

                switch (input) {
                    case "w": newY = originalY - 1; break; // Move up
                    case "a": newX = originalX - 1; break; // Move left
                    case "s": newY = originalY + 1; break; // Move down
                    case "d": newX = originalX + 1; break; // Move right
                }

                // Check if the move is valid
                if (maze.isValidMove(newX, newY)) {
                    player.move(newX, newY);
                } else {
                    System.out.println("Invalid move. You can't go through walls!");
                }
            } else {
                System.out.println("Invalid input. Please use W/A/S/D to move or 'quit' to exit.");
            }
        }

        scanner.close();
    }
}