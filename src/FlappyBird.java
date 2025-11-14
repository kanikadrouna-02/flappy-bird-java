import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth=360;
    int boardHeight=640;

    double highScore=0;

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird
    int birdX= boardWidth/8;
    int birdY= boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;



    class Bird {
        int x= birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img= img;
        }
    }


    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y= pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img=img;
        }
    }


    //game logic
    Bird bird;
    double velocityX = -4; //moves pipes to the right(simulates bird moving right)
    double velocityY = 0; //move bird up/down speed
    double gravity = 0.65;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver= false;
    double score = 0;



    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        //setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        //load image
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg =new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg =new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
    
        //bird
        bird = new Bird(birdImg);
        pipes=new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1300, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });

        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }





    public void placePipes(){
        //(0-1)* pipeHeight/2 -> (0-256)
        //pipeHeight = 128 
        //0-128- (0-256)  (1/4 to 3/4)
        int randomPipeY =(int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y= randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }




    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }



    public void draw(Graphics g){
        // System.out.println("draw");

        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    
        //bird
        g.drawImage(bird.img, bird.x , bird.y, bird.width, bird.height ,null);
    
        //pipes
        for(int i=0;i<pipes.size(); i++){
            Pipe pipe= pipes.get(i);
            g.drawImage(pipe.img, pipe.x , pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN , 32));
        if (gameOver) {
            g.drawString("Game Over! Score: " + (int) score, 10, 35);
            g.drawString("High Score: " + (int) highScore, 10, 70);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to restart", 10, 100);
        } else {
            g.drawString("Score: " + (int) score, 10, 35);
            g.drawString("High Score: " + (int) highScore, 10, 70);
        }
    }




    //move function
    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y=Math.max(bird.y,0);

        //pipes
        for(int i=0; i<pipes.size();i++){
            Pipe pipe= pipes.get(i);
            pipe.x+= velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; //0.5 because there are 2 pipes!! so you get 1 score for each pair of pipes passed 
            }

            if(collision(bird,pipe)){
                gameOver=true;
            }
        }

        if(bird.y > boardHeight){
            gameOver = true;
        }
    }




    public boolean collision( Bird a, Pipe b){
        return a.x < b.x+b.width &&   //a's top left corner doesnt reach b's top right corner
            a.x + a.width> b.x &&   // a's top right corner doesnt reach b's top left corner
            a.y<b.y+b.height &&    //a's top left corner doesnt reach b's bottom left corner
            a.y+a.height > b.y;  // a's bottom left corner passes b's top left corner
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();

            if(score > highScore){
                highScore = score;
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_SPACE){
            velocityY=-10;
            if(gameOver){
                //restart the game by resetting the conditions
                bird.y=birdY;
                velocityY=0;
                pipes.clear();
                score=0;
                gameOver=false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

        @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}
}
