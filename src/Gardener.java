public abstract class Gardener {
    protected int positionX;
    protected int positionY;
    protected int actionTimer;


    public Gardener() {
        positionX = 0;
        positionY = 0;
        actionTimer = 0;
    }


    protected int water(Garden garden) { // - Returns how much time gardener needs to water the flower
        garden.getFlowers()[positionX][positionY].setHydration(50); //max hydration
        garden.getFlowers()[positionX][positionY].setHp(1000); //max hp

        Audio.play("flower_revival_fx_16bit.wav");

        return 1;
    }

    protected int removeInsects(Garden garden) { // - Returns how much time gardener needs to remove insects from the flower
        if(garden.getFlowers()[positionX][positionY].getHasInsects()) {
            garden.getFlowers()[positionX][positionY].setHasInsects(false);
            return 2;
        }

        return 0;
    }

    protected int removeWeeds(Garden garden) { // - Returns how much time gardener needs to remove weeds from the flower
        if(garden.getFlowers()[positionX][positionY].getHasWeeds()) {
            garden.getFlowers()[positionX][positionY].setHasWeeds(false);
            return 3;
        }

        return 0;
    }

    protected int timeToHealTheFlower(Garden garden) { // How much time (ticks) gardener needs to heal the flower
        return water(garden) + removeInsects(garden) + removeWeeds(garden);
    }

    public void update(Garden garden) {}


    public void printInsectsAndWeeds(Garden garden) {
        for (int x = 0; x < garden.getSize(); x++) {
            for (int y = 0; y < garden.getSize(); y++) {
                if (x == positionX && y == positionY)
                    System.out.printf("%7s|", "▨");
                else {
                    if(garden.getFlowers()[x][y] == null)
                        System.out.printf("%7s|", "X");
                    else {
                        if(garden.getFlowers()[x][y].getHasInsects() && garden.getFlowers()[x][y].getHasWeeds())
                            System.out.printf("%2s%5d|", "■", garden.getFlowers()[x][y].getHp());
                        else if(garden.getFlowers()[x][y].getHasInsects())
                            System.out.printf("%2s%5d|", "◧", garden.getFlowers()[x][y].getHp());
                        else if(garden.getFlowers()[x][y].getHasWeeds())
                            System.out.printf("%2s%5d|", "◨", garden.getFlowers()[x][y].getHp());
                        else
                            System.out.printf("%2s%5d|", "□", garden.getFlowers()[x][y].getHp());
                    }
                }
            }
            System.out.println();
        }
        System.out.println("================================================================================");
    }
}
