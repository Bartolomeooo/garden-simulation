import javax.swing.*;
import java.awt.*;
//import java.util.Random;

public class SettingsPanel extends JPanel {
    private class LabeledComponent extends JPanel{
        private JLabel label;
        private JComponent component;
        private final Font font = new Font("Arial", Font.PLAIN, 20);
        private Dimension componentDimension = new Dimension(60,60);
        private Dimension panelDimension = new Dimension(80, 100);

        public LabeledComponent(String text, JComponent component) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setMaximumSize(panelDimension);
            //Random random = new Random();
            //this.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            this.setBackground(new Color(245, 240, 210));

            label = new JLabel(text);
            label.setAlignmentX(0.5f);
            this.add(label);

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
            this.componentDimension = dimension;
            component.setMaximumSize(componentDimension);
            component.setMinimumSize(componentDimension);

            dimension.setSize(dimension.getWidth() + 20, dimension.getHeight() + 20);
            this.panelDimension = dimension;
            this.setMaximumSize(dimension);
            this.setMinimumSize(dimension);
        }
    }

    static final int SCREEN_WIDTH = 300;
    static final int SCREEN_HEIGHT = 600;
    private Garden garden;
    private Gardener gardener;

    public SettingsPanel(Garden garden, Gardener gardener) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(245, 240, 210));
        this.setFocusable(true);
        this.garden = garden;
        this.gardener = gardener;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        LabeledComponent gardenSize = new LabeledComponent("Set size", new JTextField());
        this.add(gardenSize);

        JPanel flowersRatio = new JPanel();
        flowersRatio.setLayout(new BoxLayout(flowersRatio, BoxLayout.X_AXIS));

        LabeledComponent redRatio = new LabeledComponent("Red ratio", new JTextField());
        flowersRatio.add(redRatio);
        LabeledComponent yellowRatio = new LabeledComponent("Yellow ratio", new JTextField());
        flowersRatio.add(yellowRatio);
        LabeledComponent blueRatio = new LabeledComponent("Blue ratio", new JTextField());
        flowersRatio.add(blueRatio);
        LabeledComponent emptyRatio = new LabeledComponent("Empty ratio", new JTextField());
        flowersRatio.add(emptyRatio);

        this.add(flowersRatio);

        LabeledComponent gardenerMovement = new LabeledComponent("Gardener's movement", new JButton("Target the one with the lowest HP"));
        gardenerMovement.resize(new Dimension(250, 60));
        this.add(gardenerMovement);

        LabeledComponent showHP = new LabeledComponent("Show Flowers' HP", new JCheckBox());
        showHP.resize(new Dimension(250, 30));
        this.add(showHP);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton startButton = new JButton("START");
        buttons.add(startButton);
        JButton stopButton = new JButton("STOP");
        buttons.add(stopButton);

        this.add(buttons);
    }
}
