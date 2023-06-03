public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(10);
        garden.initialize(0, 0, 0, 0);

        Gardener gardener = new TargetingGardener();
        gardener.printInsectsAndWeeds(garden);
        System.out.println();

        new SimulationFrame(garden, gardener, false);
    }
}
