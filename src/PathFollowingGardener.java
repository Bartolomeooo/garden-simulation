import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;

public class PathFollowingGardener extends Gardener {
    private String horizontalDirection;
    private String verticalDirection;
    private final int movementIndex;


    public PathFollowingGardener(int movementIndex) {
        super();
        horizontalDirection = "Right";
        verticalDirection = "Forward";
        this.movementIndex = movementIndex;
    }

    private void retractingSnakeMove(int maxIndex) { // - Going the same snake-shaped path
        if (verticalDirection.equals("Forward")) {
            if (horizontalDirection.equals("Right")) { // Going forward right
                if (positionY + 1 == maxIndex) { // Out of range Y
                    if (positionX + 1 == maxIndex) { // Out of range X and Y [maxX, maxY]
                        verticalDirection = "Backwards";
                        positionY--;
                    } else
                        positionX++;

                    horizontalDirection = "Left";
                } else
                    positionY++;
            } else { // Going forward left
                if (positionY - 1 < 0) { // Out of range Y
                    if (positionX + 1 == maxIndex) { // Out of range X and Y [maxX, 0]
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
                if (positionY + 1 == maxIndex) { // Out of range Y
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

    private void turningSnakeMove(int maxIndex) { // - Going the snake-shaped path with a turn (two mirrored paths)
        if (verticalDirection.equals("Forward")) {
            if (horizontalDirection.equals("Right")) { // Going forward right
                if (positionY + 1 == maxIndex) { // Out of range Y
                    if (positionX + 1 == maxIndex) { // Out of range X and Y [maxX, maxY]
                        verticalDirection = "Backwards";
                        positionX--; //
                    } else
                        positionX++;

                    horizontalDirection = "Left";
                } else
                    positionY++;
            } else { // Going forward left
                if (positionY - 1 < 0) { // Out of range Y
                    if (positionX + 1 == maxIndex) { // Out of range X and Y [maxX, 0]
                        verticalDirection = "Backwards";
                        positionX--; //
                    } else
                        positionX++;

                    horizontalDirection = "Right";
                } else
                    positionY--;
            }
        } else {
            if (horizontalDirection.equals("Right")) { // Going backwards right
                if (positionY + 1 == maxIndex) { // Out of range Y
                    if (positionX - 1 < 0) { // Out of range X and Y [0, maxY]
                        verticalDirection = "Forward";
                        positionX++; //
                    } else
                        positionX--;

                    horizontalDirection = "Left";
                } else
                    positionY++;
            } else { // Going backwards left
                if (positionY - 1 < 0) { // Out of range Y
                    if (positionX - 1 < 0) { // Out of range X and Y [0, 0]
                        verticalDirection = "Forward";
                        positionX++; //
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

    private void moveRandomly(Garden garden) {
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
        while (isOutOfRange(positionX + displacementX, positionY + displacementY, garden.getSize(), garden.getSize()) || (displacementX == 0 && displacementY == 0));

        positionX += displacementX;
        positionY += displacementY;
    }

    @Override
    public void update(Garden garden) {
        if(actionTimer > 0) {
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
        } else {
            try {
                // Playing footstep audio from a wav file
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("footstep_fx.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch(movementIndex){ // Move
                case 1:
                    moveRandomly(garden);
                    break;
                case 2:
                    retractingSnakeMove(garden.getSize());
                    break;
                case 3:
                    turningSnakeMove(garden.getSize());
                    break;
            }

            if(garden.getFlowers()[positionX][positionY] != null) {
                //Debug
                System.out.println("Insects: " + garden.getFlowers()[positionX][positionY].getHasInsects() + "\nWeeds: " + garden.getFlowers()[positionX][positionY].getHasWeeds());

                actionTimer += water(garden) + removeInsects(garden) + removeWeeds(garden); // How much time (ticks) gardener needs to heal the flower
            }
        }
    }
}
