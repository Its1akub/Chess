package Other;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AudioMethod {
    /**
     * Plays an audio file for a chess game.
     *
     * @param  path  the path to the audio file
     */
    public void audioForChess(String path) {
        try {

            InputStream resourceStream = Objects.requireNonNull(AudioMethod.class.getClassLoader().getResourceAsStream(path), "Resource not found: " + path);
            byte[] audioBytes = resourceStream.readAllBytes();

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream)) {

                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                clip.start();
            }

        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + path);
            e.printStackTrace();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
