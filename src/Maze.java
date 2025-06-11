import java.util.Random;
import java.util.Stack;

/**
 * Represents a maze in the game.
 */
public class Maze {
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char PLAYER = 'P';
    private static final char EXIT = 'E';

    private int width;
    private int height;
    private char[][] grid;
    private int exitX;
    private int exitY;

    /**
     * Creates a new maze with the specified dimensions.
     *
     * @param width the width of the maze
     * @param height the height of the maze
     */
    public Maze(int width, int height) {
        // Ensure odd dimensions for proper maze generation
        this.width = (width % 2 == 0) ? width + 1 : width;
        this.height = (height % 2 == 0) ? height + 1 : height;
        this.grid = new char[this.height][this.width];

        // Initialize the grid with walls
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                grid[y][x] = WALL;
            }
        }
    }

    /**
     * Generates a random maze using the Depth-First Search algorithm.
     */
    public void generate() {
        Random random = new Random();
        Stack<int[]> stack = new Stack<>();

        // Start at cell (1, 1)
        int x = 1;
        int y = 1;
        grid[y][x] = PATH;

        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            x = current[0];
            y = current[1];

            // Find all neighbors with distance 2 that are walls
            int[][] neighbors = new int[4][2];
            int count = 0;

            // Check neighboring cells at distance 2
            if (y - 2 > 0 && grid[y - 2][x] == WALL) {
                neighbors[count++] = new int[]{x, y - 2};
            }
            if (y + 2 < height - 1 && grid[y + 2][x] == WALL) {
                neighbors[count++] = new int[]{x, y + 2};
            }
            if (x - 2 > 0 && grid[y][x - 2] == WALL) {
                neighbors[count++] = new int[]{x - 2, y};
            }
            if (x + 2 < width - 1 && grid[y][x + 2] == WALL) {
                neighbors[count++] = new int[]{x + 2, y};
            }

            if (count > 0) {
                // Choose a random neighbor
                int[] next = neighbors[random.nextInt(count)];
                int nextX = next[0];
                int nextY = next[1];

                // Remove the wall between the current cell and the chosen cell
                grid[y + (nextY - y) / 2][x + (nextX - x) / 2] = PATH;
                grid[nextY][nextX] = PATH;

                // Push the chosen cell to the stack
                stack.push(new int[]{nextX, nextY});
            } else {
                // Backtrack
                stack.pop();
            }
        }

        // Set the exit point at the bottom-right corner
        exitX = width - 2;
        exitY = height - 2;
        grid[exitY][exitX] = EXIT;

        // Ensure there's a path to the exit
        if (grid[exitY-1][exitX] == WALL && grid[exitY][exitX-1] == WALL) {
            if (random.nextBoolean()) {
                grid[exitY-1][exitX] = PATH;
            } else {
                grid[exitY][exitX-1] = PATH;
            }
        }

        // Set the starting point
        grid[1][1] = PATH;
    }

    /**
     * Displays the maze with the player's position.
     *
     * @param player the player in the maze
     */
    public void display(Player player) {
        System.out.println("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == player.getX() && y == player.getY()) {
                    System.out.print(PLAYER + " "); // Add space after player
                } else {
                    System.out.print(grid[y][x] + " "); // Add space after each cell
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if a move to the specified position is valid.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        return grid[y][x] != WALL;
    }

    /**
     * Checks if the specified position is the exit.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the position is the exit, false otherwise
     */
    public boolean isExit(int x, int y) {
        return x == exitX && y == exitY;
    }
}
