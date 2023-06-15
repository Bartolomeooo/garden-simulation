import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    static final int SCREEN_SIZE = 750;
    static int UNIT_SIZE;
    private Garden garden;
    private Gardener gardener;
    private boolean showHP;
    private boolean showGrid;


    SimulationPanel(Garden garden, Gardener gardener) {
        this.setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        this.setBackground(new Color(7, 133, 4));
        this.setFocusable(true);
        this.garden = garden;
        this.gardener = gardener;
        UNIT_SIZE = SCREEN_SIZE / garden.getSize();
        showHP = false;
        showGrid = false;

        // Music
        Audio.open();
        Audio.playBackground();
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public Gardener getGardener() {
        return gardener;
    }

    public void setGardener(Gardener gardener) {
        this.gardener = gardener;
    }

    public void setShowHP(boolean showHP) {
        this.showHP = showHP;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        drawGardener(g);
        drawFlowers(g);
        if(showGrid)
            drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < SCREEN_SIZE / UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_SIZE); //vertical
            g.drawLine(0, i*UNIT_SIZE, SCREEN_SIZE, i*UNIT_SIZE); //horizontal
        }
    }

    private void drawFlowers(Graphics g) {
        for(int y = 0; y < garden.getSize(); y++) {
            for(int x = 0; x < garden.getSize(); x++) {
                if(garden.getFlowers()[x][y] != null) {
                    drawWeeds(g, x, y);
                    drawInsects(g, x, y);
                }
                if(garden.getFlowers()[x][y] instanceof RedFlower) {
                    g.drawImage(RedFlower.getIconImage(), x * UNIT_SIZE, y * UNIT_SIZE, this);
                    if(showHP)
                        drawHp(g, x, y);
                }
                else if(garden.getFlowers()[x][y] instanceof YellowFlower) {
                    g.drawImage(YellowFlower.getIconImage(), x * UNIT_SIZE, y * UNIT_SIZE, this);
                    if(showHP)
                        drawHp(g, x, y);
                }
                else if(garden.getFlowers()[x][y] instanceof BlueFlower) {
                    g.drawImage(BlueFlower.getIconImage(), x * UNIT_SIZE, y * UNIT_SIZE, this);
                    if(showHP)
                        drawHp(g, x, y);
                }
            }
        }
    }

    private void drawWeeds(Graphics g, int x, int y) {
        if(garden.getFlowers()[x][y].getHasWeeds()) {
            g.drawImage(Garden.getWeedsIcon(), x * UNIT_SIZE, y * UNIT_SIZE, this);
        }
    }

    private void drawInsects(Graphics g, int x, int y) {
        if(garden.getFlowers()[x][y].getHasInsects()) {
            g.drawImage(Garden.getInsectIcon(), x * UNIT_SIZE, y * UNIT_SIZE, this);
        }
    }

    private void drawOutlinedText(Graphics g, String text, int x, int y, Color textColor, Color outlineColor, int outlineThickness) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Helvetica", Font.BOLD, UNIT_SIZE / 5);

        g2d.setFont(font);
        g2d.setColor(outlineColor);
        g2d.drawString(text, x - outlineThickness, y);
        g2d.drawString(text, x + outlineThickness, y);
        g2d.drawString(text, x, y - outlineThickness);
        g2d.drawString(text, x, y + outlineThickness);

        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }

    private void drawHp(Graphics g, int x, int y) {
        int hp = garden.getFlowers()[x][y].getHp();
        g.setFont(new Font("Helvetica", Font.BOLD, UNIT_SIZE / 5));
        drawOutlinedText(g, " HP: " + hp, x * UNIT_SIZE, y * UNIT_SIZE + g.getFont().getSize(), Color.white, Color.black, 2);
    }

    private void drawGardener(Graphics g) {
        g.drawImage(Gardener.getGardenerIcon(), gardener.positionX * UNIT_SIZE, gardener.positionY * UNIT_SIZE, this);
    }
}
