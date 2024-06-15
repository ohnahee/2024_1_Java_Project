import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShootingGame extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

    public ShootingGame() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MainPanel mainPanel = new MainPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "game");
                cardPanel.getComponent(1).requestFocusInWindow(); // 게임 패널에 포커스 설정
            }
        });
        GamePanel gamePanel = new GamePanel();

        cardPanel.add(mainPanel, "main");
        cardPanel.add(gamePanel, "game");

        add(cardPanel);

        setTitle("Shooting Game");
        setSize(1024, 768); // 크기를 1024x768로 변경
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShootingGame());
    }
}
