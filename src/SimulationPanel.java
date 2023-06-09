import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimulationPanel extends JPanel {
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    static int UNIT_SIZE;
    private Garden garden;
    private Gardener gardener;
    private boolean showHP;
    private boolean showGrid;

    private BufferedImage backgroundImage;

    SimulationPanel(Garden garden, Gardener gardener) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(7, 133, 4));
        this.setFocusable(true);
        this.garden = garden;
        this.gardener = gardener;
        UNIT_SIZE = SCREEN_WIDTH / garden.getSize();
        showHP = false;
        showGrid = false;
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
        repaint();
    }

    public void draw(Graphics g) {
        //  drawBackground(g);
        drawGardener(g);
        drawFlowers(g);
        if(showGrid)
            drawGrid(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //vertical
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); //horizontal
        }
    }

    private void drawImage(Graphics g, String filename, int x, int y) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));
            Image scaledImage = image.getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, x * UNIT_SIZE, y * UNIT_SIZE, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawFlowers(Graphics g) {
        for(int x = 0; x < garden.getSize(); x++) {
            for(int y = 0; y < garden.getSize(); y++) {
                if(garden.getFlowers()[y][x] != null) {
                    drawWeeds(g, x, y);
                    drawInsects(g, x, y);
                }
                if(garden.getFlowers()[y][x] instanceof RedFlower) {
                    drawImage(g, "icons/red_flower.png", x, y);
                    if(showHP)
                        drawHp(g, x, y);
                }
                else if(garden.getFlowers()[y][x] instanceof YellowFlower) {
                    drawImage(g, "icons/yellow_flower.png", x, y);
                    if(showHP)
                        drawHp(g, x, y);
                }
                else if(garden.getFlowers()[y][x] instanceof BlueFlower) {
                    drawImage(g, "icons/blue_flower.png", x, y);
                    if(showHP)
                        drawHp(g, x, y);
                }
            }
        }
    }

    private void drawWeeds(Graphics g, int x, int y) {
        if(garden.getFlowers()[y][x].getHasWeeds()) {
            drawImage(g, "icons/weeds.png", x, y);
        }
    }

    private void drawInsects(Graphics g, int x, int y) {
        if(garden.getFlowers()[y][x].getHasInsects()) {
            drawImage(g, "icons/insects.png", x, y);
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
        int hp = garden.getFlowers()[y][x].getHp();
        g.setFont(new Font("Helvetica", Font.BOLD, UNIT_SIZE / 5));
        drawOutlinedText(g, " HP: " + hp, x * UNIT_SIZE, y * UNIT_SIZE + g.getFont().getSize(), Color.white, Color.black, 2);
    }

    private void drawGardener(Graphics g) {
        drawImage(g, "icons/gardener.png", gardener.positionY, gardener.positionX);
    }
}
