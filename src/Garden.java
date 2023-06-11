import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;

public class Garden {
    private int size;
    private final Flower[][] flowers;

    public Garden(int size) {
        this.size = size;
        flowers = new Flower[size][size];
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
            }
        }
    }

    public void print() {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
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
            positionX += random.nextInt(size);
            positionY += random.nextInt(size);
            if(flowers[positionX][positionY] != null) flowers[positionX][positionY].setHasInsects(true);
        }
    }

    public void insertWeeds(double probabilityOfWeedsAppearance) {
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

    public void spreadWeeds(double probabilityOfWeedsSpread) {
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

    public void printInsects() {
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
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
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if(flowers[x][y] != null) {
                    flowers[x][y].setHydration(flowers[x][y].getHydration() - 5);
                    flowers[x][y].updateHp();
                    if(flowers[x][y].getHp() <= 0) {
                        flowers[x][y] = null;
                        Audio.play("sounds/flower_death_16bit.wav");
                    }
                }
            }
        }
    }
}
