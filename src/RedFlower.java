import java.awt.*;

public class RedFlower implements Flower {
    private int hp;
    private float hydration;
    private boolean hasInsects;
    private boolean hasWeeds;

    private static Image iconImage;


    public RedFlower() {
        hp = maxHp;
        hydration = maxHydration;
        hasInsects = false;
        hasWeeds = false;

    }

    public static Image getIconImage() {
        return iconImage;
    }

    public static void setIconImage(Image iconImage) {
        RedFlower.iconImage = iconImage;
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
        if(getHydration() == 0) damage += 3;

        if(getHasInsects()) damage += 2;

        if(getHasWeeds()) damage += 2;

        setHp(getHp() - damage);
    }

    public int currentDamagePerTick() {
        int damage = 0;

        if(getHydration() == 0)
            damage += 3;

        if(getHasInsects())
            damage += 2;

        if(getHasWeeds())
            damage += 2;

        return damage;
    }
}
