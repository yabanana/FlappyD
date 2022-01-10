package application.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ImageLoader {

    private ArrayList<Image> images;

    private static ImageLoader instance = null;

    private ImageLoader() {
        images = new ArrayList<Image>();

        try {
            Image img0 = ImageIO.read(getClass().getResourceAsStream("/application/resources/gameover.png"));
            images.add(img0);
            Image img1 = ImageIO.read(getClass().getResourceAsStream("/application/resources/message.png"));
            images.add(img1);
            Image img2 = ImageIO.read(getClass().getResourceAsStream("/application/resources/playBtn.png"));
            images.add(img2);
            Image img3 = ImageIO.read(getClass().getResourceAsStream("/application/resources/extt.png"));
            images.add(img3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageLoader getInstance() {
        if(instance == null)
            instance = new ImageLoader();
        return instance;
    }

    public Image getImage(int index) {return images.get(index);}
}