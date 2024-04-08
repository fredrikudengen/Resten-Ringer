package no.uib.inf101.sample;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {

    // The JFrame is the "root" application window.
    // We here set some properties of the main window,
    // and tell it to display our GamePanel
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("Sem2 Spill");

    // Create an instance of GamePanel
    Game gamePanel = new Game();

    // Add the GamePanel to the JFrame
    frame.add(gamePanel);

    // Call these methods to actually display the window
    frame.pack();
    frame.setVisible(true);

    // Sets window in the middle of the screen
    frame.setLocationRelativeTo(null);

    gamePanel.startGameThread();
  }
}
