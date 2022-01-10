package application.view;

import application.Utilities;
import application.model.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BirdView {

    private ArrayList<Image> images;
    Image currentImage;
    int dim;
    private int index;

    BirdView() {
        images = new ArrayList<Image>();
        dim = Utilities.birdSize;
        index = 0;
        try {
            for(int i = 0; i < 3; i++) {
                Image img = ImageIO.read(getClass().getResourceAsStream("/application/resources/redbird-"+ i +".png"));
                images.add(img);
            }
            currentImage = images.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(Game.getInstance().upwards()) {
            index++;
            if(index == images.size())
                index = 0;
        }
        else {
            index = 2;
        }
        currentImage = images.get(index);
    }
}
