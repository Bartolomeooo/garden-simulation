public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(10, 10);
        garden.initializeMap(1, 3, 2, 4);
        garden.printMap();
        for(int i = 0; i < 30; i++) {
            System.out.println(i + 1);
            garden.update();
            garden.printMap();
            System.out.println();
        }
    }
}
