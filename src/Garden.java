import java.util.Random;

public class Garden {
    private int sizeX;
    private int sizeY;
    private final Flower[][] flowers;

    public Garden(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        flowers = new Flower[sizeX][sizeY];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Flower[][] getFlowers() {
        return flowers;
    }

    public void initialize(int redFlowerRatio, int yellowFlowerRatio, int blueFlowerRatio, int emptySpaceRatio) {
        Random random = new Random();
        double denominator = redFlowerRatio + yellowFlowerRatio + blueFlowerRatio + emptySpaceRatio;
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                double randomValue = random.nextDouble();

                if(randomValue <= redFlowerRatio / denominator) flowers[x][y] = new RedFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio) / denominator) flowers[x][y] = new YellowFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio + blueFlowerRatio) / denominator) flowers[x][y] = new BlueFlower();
                else flowers[x][y] = null;
            }
        }
    }

    public void print() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                if (flowers[x][y] instanceof RedFlower) {
                    System.out.print("R ");
                } else if (flowers[x][y] instanceof YellowFlower) {
                    System.out.print("Y ");
                } else if (flowers[x][y] instanceof BlueFlower) {
                    System.out.print("B ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public void insertInsect(double probabilityOfInsectAppearance) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        int positionX = 0;
        int positionY = 0;

        if(randomValue <= probabilityOfInsectAppearance) {
            positionX += random.nextInt(sizeX);
            positionY += random.nextInt(sizeY);
            if(flowers[positionX][positionY] != null) flowers[positionX][positionY].setHasInsects(true);
        }
    }

    public void printInsects() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                if(flowers[x][y] != null) {
                    if(flowers[x][y].getHasInsects()) System.out.print("I ");
                    else System.out.print("F ");
                }
                else System.out.print(". ");
            }
            System.out.println();
        }
    }

    public void update() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                if(flowers[x][y] != null) {
                    flowers[x][y].setHydration(flowers[x][y].getHydration() - 5);
                    flowers[x][y].updateHp();
                    if(flowers[x][y].getHp() <= 0) flowers[x][y] = null;
                }
            }
        }
    }
}
