import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame jFrame;
    SnakeGame(){
        jFrame = new JFrame("Snake Game");
        Mypanel panel = new Mypanel();
        jFrame.add(panel);
        panel.setBackground(Color.gray);
        jFrame.setBounds(10,10,905,700);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SnakeGame sg = new SnakeGame();
    }
}
