import java.util.Random;

public class PathFollowingGardener extends Gardener {
    private int horizontalDirection;
    private int verticalDirection;
    private final int movementIndex;

    public PathFollowingGardener(int movementIndex) {
        super();
        horizontalDirection = 1; // 1 = go right, -1 = go left
        verticalDirection = 1; // 1 = go down, -1 = go up
        this.movementIndex = movementIndex;
    }


    @Override
    public void update(Garden garden) {
        Statistics.updateGardenerPosition(this);
        if(actionTimer > 0) { // Gardener hasn't finished healing the flower yet
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
        } else { // Gardener has finished healing the flower so he can move
            Audio.play(Audio.getFootstep());

            switch (movementIndex) { // Move
                case 1 -> moveRandomly(garden);
                case 2 -> patternMove(garden.getSize(), 1); // Going the same snake-shaped path
                case 3 -> patternMove(garden.getSize(), 2); // Going the snake-shaped path with a turn (two mirrored paths, "eights")
            }

            if(garden.getFlowers()[positionX][positionY] != null) {
                actionTimer += this.timeToHealTheFlower(garden);
            }
        }
    }


    private void patternMove(int maxIndex, int pattern) {
        if(outOfRange(positionX + horizontalDirection, maxIndex)) { // Out of range X
            horizontalDirection *= -1; // Change direction right - left

            if(outOfRange(positionY + verticalDirection, maxIndex)) { // Out of range Y
                verticalDirection *= -1; // Change direction up - down
                switch(pattern) {
                    case 1 -> positionX += horizontalDirection;
                    case 2 -> positionY += verticalDirection;
                }
            }
            else {
                positionY += verticalDirection;
            }
        } else {
            positionX += horizontalDirection;
        }
    }

    private void moveRandomly(Garden garden) {
        Random random = new Random();
        int displacementX;
        int displacementY;


        do {
            // Displacement varies in the range {-1, 0, 1} on both axes
            displacementX = random.nextInt(3) - 1;
            displacementY = random.nextInt(3) - 1;
        }
        while (outOfRange(positionX + displacementX, garden.getSize()) || outOfRange(positionY + displacementY, garden.getSize()) || (displacementX == 0 && displacementY == 0));

        positionX += displacementX;
        positionY += displacementY;
    }

    private boolean outOfRange(int i, int maxIndex) {
        return i < 0 || i >= maxIndex;
    }
}
