package application;

import application.controller.Controller;

public class GameLoop extends Thread{

    private Controller controller;

    public GameLoop(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            controller.update();
            try{
                Thread.sleep(Utilities.frequency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
