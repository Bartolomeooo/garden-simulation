public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(5, 5);
        garden.initialize(1, 3, 2, 4);

        Gardener gardener = new Gardener();

        for(int i = 0; i < 250; i++) {
            System.out.println(i + 1);
            // garden.update();
            // garden.print();

            // gardener.move(garden.getSizeX(), garden.getSizeY());
            gardener.moveRandomly(garden);
            gardener.print(garden.getSizeX(), garden.getSizeY());
            sleep(500);

            System.out.println();
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
