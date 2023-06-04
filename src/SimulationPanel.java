import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimulationPanel extends JPanel {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE;
    private Garden garden;
    private Gardener gardener;
    private boolean showHP;
    private boolean showGrid;

    private BufferedImage backgroundImage;

    SimulationPanel(Garden garden, Gardener gardener) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // this.setBackground(new Color(0, 204, 0));
        this.setFocusable(true);
        this.garden = garden;
        this.gardener = gardener;
        UNIT_SIZE = SCREEN_WIDTH / garden.getSize();
        showHP = false;
        showGrid = false;

        try {
            backgroundImage = ImageIO.read(new File("garden_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        drawBackground(g);
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
                if(garden.getFlowers()[y][x] instanceof RedFlower) {
                    drawImage(g, "red_flower.png", x, y);
                }
                else if(garden.getFlowers()[y][x] instanceof YellowFlower) {
                    drawImage(g, "yellow_flower.png", x, y);
                }
                else if(garden.getFlowers()[y][x] instanceof BlueFlower) {
                    drawImage(g, "blue_flower.png", x, y);
                }

                if(garden.getFlowers()[y][x] != null) {
                    drawWeeds(g, x, y);
                    drawInsects(g, x, y);
                    if(showHP)
                        drawHp(g, x, y);
                }
            }
        }
    }

    private void drawWeeds(Graphics g, int x, int y) {
        if(garden.getFlowers()[y][x].getHasWeeds()) {
            g.setColor(new Color(153,102,0));
            g.fillRect(x*UNIT_SIZE, y*UNIT_SIZE + 3*UNIT_SIZE/4, UNIT_SIZE, UNIT_SIZE/4);
        }
    }

    private void drawInsects(Graphics g, int x, int y) {
        if(garden.getFlowers()[y][x].getHasInsects()) {
            g.setColor(new Color(51, 51, 51));
            g.fillRect(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE/4, UNIT_SIZE);
        }
    }

    private void drawHp(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.BOLD, UNIT_SIZE/5));
        g.drawString(" HP: " + garden.getFlowers()[y][x].getHp(), x*UNIT_SIZE, y*UNIT_SIZE + g.getFont().getSize());
    }

    private void drawGardener(Graphics g) {
        drawImage(g, "gardener.png", gardener.positionY, gardener.positionX);
    }
}
