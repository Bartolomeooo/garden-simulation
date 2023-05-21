import java.util.Random;

public class Gardener {
    private int positionX;
    private int positionY;
    private String horizontalDirection;
    private String verticalDirection;
    private int actionTimer;

    public Gardener() {
        positionX = 0;
        positionY = 0;
        horizontalDirection = "Right";
        verticalDirection = "Forward";
    }

    public void move(int maxX, int maxY) {
        if (verticalDirection.equals("Forward")) {
            if (horizontalDirection.equals("Right")) { // Going forward right
                if (positionY + 1 == maxY) { // Out of range Y
                    if (positionX + 1 == maxX) { // Out of range X and Y [maxX, maxY]
                        verticalDirection = "Backwards";
                        positionY--;
                    } else
                        positionX++;

                    horizontalDirection = "Left";
                } else
                    positionY++;
            } else { // Going forward left
                if (positionY - 1 < 0) { // Out of range Y
                    if (positionX + 1 == maxX) { // Out of range X and Y [maxX, 0]
                        verticalDirection = "Backwards";
                        positionY++;
                    } else
                        positionX++;

                    horizontalDirection = "Right";
                } else
                    positionY--;
            }
        } else {
            if (horizontalDirection.equals("Right")) { // Going backwards right
                if (positionY + 1 == maxY) { // Out of range Y
                    if (positionX - 1 < 0) { // Out of range X and Y [0, maxY]
                        verticalDirection = "Forward";
                        positionY--;
                    } else
                        positionX--;

                    horizontalDirection = "Left";
                } else
                    positionY++;
            } else { // Going backwards left
                if (positionY - 1 < 0) { // Out of range Y
                    if (positionX - 1 < 0) { // Out of range X and Y [0, 0]
                        verticalDirection = "Forward";
                        positionY++;
                    } else
                        positionX--;

                    horizontalDirection = "Right";
                } else
                    positionY--;
            }
        }

        // Debug
        System.out.println(verticalDirection + ", " + horizontalDirection + " [" + positionX + "," + positionY + "]");
    }

    private boolean isOutOfRange(int positionX, int positionY, int maxX, int maxY) {
        return positionX < 0 || positionX >= maxX || positionY < 0 || positionY >= maxY;
    }

    private boolean isFirstTick = true;

    public void moveRandomly(Garden garden) {
        Random random = new Random();
        int displacementX;
        int displacementY;

        if (isFirstTick) {
            isFirstTick = false;
            return;
        }

        do {
            // Displacement varies in the range {-1, 0, 1} on both axes
            displacementX = random.nextInt(3) - 1;
            displacementY = random.nextInt(3) - 1;
        }
        while (isOutOfRange(positionX + displacementX, positionY + displacementY, garden.getSizeX(), garden.getSizeY()) || (displacementX == 0 && displacementY == 0));

        positionX += displacementX;
        positionY += displacementY;
    }

    public void update(Garden garden) {
        if(actionTimer > 0) {
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
        } else {
            move(garden.getSizeX(), garden.getSizeY());
            if(garden.getFlowers()[positionX][positionY] != null) {
                //Debug
                System.out.println("Insects: " + garden.getFlowers()[positionX][positionY].getHasInsects() + "\nWeeds: " + garden.getFlowers()[positionX][positionY].getHasWeeds());

                actionTimer += water(garden) + removeInsects(garden) + removeWeeds(garden); //how much time (ticks) gardener needs to heal a flower
            }
        }
    }

    public int water(Garden garden) { //returns how much time gardener needs to water the flower
        garden.getFlowers()[positionX][positionY].setHydration(50); //max hydration
        garden.getFlowers()[positionX][positionY].setHp(100); //max hp

        return 1;
    }

    public int removeInsects(Garden garden) { //returns how much time gardener needs to remove insects from the flower
        if(garden.getFlowers()[positionX][positionY].getHasInsects()) {
            garden.getFlowers()[positionX][positionY].setHasInsects(false);
            return 2;
        }

        return 0;
    }

    public int removeWeeds(Garden garden) { //returns how much time gardener needs to remove weeds from the flower
        if(garden.getFlowers()[positionX][positionY].getHasWeeds()) {
            garden.getFlowers()[positionX][positionY].setHasWeeds(false);
            return 3;
        }

        return 0;
    }

    public void print(int maxX, int maxY) {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (x == positionX && y == positionY)
                    System.out.print("O ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    public void printHp(Garden garden) {
        for (int x = 0; x < garden.getSizeX(); x++) {
            for (int y = 0; y < garden.getSizeY(); y++) {
                if (x == positionX && y == positionY)
                    System.out.printf("%4s", "▨");
                else {
                    if(garden.getFlowers()[x][y] == null)
                        System.out.printf("%4s", "X");
                    else
                        System.out.printf("%4d", garden.getFlowers()[x][y].getHp());
                }
            }
            System.out.println();
        }
    }

    public void printInsectsAndWeeds(Garden garden) {
        for (int x = 0; x < garden.getSizeX(); x++) {
            for (int y = 0; y < garden.getSizeY(); y++) {
                if (x == positionX && y == positionY)
                    System.out.printf("%4s", "▨");
                else {
                    if(garden.getFlowers()[x][y] == null)
                        System.out.printf("%4s", "X");
                    else {
                        if(garden.getFlowers()[x][y].getHasInsects() && garden.getFlowers()[x][y].getHasWeeds())
                            System.out.printf("%4s", "■");
                        else if(garden.getFlowers()[x][y].getHasInsects())
                            System.out.printf("%4s", "◧");
                        else if(garden.getFlowers()[x][y].getHasWeeds())
                            System.out.printf("%4s", "◨");
                        else
                            System.out.printf("%4d", garden.getFlowers()[x][y].getHp());
                            //System.out.printf("%4s", "□");
                    }
                }
            }
            System.out.println();
        }
    }
}
