public abstract class Flower {
    private int hp = 100;
    private float hydration;
    private boolean hasInsects;
    private boolean hasWeeds;

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

    public void updateHp() {}

}
