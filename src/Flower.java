public interface Flower {
    int getHp();
    void setHp(int hp);

    float getHydration();
    void setHydration(float hydration);

    boolean getHasInsects();
    void setHasInsects(boolean hasInsects);

    boolean getHasWeeds();
    void setHasWeeds(boolean hasWeeds);

    void updateHp();

    int currentDamagePerTick();
}
