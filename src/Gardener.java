public class Gardener {
    private int positionX;
    private int positionY;
    private String horizontalDirection;
    private String verticalDirection;

    public Gardener() {
        positionX = 0;
        positionY = 0;
        horizontalDirection = "Right";
        verticalDirection = "Forward";
    }

    public void move(int maxX, int maxY) {
        if(verticalDirection.equals("Forward")) {
            if(horizontalDirection.equals("Right")) { // Going forward right
                if(positionY + 1 == maxY) { // Out of range Y
                    if(positionX + 1 == maxX) { // Out of range X and Y [maxX, maxY]
                        verticalDirection = "Backwards";
                        positionY--;
                    }
                    else
                        positionX++;

                    horizontalDirection = "Left";
                }
                else
                    positionY++;
            }
            else { // Going forward left
                if(positionY - 1 < 0) { // Out of range Y
                    if(positionX + 1 == maxX) { // Out of range X and Y [maxX, 0]
                        verticalDirection = "Backwards";
                        positionY++;
                    }
                    else
                        positionX++;

                    horizontalDirection = "Right";
                }
                else
                    positionY--;
            }
        }
        else {
            if(horizontalDirection.equals("Right")) { // Going backwards right
                if(positionY + 1 == maxY) { // Out of range Y
                    if(positionX - 1 < 0) { // Out of range X and Y [0, maxY]
                        verticalDirection = "Forward";
                        positionY--;
                    }
                    else
                        positionX--;

                    horizontalDirection = "Left";
                }
                else
                    positionY++;
            }
            else { // Going backwards left
                if(positionY - 1 < 0) { // Out of range Y
                    if(positionX - 1 < 0) { // Out of range X and Y [0, 0]
                        verticalDirection = "Forward";
                        positionY++;
                    }
                    else
                        positionX--;

                    horizontalDirection = "Right";
                }
                else
                    positionY--;
            }
        }

        // Debug
        System.out.println(verticalDirection + ", " + horizontalDirection + " [" + positionX + "," + positionY + "]");
    }

    public void print(int maxX, int maxY) {
        for(int x = 0; x < maxX; x++) {
            for(int y = 0; y < maxY; y++) {
                if(x == positionX && y == positionY)
                    System.out.print("O ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
