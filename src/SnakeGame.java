/*
 * Title: Snake Game
 * Description: This Java class implements the main logic for a simple Snake game using Swing GUI.
 * Author: Romeo Bessenaar
 * Date: 2021/07/21
 */

 import java.awt.*;
 import java.awt.event.*;
 import java.util.ArrayList;
 import java.util.Random;
 import javax.swing.*;
 
 public class SnakeGame extends JPanel implements ActionListener, KeyListener { // KeyListener for the key input
     
     // Inner class to represent a single tile on the game board
     private class Tile {
         int x;
         int y;
 
         Tile(int x, int y) {
             this.x = x;
             this.y = y;
         }
     }
 
     // Game board dimensions and tile size
     int boardWidth;
     int boardHeight;
     int tileSize = 25;
 
     // Snake variables
     Tile snakeHead;
     ArrayList<Tile> snakeBody;
 
     // Food variables
     Tile food;
     Random random;
 
     // Game logic variables
     Timer gameLoop;
     int velocityX;
     int velocityY;
     boolean gameOver = false;
 
     // Constructor for the SnakeGame class
     SnakeGame(int boardWidth, int boardHeight) {
         this.boardWidth = boardWidth;
         this.boardHeight = boardHeight;
         setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
         setBackground(Color.black);
         addKeyListener(this);
         setFocusable(true);
 
         // Initialize snake head and body
         snakeHead = new Tile(5, 5);
         snakeBody = new ArrayList<Tile>();
 
         // Initialize food and random generator
         food = new Tile(10, 10);
         random = new Random();
         placeFood();
 
         // Initialize velocities
         velocityX = 0;
         velocityY = 0;
 
         // Initialize game loop timer
         gameLoop = new Timer(100, this);
         gameLoop.start();
     }
 
     // Method to paint components on the JPanel
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         draw(g);
     }
 
     // Method to draw game components
     public void draw(Graphics g) {
         // Draw food
         g.setColor(Color.red);
         g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
 
         // Draw snake head
         g.setColor(Color.yellow);
         g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
 
         // Draw snake body
         for (int i = 0; i < snakeBody.size(); i++) {
             Tile snakePart = snakeBody.get(i);
             g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
         }
 
         // Draw score
         g.setFont(new Font("Arial", Font.PLAIN, 16));
         if (gameOver) {
             g.setColor(Color.red);
             g.drawString("You only got: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
         } else {
             g.drawString("Your score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
         }
     }
 
     // Method to place food at random position on the board
     public void placeFood() {
         food.x = random.nextInt(boardWidth / tileSize);
         food.y = random.nextInt(boardHeight / tileSize);
     }
 
     // Method to check collision between two tiles
     public boolean collision(Tile tile1, Tile tile2) {
         return tile1.x == tile2.x && tile1.y == tile2.y;
     }
 
     // Method to move the snake
     public void move() {
         // Eat food check
         if (collision(snakeHead, food)) {
             snakeBody.add(new Tile(food.x, food.y));
             placeFood();
         }
 
         // Move snake body parts
         for (int i = snakeBody.size() - 1; i >= 0; i--) {
             Tile snakePart = snakeBody.get(i);
             if (i == 0) {
                 snakePart.x = snakeHead.x;
                 snakePart.y = snakeHead.y;
             } else {
                 Tile prevSnakePart = snakeBody.get(i - 1);
                 snakePart.x = prevSnakePart.x;
                 snakePart.y = prevSnakePart.y;
             }
         }
 
         // Move snake head
         snakeHead.x += velocityX;
         snakeHead.y += velocityY;
 
         // Game over conditions
         // Collides with body
         for (int i = 0; i < snakeBody.size(); i++) {
             Tile snakePart = snakeBody.get(i);
             // Collide with snake head
             if (collision(snakeHead, snakePart)) {
                 gameOver = true;
             }
         }
         // Collides with walls
         if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0
                 || snakeHead.y * tileSize > boardHeight) {
             gameOver = true;
         }
     }
 
     // ActionListener interface implementation for game loop
     @Override
     public void actionPerformed(ActionEvent e) {
         move();
         repaint();
         if (gameOver) {
             gameLoop.stop();
         }
     }
 
     // KeyListener interface implementation for handling key events
     @Override
     public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
             velocityX = 0;
             velocityY = -1;
         } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
             velocityX = 0;
             velocityY = 1;
         } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
             velocityX = -1;
             velocityY = 0;
         } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
             velocityX = 1;
             velocityY = 0;
         }
     }
 
     // Unused KeyListener interface methods
     @Override
     public void keyTyped(KeyEvent e) {}
 
     @Override
     public void keyReleased(KeyEvent e) {}
 }
 