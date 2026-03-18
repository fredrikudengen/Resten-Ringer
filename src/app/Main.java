package app;

import controller.Game;

import java.io.IOException;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) throws IOException {
    JFrame frame = new JFrame();
    Game game = new Game();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("Resten Ringer");
    frame.add(game);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    game.startGameThread();
  }
}