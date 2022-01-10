package application.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class TubeView {

    Vector<Image> images;
    int width;
    int height;

    TubeView() {

        images = new Vector<Image>(2);

        width = 104;
        height = 800;

        try {
            Image tmpImage0 = ImageIO.read(getClass().getResourceAsStream("/application/resources/pipeDown.png"));
            Image tmpImage1 = ImageIO.read(getClass().getResourceAsStream("/application/resources/pipeUp.png"));

            images.add(tmpImage0);
            images.add(tmpImage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
