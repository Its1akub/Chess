import javax.swing.*;
import java.awt.*;

public class SelectingGame extends JPanel {
    JButton localButton;
    public SelectingGame() {
        setBackground(Color.RED);
        localButton = new JButton("Local");
        add(localButton, BorderLayout.CENTER);
    }


}
