public class GardenSimulation {
    public static void main(String[] args) {
        Garden garden = new Garden(2,0,0,0);
        garden.initialize(0, 0, 0, 0);

        Gardener gardener = new TargetingGardener();

        new SimulationFrame(garden, gardener);
    }
}
