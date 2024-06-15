import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> playerBullets;
    ArrayList<Bullet> enemyBullets;
    Timer timer;
    Image backgroundImage;
    Image backgroundImage2;
    boolean showLevelText;
    boolean gameOver;
    boolean gameClear;
    boolean finalLevel;
    boolean gameFinished;
    long levelStartTime;
    long lastHitTime;
    long clearStartTime;
    int level;
    JLabel healthLabel;
    JButton retryButton;

    public GamePanel() {
        setLayout(new BorderLayout());

        player = new Player(487, 650); // 플레이어 중앙 위치 조정
        enemies = new ArrayList<>();
        level = 1;
        setupLevel(level);
        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        timer = new Timer(10, this);
        timer.start();

        ImageIcon bg = new ImageIcon("icon/background.png"); // 배경 이미지 경로
        backgroundImage = bg.getImage().getScaledInstance(1024, 768, Image.SCALE_SMOOTH); // 배경 이미지 크기 조정

        ImageIcon bg2 = new ImageIcon("icon/background2.png"); // 배경2 이미지 경로
        backgroundImage2 = bg2.getImage().getScaledInstance(1024, 768, Image.SCALE_SMOOTH); // 배경2 이미지 크기 조정

        showLevelText = true;
        gameOver = false;
        gameClear = false;
        finalLevel = false;
        gameFinished = false;
        levelStartTime = System.currentTimeMillis();
        lastHitTime = 0;

        healthLabel = new JLabel("Lives: " + player.lives);
        healthLabel.setFont(new Font("Serif", Font.BOLD, 20));
        healthLabel.setForeground(Color.WHITE);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.add(healthLabel);

        retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Serif", Font.BOLD, 20));
        retryButton.setBackground(new Color(255, 215, 0));
        retryButton.setForeground(Color.BLACK);
        retryButton.setFocusPainted(false);
        retryButton.setVisible(false);
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        topPanel.add(retryButton);

        add(topPanel, BorderLayout.NORTH);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    player.move(-15, 0); // 이동 속도를 더 빠르게
                }
                if (key == KeyEvent.VK_RIGHT) {
                    player.move(15, 0); // 이동 속도를 더 빠르게
                }
                if (key == KeyEvent.VK_UP) {
                    player.move(0, -15); // 위로 이동
                }
                if (key == KeyEvent.VK_DOWN) {
                    player.move(0, 15); // 아래로 이동
                }
                if (key == KeyEvent.VK_SPACE) {
                    playerBullets.add(new Bullet(player.x + player.width / 2 - 2, player.y, -10, Color.RED));
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    customizeOptionPane();
                    int response = JOptionPane.showConfirmDialog(null, "게임을 종료하시겠습니까?", "종료 확인",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        ((CardLayout) getParent().getLayout()).show(getParent(), "main");
                    }
                }
            }
        });
    }

    private void setupLevel(int level) {
        enemies.clear();
        if (level == 1) {
            enemies.add(new Enemy(100, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(487, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(874, 50, "icon/enemy.png", 50, 50, 3));
        } else if (level == 2) {
            enemies.add(new Enemy(100, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(300, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(500, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(700, 50, "icon/enemy.png", 50, 50, 3));
            enemies.add(new Enemy(900, 50, "icon/enemy.png", 50, 50, 3));
        } else if (level == 3) { // Final 레벨
            enemies.add(new Enemy(412, 50, "icon/enemy2.png", 200, 200, 20)); // 큰 적 하나 추가
            enemies.add(new Enemy(100, 50, "icon/enemy.png", 50, 50, 3)); // 작은 적 추가
            enemies.add(new Enemy(487, 50, "icon/enemy.png", 50, 50, 3)); // 작은 적 추가
            enemies.add(new Enemy(874, 50, "icon/enemy.png", 50, 50, 3)); // 작은 적 추가
        }
    }

    private void customizeOptionPane() {
        UIManager.put("OptionPane.background", new Color(0, 0, 0));
        UIManager.put("Panel.background", new Color(0, 0, 0));
        UIManager.put("OptionPane.messageForeground", new Color(255, 255, 255));
        UIManager.put("Button.background", new Color(255, 215, 0));
        UIManager.put("Button.foreground", new Color(0, 0, 0));
    }

    private void resetGame() {
        level = 1;
        player = new Player(487, 650);
        setupLevel(level);
        playerBullets.clear();
        enemyBullets.clear();
        gameOver = false;
        gameClear = false;
        gameFinished = false;
        showLevelText = true;
        finalLevel = false;  // finalLevel 플래그를 초기화
        levelStartTime = System.currentTimeMillis();
        retryButton.setVisible(false);
        healthLabel.setText("Lives: " + player.lives);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameFinished) {
            g.drawImage(backgroundImage2, 0, 0, this); // 배경2 이미지 그리기
        } else {
            g.drawImage(backgroundImage, 0, 0, this); // 배경 이미지 그리기
        }

        if (!gameFinished) {
            player.draw(g);

            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }

            for (Bullet bullet : playerBullets) {
                bullet.draw(g);
            }

            for (Bullet bullet : enemyBullets) {
                bullet.draw(g);
            }
        }

        if (showLevelText) {
            g.setFont(new Font("Serif", Font.BOLD, 48));
            g.setColor(Color.WHITE);
            if (finalLevel) {
                g.drawString("Final", getWidth() / 2 - 50, getHeight() / 2);
            } else {
                g.drawString("Level " + level, getWidth() / 2 - 100, getHeight() / 2);
            }
        }

        if (gameOver) {
            g.setFont(new Font("Serif", Font.BOLD, 48));
            g.setColor(Color.RED);
            g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
            retryButton.setVisible(true);
        }

        if (gameClear) {
            g.setFont(new Font("Serif", Font.BOLD, 48));
            g.setColor(Color.GREEN);
            g.drawString("Clear", getWidth() / 2 - 50, getHeight() / 2);
        }

        if (gameFinished) {
            g.setFont(new Font("Serif", Font.BOLD, 30)); // 글씨 크기를 줄임
            g.setColor(Color.WHITE);
            g.drawString("You have successfully continued your peaceful space exploration.", getWidth() / 2 - 400, getHeight() / 2);
            retryButton.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        if (showLevelText && currentTime - levelStartTime > 2000) {
            showLevelText = false;
        }

        if (gameOver || gameClear || gameFinished) {
            if (gameClear) {
                if (currentTime - clearStartTime > 2000) {
                    if (level == 2) {
                        finalLevel = true;
                    }
                    level++;
                    if (level > 3) {
                        gameFinished = true;
                    } else {
                        setupLevel(level);
                        showLevelText = true;
                        levelStartTime = System.currentTimeMillis();
                    }
                    gameClear = false;
                }
            }
            repaint();
            return;
        }

        Iterator<Bullet> it = playerBullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.move();
            if (bullet.y < 0) {
                it.remove();
            } else {
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.lives--;
                        it.remove();
                        if (enemy.lives <= 0) {
                            enemies.remove(enemy);
                            break;
                        }
                    }
                }
            }
        }

        it = enemyBullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.move();
            if (bullet.y > getHeight()) {
                it.remove();
            } else if (bullet.getBounds().intersects(player.getBounds())) {
                player.lives--;
                healthLabel.setText("Lives: " + player.lives);
                lastHitTime = currentTime;
                it.remove();
                if (player.lives <= 0) {
                    gameOver = true;
                }
            }
        }

        for (Enemy enemy : enemies) {
            enemy.move();
        }

        if (enemies.isEmpty()) {
            gameClear = true;
            clearStartTime = currentTime;
        }

        Random rand = new Random();
        if (!showLevelText && rand.nextInt(100) < (level == 3 ? 5 : (level == 2 ? 2 : 1))) { // 적의 총알 발사 확률을 낮춤
            for (Enemy enemy : enemies) {
                enemyBullets.add(enemy.shoot());
            }
        }

        if (currentTime - lastHitTime < 1000) {
            setLocation(rand.nextInt(10) - 5, rand.nextInt(10) - 5);
        } else {
            setLocation(0, 0);
        }

        repaint();
    }
}
