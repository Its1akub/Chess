package Other.Users;

import java.io.Serializable;

public class Player implements Serializable {
    private ColorSide color;

    public Player(ColorSide color) {
        this.color = color;
    }

    public ColorSide getColor() {
        return color;
    }

    public void setColor(ColorSide color) {
        this.color = color;
    }
}
