import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Mypanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));//getting the title of the game on top rectangle for any image to show on panel we use
                                                                                         //ImageIcon to add that image to our panel
    ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon  up= new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down= new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon left = new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon right = new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon food =  new ImageIcon(getClass().getResource("enemy.png"));

    boolean isUp = false;
    boolean isDown = false;
    boolean isRight = true;
    boolean isLeft = false;

    //for creating food for snake at random positions
    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    Random random = new Random();  //A feature in java which will help to pick a random value
    int foodX=xpos[random.nextInt(xpos.length-1)];
    int foodY=ypos[random.nextInt(ypos.length-1)];
    int[] snakeX = new int[750];
    int[] snakeY = new int[750];
    int move =0;
    int snakelength =3;
    Timer time;
    boolean GameOver = false;
    int score =0;
    Mypanel(){
        addKeyListener(this);    // to make our snake move
        setFocusable(true);
        time = new Timer(150,this);
        time.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,852,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);// this actually do's the task by painting our image at specified location
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        if(move == 0){ //setting the snake position
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }
        if(isRight){
            rightmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isDown){
            down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isLeft){
            left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isUp){
            up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for(int i=1;i<snakelength;i++){                              //printing the snake
            snakeimage.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        food.paintIcon(this,g,foodX,foodY);
        if(GameOver){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,10));
            g.drawString("Press space key to restart",330,360);
        }
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC",Font.PLAIN,15));
        g.drawString("Score: "+score,750,30);
        g.drawString("Length: "+snakelength,750,50);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && GameOver){
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!isLeft)){
            isUp = false;
            isLeft = false;
            isDown = false;
            isRight = true;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!isRight)){
            isUp = false;
            isLeft = true;
            isDown = false;
            isRight = false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!isDown)){
            isUp = true;
            isLeft = false;
            isDown = false;
            isRight = false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!isUp)){
            isUp = false;
            isLeft = false;
            isDown = true;
            isRight = false;
            move++;
        }
    }

    private void restart() {
        score =0;
        GameOver = false;
        move =0;
        snakelength =3;
        isLeft = false;
        isRight = true;
        isUp = false;
        isDown = false;
        time.start();
        newfood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = snakelength-1;i>0;i--){
            snakeX[i] = snakeX[i-1];       //as we are moving what ever we have at i-1 will be moved to i
            snakeY[i] = snakeY[i-1];
        }
        if(isLeft){
            snakeX[0] = snakeX[0]-25;
        }
        if(isRight){
            snakeX[0] = snakeX[0]+25;
        }
        if(isUp){
            snakeY[0] = snakeY[0]-25;
        }
        if(isDown){
            snakeY[0] = snakeY[0]+25;
        }

        if(snakeX[0]>850) snakeX[0] = 25;
        if(snakeX[0]<25) snakeX[0] = 850;
        if(snakeY[0]>625) snakeY[0] = 75;
        if(snakeY[0]<75) snakeY[0] = 625;
        Collisionwithfood();
        Collisionwithbody();
        repaint();
    }

    private void Collisionwithbody() {
        for(int i=snakelength-1;i>0;i--){
            if(snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                time.stop();
                GameOver=true;
            }
        }
    }

    private void Collisionwithfood() {
        if(snakeX[0]==foodX && snakeY[0] == foodY){
            newfood();
            snakelength++;
            score++;
            snakeX[snakelength-1] = snakeX[snakelength-2];//as length of snake increases whatever at snakelength-1 copied to snakelength-2
            snakeY[snakelength-1] = snakeY[snakelength-2];
        }
    }

    private void newfood() {
        foodX = xpos[random.nextInt(xpos.length-1)];
        foodY = ypos[random.nextInt(ypos.length-1)];
        for(int i=snakelength-1;i>=0;i--){
            if(snakeX[i] == foodX && snakeY[i] == foodY){  //food appearing on snake body we are checking that
                newfood();
            }
        }
    }
}
