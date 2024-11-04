package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Detective Escape");

        // Add the GamePanel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // window other settings
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // game loop starts
        gamePanel.startGameThread();
    }
}