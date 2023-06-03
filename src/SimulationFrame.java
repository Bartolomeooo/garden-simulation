import javax.swing.*;

public class SimulationFrame extends JFrame {
    SimulationFrame(Garden garden, Gardener gardener, boolean running) {
        JPanel panel = new JPanel();
        // Settings Panel
        SettingsPanel settingsPanel = new SettingsPanel(garden, gardener, running);
        panel.add(settingsPanel);

        // Graphics panel
        panel.add(settingsPanel.getSimulationPanel());
        this.add(panel);

        // Frame settings
        this.setTitle("Garden Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Set the frame in the middle of the screen
    }
}
