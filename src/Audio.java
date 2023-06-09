import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    static void playInLoop(String filename) {
        Clip loopClip;
        try {
            AudioInputStream loopAudioStream = AudioSystem.getAudioInputStream(new File(filename));
            loopClip = AudioSystem.getClip();
            loopClip.open(loopAudioStream);
            loopClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void play(String filename) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
