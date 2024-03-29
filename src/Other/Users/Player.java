package Other.Users;

import java.io.Serializable;

public class Player implements Serializable {
    private ColorSide color;
    private String name;

    public ColorSide getColor() {
        return color;
    }

    public void setColor(ColorSide color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
