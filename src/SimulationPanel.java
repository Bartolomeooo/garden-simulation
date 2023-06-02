import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE;
    private Garden garden;
    private Gardener gardener;

    SimulationPanel(Garden garden, Gardener gardener) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0, 204, 0));
        this.setFocusable(true);
        this.garden = garden;
        this.gardener = gardener;
        UNIT_SIZE = SCREEN_WIDTH / garden.getSizeX();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        repaint();
    }

    public void draw(Graphics g) {
        drawFlowers(g);
        drawGardener(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //vertical
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); //horizontal
        }
    }

    private void drawFlowers(Graphics g) {
        for(int x = 0; x < garden.getSizeX(); x++) {
            for(int y = 0; y < garden.getSizeY(); y++) {
                if(garden.getFlowers()[y][x] instanceof RedFlower) {
                    g.setColor(new Color(204, 0 , 0));
                    g.fillOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
                else if(garden.getFlowers()[y][x] instanceof YellowFlower) {
                    g.setColor(new Color(255, 204, 51));
                    g.fillOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
                else if(garden.getFlowers()[y][x] instanceof BlueFlower) {
                    g.setColor(new Color(0, 0, 204));
                    g.fillOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }

                if(garden.getFlowers()[y][x] != null) {
                    drawWeeds(g, x, y);
                    drawInsects(g, x, y);
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
        g.setColor(Color.black);
        g.fillOval(gardener.positionY*UNIT_SIZE + UNIT_SIZE/6, gardener.positionX*UNIT_SIZE + UNIT_SIZE/6, UNIT_SIZE*2/3, UNIT_SIZE*2/3);
    }
}
