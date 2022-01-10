package application.view;

import application.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BaseView {
    Image currentImage;
    int height;
    int width;

    BaseView() {

        width = Utilities.viewportWidth;
        height = 100;

        try {
            Image tmpImage = ImageIO.read(getClass().getResourceAsStream("/application/resources/base.png"));
            currentImage = tmpImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
