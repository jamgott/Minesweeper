import java.awt.Color;
import java.awt.Graphics;

public class Cell extends GameObj {

    public static final int SIZE = 35;
    private boolean mine;
    private int adj;
    private Color color;
    private boolean flipped;
    private boolean flagged;

    public Cell(int x, int y, int courtWidth, int courtHeight, Color color, 
            boolean mine, int adj) {
        super(x, y, SIZE, SIZE, courtWidth, courtHeight);
        this.mine = mine;
        this.adj = adj;
        this.color = color;
        this.flipped = false;
        this.flagged = false;
    }

    public boolean isMine() {
        return mine;
    }
    
    public void placeMine() {
        this.mine = true;
    }

    public int numAdj() {
        return adj;
    }
    
    public void addAdj() {
        this.adj++;
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color c) {
        this.color = c;
    }
    public boolean isFlipped() {
        return flipped;
    }
    public void flip() {
        this.flipped = true;
    }
    public boolean isFlagged() {
        return flagged;
    }
    public void flag() {
        this.flagged = !this.flagged;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        if (this.mine && this.flipped) {
            g.fillOval(this.getPx() + 13, this.getPy() + 13, 11, 11);
        }
        if (this.adj > 0 && !this.mine && this.flipped) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(this.adj), this.getPx() + 17, this.getPy() + 17);
        } else if (this.flagged) {
            g.drawLine(this.getPx() + 10, this.getPy() + 10, this.getPx() + 10, this.getPy() + 30);
            g.setColor(Color.RED);
            g.fillRect(this.getPx() + 10, this.getPy() + 10, 11, 9);
        }
    }
}