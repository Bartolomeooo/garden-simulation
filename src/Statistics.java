import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Statistics {
    private static int initialNumberOfRedFlowers = 0;
    private static int initialNumberOfYellowFlowers = 0;
    private static int initialNumberOfBlueFlowers = 0;

    private static int numberOfDeadRedFlowers = 0;
    private static int numberOfDeadYellowFlowers = 0;
    private static int numberOfDeadBlueFlowers = 0;

    private static int numberOfFlowersThatNeedWater = 0;
    private static int numberOfFlowersThatHaveInsects = 0;
    private static int numberOfFlowersThatHaveWeeds = 0;

    private static int tick = 1;

    public static void setInitialStatistics(Flower flower) {
        if(flower instanceof RedFlower) initialNumberOfRedFlowers++;
        if(flower instanceof YellowFlower) initialNumberOfYellowFlowers++;
        if(flower instanceof BlueFlower) initialNumberOfBlueFlowers++;
    }

    private static void updateNumberOfDeadFlowers(Flower flower) {
        if(flower instanceof RedFlower && flower.getHp() <= 0) numberOfDeadRedFlowers++;
        if(flower instanceof YellowFlower && flower.getHp() <= 0) numberOfDeadYellowFlowers++;
        if(flower instanceof BlueFlower && flower.getHp() <= 0) numberOfDeadBlueFlowers++;
    }

    private static void updateNumberOfFlowersThatNeedWater(Flower flower) {
        if(flower.getHydration() == 0) numberOfFlowersThatNeedWater++;
    }

    private static void updateNumberOfFlowersThatHaveInsects(Flower flower) {
        if(flower.getHasInsects()) numberOfFlowersThatHaveInsects++;
    }

    private static void updateNumberOfFlowersThatHaveWeeds(Flower flower) {
        if(flower.getHasWeeds()) numberOfFlowersThatHaveWeeds++;
    }

    private static void resetValues() {
        numberOfFlowersThatNeedWater = 0;
        numberOfFlowersThatHaveInsects = 0;
        numberOfFlowersThatHaveWeeds = 0;
    }
    public static void update(Flower flower) {
        updateNumberOfDeadFlowers(flower);
        updateNumberOfFlowersThatNeedWater(flower);
        updateNumberOfFlowersThatHaveInsects(flower);
        updateNumberOfFlowersThatHaveWeeds(flower);
    }

    private static String makeLine() {
        return tick + ";" + (initialNumberOfRedFlowers - numberOfDeadRedFlowers) + ";" + (initialNumberOfYellowFlowers - numberOfDeadYellowFlowers) + ";" + (initialNumberOfBlueFlowers - numberOfDeadBlueFlowers) + ";" + numberOfFlowersThatHaveInsects + ";" + numberOfFlowersThatHaveWeeds;
    }

    public static void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(makeLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        tick++;
        resetValues();
    }

    public static void clearFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print("");
            writer.println("Generation;Red Flowers;Yellow Flowers;Blue Flowers;Insects;Weeds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
