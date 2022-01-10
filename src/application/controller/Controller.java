package application.controller;

import application.model.Game;
import application.view.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter {

    private GamePanel gp;

    public Controller(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Game.getInstance().jump();
    }

    public void update() {

        if(Game.getInstance().getIsStarted() && !Game.getInstance().getGameOver())
            Game.getInstance().update();

        if(!Game.getInstance().getIsStarted())
            Game.getInstance().initScreenUpdate();

        gp.buttonVisibilityManager();
        gp.update();
        gp.repaint();
    }
}
