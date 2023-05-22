public class BlueFlower extends Flower {
    @Override
    public void updateHp() {
        int damage = 0;

        if(getHydration() < 0) setHydration(0);
        if(getHydration() == 0) damage += 7;

        if(getHasInsects()) damage += 1;

        if(getHasWeeds()) damage += 2;

        setHp(getHp() - damage);
    }
}
