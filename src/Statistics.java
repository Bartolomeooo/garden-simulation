import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Statistics {

    // Amounts of all the flowers created during initialization of the garden
    private static int initialNumberOfRedFlowers = 0;
    private static int initialNumberOfYellowFlowers = 0;
    private static int initialNumberOfBlueFlowers = 0;

    // Amounts of all the flowers that died in a particular generation
    private static int numberOfDeadRedFlowers = 0;
    private static int numberOfDeadYellowFlowers = 0;
    private static int numberOfDeadBlueFlowers = 0;

    // Total numbers of insects and weeds in the garden
    private static int numberOfFlowersThatHaveInsects = 0;
    private static int numberOfFlowersThatHaveWeeds = 0;

    // Position of the gardener
    private static int gardenerPositionX;
    private static int gardenerPositionY;

    // Current Generation
    private static int tick = 1;

    // Set the initial number of flowers
    public static void setInitialStatistics(Flower flower) {
        if(flower instanceof RedFlower) initialNumberOfRedFlowers++;
        if(flower instanceof YellowFlower) initialNumberOfYellowFlowers++;
        if(flower instanceof BlueFlower) initialNumberOfBlueFlowers++;
    }

    // Update the number of dead flowers
    private static void updateNumberOfDeadFlowers(Flower flower) {
        if(flower instanceof RedFlower && flower.getHp() <= 0) numberOfDeadRedFlowers++;
        if(flower instanceof YellowFlower && flower.getHp() <= 0) numberOfDeadYellowFlowers++;
        if(flower instanceof BlueFlower && flower.getHp() <= 0) numberOfDeadBlueFlowers++;
    }

    // Update the number of flowers with insects
    private static void updateNumberOfFlowersThatHaveInsects(Flower flower) {
        if(flower.getHasInsects()) numberOfFlowersThatHaveInsects++;
    }

    // Update the number of flowers with weeds
    private static void updateNumberOfFlowersThatHaveWeeds(Flower flower) {
        if(flower.getHasWeeds()) numberOfFlowersThatHaveWeeds++;
    }

    // Update the gardener position variables
    public static void updateGardenerPosition(Gardener gardener) {
        gardenerPositionX = gardener.positionX;
        gardenerPositionY = gardener.positionY;
    }

    // Reset the values that need to be recounted
    private static void resetValues() {
        numberOfFlowersThatHaveInsects = 0;
        numberOfFlowersThatHaveWeeds = 0;
    }

    // Update all the flower data
    public static void update(Flower flower) {
        updateNumberOfDeadFlowers(flower);
        updateNumberOfFlowersThatHaveInsects(flower);
        updateNumberOfFlowersThatHaveWeeds(flower);
    }

    // Create a single line of statistics
    private static String makeLine() {
        return tick + ";" + (initialNumberOfRedFlowers - numberOfDeadRedFlowers) + ";" + (initialNumberOfYellowFlowers - numberOfDeadYellowFlowers) + ";" + (initialNumberOfBlueFlowers - numberOfDeadBlueFlowers) + ";" + numberOfFlowersThatHaveInsects + ";" + numberOfFlowersThatHaveWeeds + ";" + gardenerPositionX + ";" + gardenerPositionY;
    }

    // Print a single line of data in the file
    public static void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(makeLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        tick++;
        resetValues();
    }

    // Clear the statistics file and print headings at the beginning
    public static void clearFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print("");
            writer.println("Generation;Red Flowers;Yellow Flowers;Blue Flowers;Insects;Weeds;Gardener Horizontal Position;Gardener Vertical Position");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
