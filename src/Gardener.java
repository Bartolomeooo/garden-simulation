public class Gardener {
    private int posX;
    private int posY;
    private String horizontalDirection;
    private String verticalDirection;

    public Gardener()
    {
        posX = 0;
        posY = 0;
        horizontalDirection = "Right";
        verticalDirection = "Forward";
    }

    public void move(int maxX, int maxY)
    {
        if(verticalDirection.equals("Forward"))
        {
            if(horizontalDirection.equals("Right")) //going forward right
            {
                if(posY + 1 == maxY) //out of range Y
                {
                    if(posX + 1 == maxX) //out of range X and Y [maxX, maxY]
                    {
                        verticalDirection = "Backwards";
                        posY--;
                    }
                    else
                        posX++;

                    horizontalDirection = "Left";
                }
                else
                    posY++;

            }
            else //going forward left
            {
                if(posY - 1 < 0) //out of range Y
                {
                    if(posX + 1 == maxX) //out of range X and Y [maxX, 0]
                    {
                        verticalDirection = "Backwards";
                        posY++;
                    }
                    else
                        posX++;

                    horizontalDirection = "Right";
                }
                else
                    posY--;
            }
        }
        else
        {
            if(horizontalDirection.equals("Right")) //going backwards right
            {
                if(posY + 1 == maxY) //out of range Y
                {
                    if(posX - 1 < 0) //out of range X and Y [0, maxY]
                    {
                        verticalDirection = "Forward";
                        posY--;
                    }
                    else
                        posX--;

                    horizontalDirection = "Left";
                }
                else
                    posY++;

            }
            else //going backwards left
            {
                if(posY - 1 < 0) //out of range Y
                {
                    if(posX - 1 < 0) //out of range X and Y [0, 0]
                    {
                        verticalDirection = "Forward";
                        posY++;
                    }
                    else
                        posX--;

                    horizontalDirection = "Right";
                }
                else
                    posY--;
            }
        }

        //debug
        System.out.println(verticalDirection + ", " + horizontalDirection + " [" + posX + "," + posY + "]");
    }

    public void print(int maxX, int maxY)
    {
        for(int x = 0; x < maxX; x++)
        {
            for(int y = 0; y < maxY; y++)
            {
                if(x == posX && y == posY)
                    System.out.printf("O");
                else
                    System.out.printf(".");
            }
            System.out.println();
        }
    }
}
