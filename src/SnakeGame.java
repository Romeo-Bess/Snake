import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{ //KeyListener for the key input
    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){ //constructor for game
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //If you want a Grid
     /*    for(int i = 0;i < boardWidth/tileSize; i++) {
            //(x1,y1,x2,y2)
            g.drawLine(i*tileSize, 0,i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);

        } */

        //Food
        g.setColor(Color.red);
       // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake
        g.setColor(Color.yellow);
       // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //Snake Body
        for (int i = 0; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
           // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //Score keeping
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("You only got: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Your score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){ 
        food.x = random.nextInt(boardWidth/tileSize); //600/25 =24
        food.y = random.nextInt(boardHeight/tileSize); //gonna be random number from 0-24
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    public void move(){ 
        //eat food check 
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //each tile must catch up to one before before head can move
        //Snake Body
        for(int i = snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i ==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
                }
        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over conditions
        //Collides with body
        for (int i = 0; i< snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //Collide with snake head
            if (collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        if(snakeHead.x * tileSize <0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight ){ // if it hits the walls
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){ // game loop
        move(); 
        repaint(); //calls draw over and over again
        if (gameOver){
            gameLoop.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) { // this is for whenever key is pressed
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY !=1){ //&& so it cant go back and eat itself
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() ==KeyEvent.VK_DOWN && velocityY !=-1){
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() ==KeyEvent.VK_LEFT && velocityX !=1){
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() ==KeyEvent.VK_RIGHT && velocityX !=-1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    // do not need the rest
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
