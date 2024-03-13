package Other;

import Game.StopMenu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeKeyAdapter extends KeyAdapter {
    StopMenu stopMenu;
    boolean stopMenuShown;
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stopMenuShown = !stopMenuShown;
            stopMenu.setVisible(stopMenuShown);
            System.out.println(e.getKeyChar());
            stopMenu.revalidate();
            stopMenu.repaint();

        }
    }
    public void getStopMenu(StopMenu stopMenu) {
        this.stopMenu = stopMenu;
    }

    public void getStopMenuShown(boolean stopMenuShown) {
       this.stopMenuShown = stopMenuShown;
    }


}
