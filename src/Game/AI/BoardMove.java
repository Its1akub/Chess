package Game.AI;

public class BoardMove {
    private String type;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public BoardMove(String type,int startX, int startY, int endX, int endY) {
        this.type = type;
        this.startX = startX;
        this.startY = startY;
        this.endX =endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " (y: " + startY + ", x: " + startX + ") -> (y:" + endY + ", x:" + endX + ")";
    }
}

