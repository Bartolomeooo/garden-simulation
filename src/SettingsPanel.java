import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.Random;

public class SettingsPanel extends JPanel implements ActionListener {
    private final SimulationPanel simulationPanel;
    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 600;
    private boolean running;
    static final int DELAY = 500;
    private final LabeledComponent gardenSize;
    private final LabeledComponent redRatio;
    private final LabeledComponent yellowRatio;
    private final LabeledComponent blueRatio;
    private final LabeledComponent emptyRatio;
    private final LabeledComponent gardenerMovement;
    private final LabeledComponent showHP;
    private final LabeledComponent showGrid;
    private final JButton startStopButton;
    private final String[] movementText;
    private int movementIndex;
    Timer timer;


    public SettingsPanel(Garden garden, Gardener gardener, boolean running) {
        // Panel settings
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(245, 240, 210));
        this.setFocusable(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        simulationPanel = new SimulationPanel(garden, gardener);

        // Garden size setting
        gardenSize = new LabeledComponent("Set size", new JTextField());
        this.add(gardenSize);

        // Flowers settings
        JPanel flowersRatio = new JPanel();
        flowersRatio.setLayout(new BoxLayout(flowersRatio, BoxLayout.X_AXIS));

        redRatio = new LabeledComponent("Red ratio", new JTextField());
        flowersRatio.add(redRatio);
        yellowRatio = new LabeledComponent("Yellow ratio", new JTextField());
        flowersRatio.add(yellowRatio);
        blueRatio = new LabeledComponent("Blue ratio", new JTextField());
        flowersRatio.add(blueRatio);
        emptyRatio = new LabeledComponent("Empty ratio", new JTextField());
        flowersRatio.add(emptyRatio);

        this.add(flowersRatio);

        // Gardener movement setting
        movementText = new String[4];
        movementText[0] = "Target the one with the lowest HP";
        movementText[1] = "Move randomly";
        movementText[2] = "Pattern 1";
        movementText[3] = "Pattern 2";

        movementIndex = 0;
        gardenerMovement = new LabeledComponent("Gardener's movement", new JButton(movementText[movementIndex]));
        gardenerMovement.resize(250);
        this.add(gardenerMovement);

        // Visible HP and grid settings
        JPanel hpGridSettings = new JPanel();
        hpGridSettings.setLayout(new BoxLayout(hpGridSettings, BoxLayout.X_AXIS));

        showHP = new LabeledComponent("Show Flowers' HP", new JCheckBox());
        showHP.resize(150);
        hpGridSettings.add(showHP);

        showGrid = new LabeledComponent("Show grid", new JCheckBox());
        showGrid.resize(150);
        hpGridSettings.add(showGrid);

        this.add(hpGridSettings);

        // Start and Stop button
        startStopButton = new JButton("START");
        this.add(startStopButton);
        startStopButton.setAlignmentX(0.5f);

        // Make buttons functional
        ((JButton) gardenerMovement.getComponent()).addActionListener(this);
        startStopButton.addActionListener(this);

        // Simulation settings
        this.running = running;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public SimulationPanel getSimulationPanel() {
        return simulationPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            // Garden update
            simulationPanel.getGarden().update();

            simulationPanel.getGarden().insertInsect(0.3);
            simulationPanel.getGarden().insertWeeds(0.3);
            simulationPanel.getGarden().spreadWeeds(0.05);

            // Gardener update
            simulationPanel.getGardener().update(simulationPanel.getGarden());
            simulationPanel.getGardener().printInsectsAndWeeds(simulationPanel.getGarden()); //debug
        }

        if(e.getSource() == startStopButton) {
            switch(startStopButton.getText()) {
                case "START":
                    try {
                        // Garden init
                        int size = Integer.parseInt(((JTextField) gardenSize.getComponent()).getText());

                        int redRatioNumber = Integer.parseInt(((JTextField) redRatio.getComponent()).getText());
                        int yellowRatioNumber = Integer.parseInt(((JTextField) yellowRatio.getComponent()).getText());
                        int blueRatioNumber = Integer.parseInt(((JTextField) blueRatio.getComponent()).getText());
                        int emptyRatioNumber = Integer.parseInt(((JTextField) emptyRatio.getComponent()).getText());

                        simulationPanel.setGarden(new Garden(size));
                        simulationPanel.getGarden().initialize(redRatioNumber, yellowRatioNumber, blueRatioNumber, emptyRatioNumber);
                        simulationPanel.getGarden().print(); // Debug

                        // Gardener init
                        switch(movementIndex) {
                            case 0: // "Target the one with the lowest HP"
                                simulationPanel.setGardener(new TargetingGardener());
                                break;
                            case 1: // "Move randomly"
                                simulationPanel.setGardener(new PathFollowingGardener(1));
                                break;
                            case 2: // "Pattern 1"
                                simulationPanel.setGardener(new PathFollowingGardener(2));
                                break;
                            case 3: // "Pattern 2"
                                simulationPanel.setGardener(new PathFollowingGardener(3));
                                break;
                        }

                        // Simulation init
                        SimulationPanel.UNIT_SIZE = SimulationPanel.SCREEN_WIDTH / simulationPanel.getGarden().getSize();

                        if(((JCheckBox) showHP.getComponent()).isSelected()) {
                            simulationPanel.setShowHP(true);
                        }
                        else {
                            simulationPanel.setShowHP(false);
                        }

                        if(((JCheckBox) showGrid.getComponent()).isSelected()) {
                            simulationPanel.setShowGrid(true);
                        }
                        else {
                            simulationPanel.setShowGrid(false);
                        }

                        running = true;
                        startStopButton.setText("STOP");
                    } catch (Exception ex) {
                        System.out.println("At least one TextField doesn't have a value");
                    }
                    break;
                case "STOP":
                    running = false;
                    startStopButton.setText("START");
                    break;
            }
        }

        if (e.getSource() == gardenerMovement.getComponent()) {
            movementIndex = (movementIndex + 1) % 4;
            ((JButton) gardenerMovement.getComponent()).setText(movementText[movementIndex]);
        }

        simulationPanel.repaint();
    }



