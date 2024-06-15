import java.awt.*;

class Bullet {
    int x, y;
    int width, height;
    int speed;
    Color color;

    public Bullet(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = 5;
        this.height = 10;
        this.speed = speed;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void move() {
        y += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

