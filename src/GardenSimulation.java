public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(10, 10);
        garden.initialize(1, 3, 2, 4);

        for(int i = 0; i < 30; i++) {
            System.out.println(i + 1);
            garden.update();
            garden.print();
            System.out.println();
        }
    }
}
