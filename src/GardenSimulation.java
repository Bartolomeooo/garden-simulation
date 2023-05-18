public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(10, 10);
        garden.initialize(1, 3, 2, 4);

        Gardener gardener = new Gardener();

        for(int i = 0; i < 250; i++) {
            System.out.println(i + 1);
            garden.update();
            garden.print();

            gardener.move(garden.getSizeX(), garden.getSizeY());

            System.out.println();
        }
    }
}
