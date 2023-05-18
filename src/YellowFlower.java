public class YellowFlower extends Flower {
    @Override
    public void updateHp() {
        setHp(getHp() - 5);
    }
}
