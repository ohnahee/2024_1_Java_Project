import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    Image backgroundImage;

    public MainPanel(ActionListener startGameListener) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        ImageIcon bg = new ImageIcon("icon/background.png"); // 배경 이미지 경로
        backgroundImage = bg.getImage().getScaledInstance(1024, 768, Image.SCALE_SMOOTH); // 배경 이미지 크기 조정

        JLabel titleLabel = new JLabel("Space Exploration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 60)); // 폰트 크기와 두께 조정
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        gbc.gridy++;
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 30)); // 폰트 크기와 두께 조정
        startButton.setBackground(new Color(255, 215, 0));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());
        startButton.addActionListener(startGameListener);
        add(startButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this); // 배경 이미지 그리기
    }
}
