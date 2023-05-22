public class YellowFlower extends Flower {
    @Override
    public void updateHp() {
        int damage = 0;

        if(getHydration() < 0) setHydration(0);
        if(getHydration() == 0) damage += 5;

        if(getHasInsects()) damage += 2;

        if(getHasWeeds()) damage += 1;

        setHp(getHp() - damage);
    }
}