    private class LabeledComponent extends JPanel {
        private final JLabel label;
        private final JComponent component;
        private final Font font = new Font("Arial", Font.PLAIN, 20);
        private Dimension componentDimension = new Dimension(60,30);
        private Dimension panelDimension = new Dimension((int) componentDimension.getWidth() + 20, (int) componentDimension.getHeight() + 40);

        public LabeledComponent(String text, JComponent component) {
            // Panel settings
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setMaximumSize(panelDimension);
            /*Random random = new Random();
            this.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));*/
            this.setBackground(new Color(245, 240, 210));

            // Label
            label = new JLabel(text);
            label.setAlignmentX(0.5f);
            this.add(label);

            // Component
            this.component = component;

            if(component instanceof JTextField) {
                component.setFont(font);
            }
            else if(component instanceof JCheckBox) {
                ((JCheckBox) component).setHorizontalAlignment(SwingConstants.CENTER);
                component.setBackground(new Color(245, 240, 210));
            }

            component.setMaximumSize(componentDimension);
            component.setMinimumSize(componentDimension);
            component.setAlignmentX(0.5f);
            this.add(component);

            // Add space between panels
            this.add(Box.createHorizontalStrut(0));
        }

        public void resize(int width) {
            // Resize component
            componentDimension.setSize(width, componentDimension.getHeight());
            component.setMaximumSize(componentDimension);
            component.setMinimumSize(componentDimension);

            // Resize panel
            panelDimension.setSize(width + 20, panelDimension.getHeight());
            this.setMaximumSize(panelDimension);
            this.setMinimumSize(panelDimension);
        }

        public JComponent getComponent() {
            return component;
        }
    }
}
