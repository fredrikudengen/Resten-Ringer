package model;

import java.io.IOException;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) throws IOException {
    // The JFrame is the "root" application window.
    // We here set some properties of the main window,
    // and tell it to display our GamePanel
    JFrame frame = new JFrame();
    Game game = new Game();    

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("Resten Ringer");
    frame.add(game);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    game.startGameThread();
  }
}
