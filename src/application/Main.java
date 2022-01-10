package application;

import application.controller.Controller;
import application.view.GamePanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(Utilities.viewportWidth, Utilities.viewportHeight);

        GamePanel gp = new GamePanel();
        Controller controller = new Controller(gp);

        gp.addMouseListener(controller);

        frame.setUndecorated(true);
        frame.setLocation((int) (Utilities.screenSize.getWidth()/2 - Utilities.viewportWidth/2),0);
        frame.add(gp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        GameLoop loop = new GameLoop(controller);
        loop.start();
    }
}
