import java.awt.*;
import javax.swing.*;

class Player {
    int x, y;
    int width, height;
    Image image;
    int lives;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.lives = 3;
        ImageIcon ii = new ImageIcon("icon/player.png"); // player.png 파일 경로
        image = ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // 이미지 크기 조정
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
