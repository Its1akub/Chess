package Other;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioMethod {
    public static void audioForChess(String path) {
        try {
            File audioGameStart = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioGameStart);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
