package application.model;

import java.awt.*;

public class Bird {

    private Rectangle bird;

    Bird(int x, int y, int birdSize) {
        bird = new Rectangle(x,y,birdSize,birdSize);
    }

    public Rectangle getBird() {return bird;}
}
