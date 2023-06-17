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


    @Override
    public void update(Garden garden) {
        if(actionTimer > 0) { // Gardener hasn't finished healing the flower yet
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
        } else { // Gardener has finished healing the flower so he can move
            Audio.play(Audio.getFootstep());

            switch (movementIndex) { // Move
                case 1 -> moveRandomly(garden);
                case 2 -> patternMove(garden.getSize(), 1);
                case 3 -> patternMove(garden.getSize(), 2);
            }

            if(garden.getFlowers()[positionX][positionY] != null) {
                actionTimer += this.timeToHealTheFlower(garden);
            }
        }
    }

    private void patternMove(int maxIndex, int pattern) {
        if (verticalDirection.equals("Forward")) {
            if (horizontalDirection.equals("Right")) { // Going forward right
                if (positionX + 1 == maxIndex) { // Out of range X
                    if (positionY + 1 == maxIndex) { // Out of range X and Y [maxX, maxY]
                        verticalDirection = "Backwards";
                        switch(pattern) {
                            case 1 -> positionX--; // Pattern 1 - go left
                            case 2 -> positionY--; // Pattern 2 - go up
                        }
                    } else {
                        positionY++;
                    }

                    horizontalDirection = "Left";
                } else {
                    positionX++;
                }
            } else { // Going forward left
                if (positionX - 1 < 0) { // Out of range X
                    if (positionY + 1 == maxIndex) { // Out of range X and Y [0, maxY]
                        verticalDirection = "Backwards";
                        switch(pattern) {
                            case 1 -> positionX++; // Pattern 1 - go right
                            case 2 -> positionY--; // Pattern 2 - go up
                        }
                    } else {
                        positionY++;
                    }

                    horizontalDirection = "Right";
                } else {
                    positionX--;
                }
            }
        } else {
            if (horizontalDirection.equals("Right")) { // Going backwards right
                if (positionX + 1 == maxIndex) { // Out of range X
                    if (positionY - 1 < 0) { // Out of range X and Y [maxX, 0]
                        verticalDirection = "Forward";
                        switch(pattern) {
                            case 1 -> positionX--; // Pattern 1 - go left
                            case 2 -> positionY++; // Pattern 2 - go down
                        }
                    } else {
                        positionY--;
                    }

                    horizontalDirection = "Left";
                } else {
                    positionX++;
                }
            } else { // Going backwards left
                if (positionX - 1 < 0) { // Out of range X
                    if (positionY - 1 < 0) { // Out of range X and Y [0, 0]
                        verticalDirection = "Forward";
                        switch(pattern) {
                            case 1 -> positionX++; // Pattern 1 - go right
                            case 2 -> positionY++; // Pattern 2 - go down
                        }
                    } else {
                        positionY--;
                    }

                    horizontalDirection = "Right";
                } else {
                    positionX--;
                }
            }
        }
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
}
