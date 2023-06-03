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
    private final JButton startButton;
    private final JButton stopButton;
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
        gardenerMovement = new LabeledComponent("Gardener's movement", new JButton("Target the one with the lowest HP"));
        gardenerMovement.resize(new Dimension(250, 60));
        this.add(gardenerMovement);

        // Visible HP setting
        showHP = new LabeledComponent("Show Flowers' HP", new JCheckBox());
        showHP.resize(new Dimension(250, 30));
        this.add(showHP);

        // Start and Stop buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        startButton = new JButton("START");
        buttons.add(startButton);
        stopButton = new JButton("STOP");
        buttons.add(stopButton);

        this.add(buttons);

        // Make buttons functional
        ((JButton) gardenerMovement.getComponent()).addActionListener(this);
        startButton.addActionListener(this);
        stopButton.addActionListener(this);

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
        else if(e.getSource() == startButton) {
            // Garden init
            int size = Integer.parseInt(((JTextField) gardenSize.getComponent()).getText());
            simulationPanel.setGarden(new Garden(size, size));

            int redRatioNumber = Integer.parseInt(((JTextField) redRatio.getComponent()).getText());
            int yellowRatioNumber = Integer.parseInt(((JTextField) yellowRatio.getComponent()).getText());
            int blueRatioNumber = Integer.parseInt(((JTextField) blueRatio.getComponent()).getText());
            int emptyRatioNumber = Integer.parseInt(((JTextField) emptyRatio.getComponent()).getText());

            simulationPanel.getGarden().initialize(redRatioNumber, yellowRatioNumber, blueRatioNumber, emptyRatioNumber);
            simulationPanel.getGarden().print();

            // Gardener init TODO: User chooses the movement
            simulationPanel.setGardener(new TargetingGardener());

            // Simulation init
            SimulationPanel.UNIT_SIZE = SimulationPanel.SCREEN_WIDTH / simulationPanel.getGarden().getSizeX();
            running = true;
            System.out.println("aaaa");
        }

        if (e.getSource() == stopButton) {
            System.out.println("bbbb");
            running = false;
        }
        /*else if (e.getSource() == gardenerMovement.getComponent()) {  TODO: Make this button functional + Make "Show HP" functional
            ((JButton) gardenerMovement.getComponent()).setText();
        }*/

        simulationPanel.repaint();
    }



    private class LabeledComponent extends JPanel {
        private final JLabel label;
        private final JComponent component;
        private final Font font = new Font("Arial", Font.PLAIN, 20);
        private Dimension componentDimension = new Dimension(60,60);
        private Dimension panelDimension = new Dimension(80, 100);

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
        }

        public void resize(Dimension dimension) {
            // Resize component
            this.componentDimension = dimension;
            component.setMaximumSize(componentDimension);
            component.setMinimumSize(componentDimension);

            // Resize panel
            dimension.setSize(dimension.getWidth() + 20, dimension.getHeight() + 20);
            this.panelDimension = dimension;
            this.setMaximumSize(dimension);
            this.setMinimumSize(dimension);
        }

        public JComponent getComponent() {
            return component;
        }
    }
}
