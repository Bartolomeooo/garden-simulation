import java.awt.*;
import java.util.Random;

public class Garden {
    private final int size;
    private final Flower[][] flowers;
    private final double probabilityOfInsectAppearance;
    private final double probabilityOfWeedsAppearance;
    private final double probabilityOfWeedsSpread;

    private static Image weedsIcon;
    private static Image insectIcon;

    public static void setInsectIcon(Image insectIcon) {
        Garden.insectIcon = insectIcon;
    }
    public static Image getInsectIcon() {
        return insectIcon;
    }

    public static void setWeedsIcon(Image weedsIcon) {
        Garden.weedsIcon = weedsIcon;
    }
    public static Image getWeedsIcon() {
        return weedsIcon;
    }

    public Garden(int size, double probabilityOfInsectAppearance, double probabilityOfWeedsAppearance, double probabilityOfWeedsSpread) {
        this.size = size;
        flowers = new Flower[size][size];
        this.probabilityOfInsectAppearance = probabilityOfInsectAppearance;
        this.probabilityOfWeedsAppearance = probabilityOfWeedsAppearance;
        this.probabilityOfWeedsSpread = probabilityOfWeedsSpread;
    }

    public int getSize() {
        return size;
    }

    public Flower[][] getFlowers() {
        return flowers;
    }


    public void initialize(int redFlowerRatio, int yellowFlowerRatio, int blueFlowerRatio, int emptySpaceRatio) {
        Random random = new Random();
        double denominator = redFlowerRatio + yellowFlowerRatio + blueFlowerRatio + emptySpaceRatio;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                double randomValue = random.nextDouble();

                if(randomValue <= redFlowerRatio / denominator) flowers[x][y] = new RedFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio) / denominator) flowers[x][y] = new YellowFlower();
                else if(randomValue <= (redFlowerRatio + yellowFlowerRatio + blueFlowerRatio) / denominator) flowers[x][y] = new BlueFlower();
                else flowers[x][y] = null;

                if(flowers[x][y] != null) Statistics.setInitialStatistics(flowers[x][y]);
            }
        }
    }

    public void update() {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(flowers[x][y] != null) {
                    flowers[x][y].setHydration(flowers[x][y].getHydration() - 1);
                    flowers[x][y].updateHp();
                    Statistics.update(flowers[x][y]);
                    if(flowers[x][y].getHp() <= 0) {
                        flowers[x][y] = null;
                        Audio.play(Audio.getFlowerDeath());
                    }
                }
            }
        }

        this.insertInsect();
        this.insertWeeds();
        this.spreadWeeds();

        Statistics.saveToFile("statistics/garden_simulation.txt");
    }

    private void insertInsect() {
        Random random = new Random();
        double randomValue = random.nextDouble();

        int positionX = 0;
        int positionY = 0;

        if(randomValue <= probabilityOfInsectAppearance) {
            positionX += random.nextInt(size);
            positionY += random.nextInt(size);
            if(flowers[positionX][positionY] != null) flowers[positionX][positionY].setHasInsects(true);
        }
    }

    private void insertWeeds() {
        Random random = new Random();
        double randomValue = random.nextDouble();

        int positionX = 0;
        int positionY = 0;

        if(randomValue <= probabilityOfWeedsAppearance) {
            positionX += random.nextInt(size);
            positionY += random.nextInt(size);
            if(flowers[positionX][positionY] != null) flowers[positionX][positionY].setHasWeeds(true);
        }
    }

    private boolean isOutOfRange(int positionX, int positionY, int maxX, int maxY) {
        return positionX < 0 || positionX >= maxX || positionY < 0 || positionY >= maxY;
    }

    private void spreadWeeds() {
        Random random = new Random();

        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(flowers[x][y] != null) {
                    double randomValue = random.nextDouble();
                    if(flowers[x][y].getHasWeeds() && randomValue <= probabilityOfWeedsSpread) {
                        int displacementX;
                        int displacementY;

                        // Displacement varies in the range {-1, 0, 1} on both axes
                        displacementX = random.nextInt(3) - 1;
                        displacementY = random.nextInt(3) - 1;

                        if(!isOutOfRange(x + displacementX, y + displacementY, size, size) && flowers[x + displacementX][y + displacementY] != null)
                            flowers[x + displacementX][y + displacementY].setHasWeeds(true);
                    }
                }
            }
        }
    }
}
