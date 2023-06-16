import static java.lang.Math.abs;

public class TargetingGardener extends Gardener {
    private Vector targetFlower;

    public TargetingGardener() {
        super();
        targetFlower = new Vector(positionX, positionY);
    }


    @Override
    public void update(Garden garden) {
        if(actionTimer > 0) {
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
            setTheTargetFlower(garden);
        } else {
            Audio.play(Audio.getFootstep());
            moveToTheTarget(garden);
        }
    }

    private void moveToTheTarget(Garden garden) {
        Vector gardenerVector = new Vector(positionX, positionY);
        Vector directionVector = makeDirectionVector(gardenerVector, targetFlower);
        //System.out.println("Direction Vector: [" + directionVector.x + ", " + directionVector.y + "]");

        gardenerVector.add(directionVector);
        positionX = gardenerVector.x;
        positionY = gardenerVector.y;

        if(positionX == targetFlower.x && positionY == targetFlower.y) {
            if(garden.getFlowers()[positionX][positionY] != null) {
                actionTimer += timeToHealTheFlower(garden);
            }
            else{
                setTheTargetFlower(garden);
            }
        }
    }

    private void setTheTargetFlower(Garden garden) {
        Vector minHpIndex = new Vector(positionOfTheFirstFlower(garden));

        for(int y = minHpIndex.y; y < garden.getSize(); y++) {
            for(int x = 0; x < garden.getSize(); x++) {
                if(garden.getFlowers()[x][y] != null) {
                    if(garden.getFlowers()[x][y].getHp() < garden.getFlowers()[minHpIndex.x][minHpIndex.y].getHp()) {
                        if(damageBeforeGardenerArrival(garden, x, y) < garden.getFlowers()[x][y].getHp()) // Can gardener make it on time
                            minHpIndex = new Vector(x, y);
                    }
                }
            }
        }

        //System.out.println("Target Flower: [" + minHpIndex.x + ", " + minHpIndex.y + "]");
        targetFlower = minHpIndex;
    }

    private Vector positionOfTheFirstFlower(Garden garden) {
        Vector vector = new Vector(-1, -1);
        int y = 0;

        do{
            int x = 0;
            do{
                if(garden.getFlowers()[x][y] != null) {
                    if(damageBeforeGardenerArrival(garden, x, y) < garden.getFlowers()[x][y].getHp()) // Can gardener make it on time
                        vector = new Vector(x, y);
                }
                x++;

            }while(x < garden.getSize() && vector.x == -1);
            y++;

        }while(y < garden.getSize() && vector.x == -1);

        if(vector.x == -1) {
            System.out.println("NO FLOWERS IN THE GARDEN");
            vector.x = positionX;
            vector.y = positionY; // Stay
        }

        //System.out.println("First Flower: [" + vector.x + ", " + vector.y + "]");
        return vector;
    }

    private int damageBeforeGardenerArrival(Garden garden, int x, int y) { // Returns the amount of damage the flower will take until Gardener arrives based on their positions
        return (abs(positionX - x) + abs(positionY - y)) * garden.getFlowers()[x][y].currentDamagePerTick();
    }

    private Vector makeDirectionVector(Vector start, Vector end) {
        int multiplierX;
        int multiplierY;
        Vector sum = new Vector(end.x - start.x, end.y - start.y);

        if(sum.x >= 0)
            multiplierX = 1;
        else
            multiplierX = -1;

        if(sum.y >= 0)
            multiplierY = 1;
        else
            multiplierY = -1;

        sum.x *= multiplierX;
        sum.y *= multiplierY;

        if(sum.x >= sum.y && sum.x != 0)
            return new Vector(multiplierX, 0);
        else if(sum.y > sum.x)
            return new Vector(0, multiplierY);
        else
            return new Vector(0 , 0); //sum = [0, 0]
    }



    private static class Vector {
        private int x;
        private int y;

        public Vector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Vector(Vector vector) {
            this.x = vector.x;
            this.y = vector.y;
        }

        private void add(Vector vector) {
            x = x + vector.x;
            y = y + vector.y;
        }
    }
}
