import javax.swing.*;

public class SimulationFrame extends JFrame {
    SimulationFrame(Garden garden, Gardener gardener) {
        this.add(new SimulationPanel(garden,gardener));
        this.setTitle("Garden Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Set the frame in the middle of the screen
    }
}
