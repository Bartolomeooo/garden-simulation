import java.util.Random;

public class Garden {
    private int sizeX;
    private int sizeY;
    private final Flower[][] map;

    public Garden(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        map = new Flower[sizeX][sizeY];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Flower[][] getMap() {
        return map;
    }

    public void initialize(int redFlowerRatio, int yellowFlowerRatio, int blueFlowerRatio, int emptySpaceRatio) {
        Random random = new Random();
        double denominator = redFlowerRatio + yellowFlowerRatio + blueFlowerRatio + emptySpaceRatio;
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeX; y++) {
                double randomValue = random.nextDouble();

                if(randomValue <= redFlowerRatio / denominator) map[x][y] = new RedFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio) / denominator) map[x][y] = new YellowFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio + blueFlowerRatio) / denominator) map[x][y] = new BlueFlower();
                else map[x][y] = null;
            }
        }
    }

    public void print() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                if (map[x][y] instanceof RedFlower) {
                    System.out.print("R ");
                } else if (map[x][y] instanceof YellowFlower) {
                    System.out.print("Y ");
                } else if (map[x][y] instanceof BlueFlower) {
                    System.out.print("B ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public void update() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                if(map[x][y] != null) {
                    map[x][y].updateHp();
                    if(map[x][y].getHp() <= 0) map[x][y] = null;
                }
            }
        }
    }
}
