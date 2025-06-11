/**
 * Represents a player in the maze game.
 */
public class Player {
    private int x;
    private int y;

    /**
     * Creates a new player at the specified position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the player's current x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the player's current y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Moves the player to a new position.
     *
     * @param newX the new x-coordinate
     * @param newY the new y-coordinate
     */
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
