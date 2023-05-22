public abstract class Flower {
    private int hp;
    private float hydration;
    private boolean hasInsects;
    private boolean hasWeeds;

    public Flower() {
        hp = 100;
        hydration = 50;
        hasInsects = false;
        hasWeeds = false;
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

    public void updateHp() {}

    public int currentDamagePerTick() {
        return 0;
    }
}
