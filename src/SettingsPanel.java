import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SettingsPanel extends JPanel implements ActionListener {
    private final SimulationPanel simulationPanel;
    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 750;
    private boolean running;
    private final Timer timer;

    private final LabeledComponent speed;
    private final LabeledComponent gardenSize;

    private final LabeledComponent redRatio;
    private final LabeledComponent yellowRatio;
    private final LabeledComponent blueRatio;
    private final LabeledComponent emptyRatio;

    private final LabeledComponent probabilityOfInsectAppearance;
    private final LabeledComponent probabilityOfWeedsAppearance;
    private final LabeledComponent probabilityOfWeedsSpread;

    private final LabeledComponent gardenerMovement;
    private final String[] movementText;
    private int movementIndex;

    private final LabeledComponent showHP;
    private final LabeledComponent showGrid;

    private final JButton startStopButton;

    private final LabeledComponent soundEffects;


    public SettingsPanel(Garden garden, Gardener gardener) {
        // Panel settings
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(245, 240, 210));
        this.setFocusable(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        simulationPanel = new SimulationPanel(garden, gardener);

        // Garden size and speed settings
        JPanel sizeSpeed = new JPanel();
        sizeSpeed.setLayout(new BoxLayout(sizeSpeed, BoxLayout.X_AXIS));

        gardenSize = new LabeledComponent("Garden size", new JTextField("10"));
        sizeSpeed.add(gardenSize);

        speed = new LabeledComponent("Speed [ms]", new JTextField("500"));
        sizeSpeed.add(speed);

        this.add(sizeSpeed);

        // Flowers settings
        JPanel flowersRatio = new JPanel();
        flowersRatio.setLayout(new BoxLayout(flowersRatio, BoxLayout.X_AXIS));

        redRatio = new LabeledComponent("Red ratio", new JTextField("1"));
        flowersRatio.add(redRatio);
        yellowRatio = new LabeledComponent("Yellow ratio", new JTextField("1"));
        flowersRatio.add(yellowRatio);
        blueRatio = new LabeledComponent("Blue ratio", new JTextField("1"));
        flowersRatio.add(blueRatio);
        emptyRatio = new LabeledComponent("Empty ratio", new JTextField("0"));
        flowersRatio.add(emptyRatio);

        this.add(flowersRatio);

        // Insects and Weeds settings
        JPanel insectsWeeds = new JPanel();
        insectsWeeds.setLayout(new BoxLayout(insectsWeeds, BoxLayout.X_AXIS));

        probabilityOfInsectAppearance = new LabeledComponent("% Insects", new JTextField("5.0"));
        insectsWeeds.add(probabilityOfInsectAppearance);
        probabilityOfWeedsAppearance = new LabeledComponent("% Weeds", new JTextField("5.0"));
        insectsWeeds.add(probabilityOfWeedsAppearance);
        probabilityOfWeedsSpread = new LabeledComponent("% W. Spread", new JTextField("0.5"));
        insectsWeeds.add(probabilityOfWeedsSpread);

        this.add(insectsWeeds);

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

        // Space
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(300,300));
        panel.setBackground(new Color(245, 240, 210));
        this.add(panel);

        // Sound effects setting
        soundEffects = new LabeledComponent("Sound effects", new JCheckBox());
        this.add(soundEffects);

        // Make buttons functional
        ((JButton) gardenerMovement.getComponent()).addActionListener(this);
        startStopButton.addActionListener(this);

        // Simulation settings
        running = false;
        int delay = Integer.parseInt(((JTextField) speed.getComponent()).getText());
        timer = new Timer(delay, this);
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

            // Gardener update
            simulationPanel.getGardener().update(simulationPanel.getGarden());
            //simulationPanel.getGardener().print(simulationPanel.getGarden()); // Debug
        }

        if(e.getSource() == startStopButton) {
            switch(startStopButton.getText()) {
                case "START":
                    try {
                        timerSetup();
                        gardenInit();
                        gardenerInit();
                        simulationInit();
                        graphicsInit();
                        Statistics.clearFile("statistics/garden_simulation.txt");

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


    private void timerSetup() {
        setSpeedInRange();
        int delay = Integer.parseInt(((JTextField) speed.getComponent()).getText());
        timer.setDelay(delay);
    }

    private void gardenInit() {
        // Garden size
        setGardenSizeInRange();
        int size = Integer.parseInt(((JTextField) gardenSize.getComponent()).getText());

        // Flowers ratio
        setRatioInRange(redRatio);
        int redRatioNumber = Integer.parseInt(((JTextField) redRatio.getComponent()).getText());
        setRatioInRange(yellowRatio);
        int yellowRatioNumber = Integer.parseInt(((JTextField) yellowRatio.getComponent()).getText());
        setRatioInRange(blueRatio);
        int blueRatioNumber = Integer.parseInt(((JTextField) blueRatio.getComponent()).getText());
        setRatioInRange(emptyRatio);
        int emptyRatioNumber = Integer.parseInt(((JTextField) emptyRatio.getComponent()).getText());

        // Insects and Weeds
        setProbabilityInRange(probabilityOfInsectAppearance);
        double insects = Double.parseDouble(((JTextField) probabilityOfInsectAppearance.getComponent()).getText());
        setProbabilityInRange(probabilityOfWeedsAppearance);
        double weeds = Double.parseDouble(((JTextField) probabilityOfWeedsAppearance.getComponent()).getText());
        setProbabilityInRange(probabilityOfWeedsSpread);
        double weedsSpread = Double.parseDouble(((JTextField) probabilityOfWeedsSpread.getComponent()).getText());

        // Initialization
        simulationPanel.setGarden(new Garden(size, insects/100.0, weeds/100.0, weedsSpread/100.0));
        simulationPanel.getGarden().initialize(redRatioNumber, yellowRatioNumber, blueRatioNumber, emptyRatioNumber);
        System.out.println("==================================NEW GARDEN====================================");
    }

    private void gardenerInit() {
        switch (movementIndex) {
            case 0 -> // "Target the one with the lowest HP"
                    simulationPanel.setGardener(new TargetingGardener());
            case 1 -> // "Move randomly"
                    simulationPanel.setGardener(new PathFollowingGardener(1));
            case 2 -> // "Pattern 1"
                    simulationPanel.setGardener(new PathFollowingGardener(2));
            case 3 -> // "Pattern 2"
                    simulationPanel.setGardener(new PathFollowingGardener(3));
        }
    }

    private void simulationInit() {
        SimulationPanel.UNIT_SIZE = SimulationPanel.SCREEN_SIZE / simulationPanel.getGarden().getSize();

        simulationPanel.setShowHP(((JCheckBox) showHP.getComponent()).isSelected());
        simulationPanel.setShowGrid(((JCheckBox) showGrid.getComponent()).isSelected());

        Audio.setOn(((JCheckBox) soundEffects.getComponent()).isSelected());
    }

    // Scale and return an image from the given file
    private Image scaleImage(String filename) {
        try {
            Image image = ImageIO.read(new File(filename));
            return image.getScaledInstance(SimulationPanel.UNIT_SIZE, SimulationPanel.UNIT_SIZE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Set all icons
    private void graphicsInit() {
            // Flowers
            RedFlower.setIconImage(scaleImage("icons/red_flower.png"));
            BlueFlower.setIconImage(scaleImage("icons/blue_flower.png"));
            YellowFlower.setIconImage(scaleImage("icons/yellow_flower.png"));

            // Weeds and Insect icons
            Garden.setWeedsIcon(scaleImage("icons/weeds.png"));
            Garden.setInsectIcon(scaleImage("icons/insects.png"));

            // Gardener
            Gardener.setGardenerIcon(scaleImage("icons/gardener.png"));
    }


    private void setSpeedInRange() {
        int delay = Integer.parseInt(((JTextField) speed.getComponent()).getText());
        int maxDelay = 5000; // 5000ms = 5s
        int minDelay = 0; // As fast as possible

        if(delay > maxDelay) {
            ((JTextField) speed.getComponent()).setText(Integer.toString(maxDelay));
        }
        else if(delay < minDelay) {
            ((JTextField) speed.getComponent()).setText(Integer.toString(minDelay));
        }
    }

    private void setGardenSizeInRange() {
        int size = Integer.parseInt(((JTextField) gardenSize.getComponent()).getText());
        int maxSize = 50;
        int minSize = 2;

        if(size > maxSize) {
            ((JTextField) gardenSize.getComponent()).setText(Integer.toString(maxSize));
        }
        else if(size < minSize) {
            ((JTextField) gardenSize.getComponent()).setText(Integer.toString(minSize));
        }
    }

    private void setRatioInRange(LabeledComponent labeledComponent) {
        int ratio = Integer.parseInt(((JTextField) labeledComponent.getComponent()).getText());
        int maxRatio = 999;
        int minRatio = 0;

        if(ratio > maxRatio) {
            ((JTextField) labeledComponent.getComponent()).setText(Integer.toString(maxRatio));
        }
        else if(ratio < minRatio) {
            ((JTextField) labeledComponent.getComponent()).setText(Integer.toString(minRatio));
        }
    }

    private void setProbabilityInRange(LabeledComponent labeledComponent) {
        double probability = Double.parseDouble(((JTextField) labeledComponent.getComponent()).getText());
        double maxProbability = 100;
        double minProbability = 0;

        if(probability > maxProbability) {
            ((JTextField) labeledComponent.getComponent()).setText(Double.toString(maxProbability));
        }
        else if(probability < minProbability) {
            ((JTextField) labeledComponent.getComponent()).setText(Double.toString(minProbability));
        }
    }



    private static class LabeledComponent extends JPanel {
        private final JComponent component;
        private final Dimension componentDimension = new Dimension(60,30);
        private final Dimension panelDimension = new Dimension((int) componentDimension.getWidth() + 20, (int) componentDimension.getHeight() + 40);

        public LabeledComponent(String text, JComponent component) {
            // Panel settings
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setMaximumSize(panelDimension);
            this.setBackground(new Color(245, 240, 210));

            // Label
            JLabel label = new JLabel(text);
            label.setAlignmentX(0.5f);
            this.add(label);

            // Component
            this.component = component;

            if(component instanceof JTextField) {
                Font font = new Font("Arial", Font.PLAIN, 20);
                component.setFont(font);
            }
            else if(component instanceof JCheckBox) {
                ((JCheckBox) component).setHorizontalAlignment(SwingConstants.CENTER);
                component.setBackground(new Color(245, 240, 210));
            }

            // Set component size
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
