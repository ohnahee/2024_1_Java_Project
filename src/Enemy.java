import java.awt.*;
import javax.swing.*;
import java.util.Random;

class Enemy {
    int x, y;
    int width, height;
    Image image;
    int lives;
    int dx, dy;
    Random random;

    public Enemy(int x, int y, String imagePath, int width, int height, int lives) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lives = lives;
        this.dx = 3; // 속도를 줄임
        this.dy = 3; // 속도를 줄임
        this.random = new Random();
        ImageIcon ii = new ImageIcon(imagePath); // 적 이미지 파일 경로
        image = ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // 이미지 크기 조정
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Bullet shoot() {
        int bulletSpeed = 5 + random.nextInt(6); // 불규칙한 총알 속도
        return new Bullet(x + width / 2 - 2, y + height, bulletSpeed, Color.GREEN); // 적 총알 생성
    }

    public void move() {
        x += dx;
        y += dy;

        if (x <= 0 || x >= 1024 - width) {
            dx = -dx;
        }

        if (y <= 0 || y >= 768 / 2 - height) {
            dy = -dy;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
