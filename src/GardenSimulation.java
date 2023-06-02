public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(5, 5);
        garden.initialize(1, 3, 2, 4);

        //Gardener gardener = new PathFollowingGardener();
        Gardener gardener = new TargetingGardener();
        gardener.printInsectsAndWeeds(garden);
        System.out.println();

        new SimulationFrame(garden, gardener);

        for(int i = 0; i < 500; i++) {
            System.out.println(i + 1);

            garden.update();

            garden.insertInsect(0.3);
            garden.insertWeeds(0.3);
            garden.spreadWeeds(0.05);
            //garden.printInsects();

            gardener.update(garden);
            gardener.printInsectsAndWeeds(garden);
            //gardener.printHp(garden);
            //garden.print();
            //gardener.retractingSnakeMove(garden.getSizeX(), garden.getSizeY());
            //gardener.turningSnakeMove(garden.getSizeX(), garden.getSizeY());
            //gardener.moveRandomly(garden);
            //gardener.print(garden.getSizeX(), garden.getSizeY());
            sleep(700);
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
