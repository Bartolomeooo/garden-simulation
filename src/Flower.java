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
    public void updateHp() {}
}
