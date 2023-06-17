import static java.lang.Math.abs;

public class TargetingGardener extends Gardener {
    private Vector targetFlower;

    public TargetingGardener() {
        super();
        targetFlower = new Vector(positionX, positionY);
    }


    @Override
    public void update(Garden garden) {
        Statistics.updateGardenerPosition(this);
        if(actionTimer > 0) { // Gardener hasn't finished healing the flower yet
            actionTimer--;
            water(garden);
            removeInsects(garden);
            removeWeeds(garden);
            setTheTargetFlower(garden);
        } else { // Gardener has finished healing the flower so he can move
            Audio.play(Audio.getFootstep());
            moveToTheTarget(garden);
        }
    }

    private void moveToTheTarget(Garden garden) {
        Vector gardenerVector = new Vector(positionX, positionY);
        Vector directionVector = makeDirectionVector(gardenerVector, targetFlower);

        gardenerVector.add(directionVector);
        positionX = gardenerVector.x;
        positionY = gardenerVector.y;

        if(positionX == targetFlower.x && positionY == targetFlower.y) { // He is standing on the target flower
            if(garden.getFlowers()[positionX][positionY] != null) {
                actionTimer += timeToHealTheFlower(garden);
            }
            else{
                setTheTargetFlower(garden);
            }
        }
    }

    private void setTheTargetFlower(Garden garden) { // Target - the flower with the least amount of Hp
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

        targetFlower = minHpIndex;
    }

    private Vector positionOfTheFirstFlower(Garden garden) {
        Vector firstFlower = new Vector(-1, -1);

        for(int y = 0; y < garden.getSize() && firstFlower.x == -1; y++) {
            for(int x = 0; x < garden.getSize() && firstFlower.x == -1; x++) {
                if(garden.getFlowers()[x][y] != null) {
                    if(damageBeforeGardenerArrival(garden, x, y) < garden.getFlowers()[x][y].getHp()) // Can gardener make it on time
                        firstFlower = new Vector(x, y);
                }
            }
        }

        if(firstFlower.x == -1) {
            System.out.println("NO FLOWERS IN THE GARDEN");
            firstFlower.x = positionX;
            firstFlower.y = positionY; // Stay
        }

        return firstFlower;
    }

    private int damageBeforeGardenerArrival(Garden garden, int x, int y) { // Returns the amount of damage the flower will take until Gardener arrives based on their positions
        return (abs(positionX - x) + abs(positionY - y)) * garden.getFlowers()[x][y].currentDamagePerTick();  // Distance * current damage per tick
    }

    private Vector makeDirectionVector(Vector start, Vector end) { // [0,1], [1,0], [-1,0] or [0,-1]
        int multiplierX;
        int multiplierY;
        Vector distance = new Vector(end.x - start.x, end.y - start.y);

        if(distance.x >= 0)
            multiplierX = 1;
        else
            multiplierX = -1;

        if(distance.y >= 0)
            multiplierY = 1;
        else
            multiplierY = -1;

        distance.x *= multiplierX;
        distance.y *= multiplierY;

        if(distance.x >= distance.y && distance.x != 0)
            return new Vector(multiplierX, 0);
        else if(distance.y > distance.x)
            return new Vector(0, multiplierY);
        else
            return new Vector(0 , 0); // distance = [0, 0]
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
