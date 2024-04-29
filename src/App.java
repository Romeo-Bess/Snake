/*
 * Title: Snake Game
 * Description: This Java program implements a simple Snake game using Swing GUI.
 * Author: Romeo Bessenaar
 * Date: 2021/07/21
 */

 import javax.swing.*;

 public class App {
     public static void main(String[] args) throws Exception {
         // Define the dimensions of the game board
         int boardWidth = 600;
         int boardHeight = boardWidth;
 
         // Create a JFrame for the game window
         JFrame frame = new JFrame("Snake");
         frame.setVisible(true);
         frame.setSize(boardWidth, boardHeight);
         // Center the window on the screen
         frame.setLocationRelativeTo(null);
         // Disable window resizing 
         frame.setResizable(false); 
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
         // Create an instance of the SnakeGame class, passing the board dimensions
         SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
 
         // Add the SnakeGame component to the JFrame
         frame.add(snakeGame);
         // Adjust the frame size to fit the SnakeGame component
         frame.pack();
         // Set focus to the SnakeGame component for keyboard input 
         snakeGame.requestFocus(); 
     }
 }
 