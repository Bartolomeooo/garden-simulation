import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class YellowFlower implements Flower {
    private int hp;
    private float hydration;
    private boolean hasInsects;
    private boolean hasWeeds;

    private static Image iconImage;


    public YellowFlower() {
        hp = 1000;
        hydration = 50;
        hasInsects = false;
        hasWeeds = false;
    }

    public static Image getIconImage() {
        return iconImage;
    }

    public static void setIconImage(Image iconImage) {
        YellowFlower.iconImage = iconImage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getHydration() {
        return hydration;
    }
    public void setHydration(float hydration) {
        this.hydration = hydration;
    }

    public boolean getHasInsects() {
        return hasInsects;
    }

    public void setHasInsects(boolean hasInsects) {
        this.hasInsects = hasInsects;
    }

    public boolean getHasWeeds() {
        return hasWeeds;
    }

    public void setHasWeeds(boolean hasWeeds) {
        this.hasWeeds = hasWeeds;
    }


    public void updateHp() {
        int damage = 0;

        if(getHydration() < 0) setHydration(0);
        if(getHydration() == 0) damage += 5;

        if(getHasInsects()) damage += 2;

        if(getHasWeeds()) damage += 1;

        setHp(getHp() - damage);
    }

    public int currentDamagePerTick() {
        int damage = 0;

        if(getHydration() == 0)
            damage += 5;

        if(getHasInsects())
            damage += 2;

        if(getHasWeeds())
            damage += 1;

        return damage;
    }
}
