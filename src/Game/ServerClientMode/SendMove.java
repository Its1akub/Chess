package Game.ServerClientMode;

import Other.Coordinates;

import java.io.Serializable;

public class SendMove implements Serializable {
    private int previousCX, previousCY;
    private int currentCX, currentCY;

    public SendMove(int pCX, int pCY,int cCX, int cCY) {
        this.previousCX = pCX;
        this.previousCY = pCY;
        this.currentCX = cCX;
        this.currentCY = cCY;
    }

    public int getPreviousCX() {
        return previousCX;
    }

    public int getPreviousCY() {
        return previousCY;
    }

    public int getCurrentCX() {
        return currentCX;
    }

    public void setCurrentCX(int currentCX) {
        this.currentCX = currentCX;
    }

    public int getCurrentCY() {
        return currentCY;
    }

    public void setCurrentCY(int currentCY) {
        this.currentCY = currentCY;
    }

    /**
     * Rotates the coordinates of the current and previous positions.
     * This function rotates the coordinates of the current and previous positions
     * by flipping them horizontally and vertically. It uses the flipCoordinates
     * method to perform the flipping operation. After flipping the coordinates,
     * it updates the currentCX, currentCY, previousCX, and previousCY variables
     * with the new flipped values.
     *
     */
    public void rotateCoordinates() {
        Coordinates current = flipCoordinates(currentCX, currentCY);
        currentCX = current.getX();
        currentCY = current.getY();

        Coordinates previous = flipCoordinates(previousCX, previousCY);
        previousCX = previous.getX();
        previousCY = previous.getY();
    }

    /**
     * Flips the coordinates of a given point on a chessboard by flipping it horizontally and vertically.
     *
     * @param  x  the x-coordinate of the point to be flipped
     * @param  y  the y-coordinate of the point to be flipped
     * @return    the new coordinates of the flipped point
     */
    private Coordinates flipCoordinates(int x, int y) {
        return new Coordinates(7-x, 7-y);
    }


    @Override
    public String toString() {
        return "SendMove{" +
                "previousCX=" + previousCX +
                ", previousCY=" + previousCY +
                ", currentCX=" + currentCX +
                ", currentCY=" + currentCY +
                '}';
    }
}
