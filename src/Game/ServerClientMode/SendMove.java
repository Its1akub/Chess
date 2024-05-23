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

    public void rotateCoordinates() {
        Coordinates current = flipCoordinates(currentCX, currentCY);
        currentCX = current.getX();
        currentCY = current.getY();

        Coordinates previous = flipCoordinates(previousCX, previousCY);
        previousCX = previous.getX();
        previousCY = previous.getY();
    }
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
