public class BlueFlower implements Flower {
    private int hp;
    private float hydration;
    private boolean hasInsects;
    private boolean hasWeeds;


    public BlueFlower() {
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


    public void updateHp() {
        int damage = 0;

        if(getHydration() < 0) setHydration(0);
        if(getHydration() == 0) damage += 7;

        if(getHasInsects()) damage += 1;

        if(getHasWeeds()) damage += 2;

        setHp(getHp() - damage);
    }

    public int currentDamagePerTick() {
        int damage = 0;

        if(getHydration() == 0)
            damage += 7;

        if(getHasInsects())
            damage += 1;

        if(getHasWeeds())
            damage += 2;

        return damage;
    }
}
