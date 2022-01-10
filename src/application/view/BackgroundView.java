package application.view;

import application.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BackgroundView {

    ArrayList<Image> bkgs;
    int height;
    int width;

    BackgroundView() {

        bkgs = new ArrayList<Image>();
        width = Utilities.viewportWidth;
        height = Utilities.viewportHeight;

        try {
            Image tmpImage0 = ImageIO.read(getClass().getResourceAsStream("/application/resources/background-day.png"));
            Image tmpImage1 = ImageIO.read(getClass().getResourceAsStream("/application/resources/background-night.png"));

            bkgs.add(tmpImage0);
            bkgs.add(tmpImage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
