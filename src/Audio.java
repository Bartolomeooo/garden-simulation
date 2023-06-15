import javax.sound.sampled.*;
import java.io.File;

public class Audio {
    private static Clip footstep;
    private static Clip flowerDeath;
    private static Clip flowerRevival;
    private static Clip background;

    public static Clip getFootstep() {
        return footstep;
    }

    public static Clip getFlowerDeath() {
        return flowerDeath;
    }

    public static Clip getFlowerRevival() {
        return flowerRevival;
    }


    public static void open() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("sounds/footstep_fx_16bit.wav"));
            footstep = AudioSystem.getClip();
            footstep.open(audioStream);

            audioStream = AudioSystem.getAudioInputStream(new File("sounds/flower_death_16bit.wav"));
            flowerDeath = AudioSystem.getClip();
            flowerDeath.open(audioStream);

            audioStream = AudioSystem.getAudioInputStream(new File("sounds/flower_revival_fx_16bit.wav"));
            flowerRevival = AudioSystem.getClip();
            flowerRevival.open(audioStream);

            audioStream = AudioSystem.getAudioInputStream(new File("sounds/background_fx_16bit.wav"));
            background = AudioSystem.getClip();
            background.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(Clip clip) {
        clip.stop();
        clip.setMicrosecondPosition(0);

        while(!clip.isActive()) {
            clip.start();
        }
    }

    public static void playBackground() {
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
