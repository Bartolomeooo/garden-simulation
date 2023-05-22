public class RedFlower extends Flower {
    @Override
    public void updateHp() {
        int damage = 0;

        if(getHydration() < 0) setHydration(0);
        if(getHydration() == 0) damage += 3;

        if(getHasInsects()) damage += 2;

        if(getHasWeeds()) damage += 2;

        setHp(getHp() - damage);
    }
}
