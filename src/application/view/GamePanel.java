package application.view;

import application.Utilities;
import application.model.Game;
import application.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GamePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int WIDTH = Utilities.viewportWidth, HEIGHT = Utilities.viewportHeight;

    private BirdView birdView; //sprite

    private TubeView tubeView;

    private BackgroundView backgroundView;

    private BaseView baseView;

    private JButton playButton;
    private JButton exitButton;
    private JButton difficultyButton;

    private Font customFont;

    public GamePanel() {

        setLayout(null);

        birdView = new BirdView();

        tubeView = new TubeView();
        backgroundView = new BackgroundView();
        baseView = new BaseView();

        fontLoader();
        buttonAdder();
        addListeners();
    }

    private void buttonAdder() {

        playButton = new JButton("Play");
        exitButton = new JButton("X");
        difficultyButton = new JButton("EASY");

        add(playButton);
        add(exitButton);
        add(difficultyButton);

        Insets insets = getInsets();
        playButton.setBounds(100 + insets.left,820 + insets.top, 150, 100);
        exitButton.setBounds(10 + insets.left, 10 + insets.top, 45, 45);
        difficultyButton.setBounds(400 + insets.left,820 + insets.top, 150, 100);

        playButton.setBackground(Color.WHITE);
        playButton.setIcon(new ImageIcon(ImageLoader.getInstance().getImage(2)));

        exitButton.setBackground(Color.RED);

        difficultyButton.setBackground(Color.GREEN);
    }

    public void buttonVisibilityManager() {
        if(!Game.getInstance().getIsStarted()) {
            exitButton.setVisible(true);
            playButton.setVisible(false);
            difficultyButton.setVisible(false);
        }
        if(Game.getInstance().getIsStarted() && !Game.getInstance().getGameOver()) {
            playButton.setVisible(false);
            difficultyButton.setVisible(false);
        }
        if(Game.getInstance().getIsStarted() && Game.getInstance().getGameOver()) {
            playButton.setVisible(true);
            difficultyButton.setVisible(true);
        }
    }

    private void fontLoader() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/application/resources/PixelMplus10-Bold.ttf")).deriveFont(83f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,getClass().getResourceAsStream("/application/resources/PixelMplus10-Bold.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void addListeners() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Game.getInstance().getIsStarted() || Game.getInstance().getGameOver()) {
                    Sound.getInstance().startSwoosh();
                    Game.getInstance().restart();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        difficultyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Game.getInstance().getIsStarted() || Game.getInstance().getGameOver()) {
                    if(difficultyButton.getText() == "EASY") {
                        difficultyButton.setText("HARD");
                        difficultyButton.setBackground(Color.ORANGE);
                        Utilities.frequency = 14;
                        Utilities.speed = 6;
                    } else if(difficultyButton.getText() == "HARD") {
                        difficultyButton.setBackground(Color.GREEN);
                        difficultyButton.setText("EASY");
                        Utilities.frequency = 18;
                        Utilities.speed = 5;
                    }
                    Sound.getInstance().startPoint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < Game.getInstance().getBkgs().size(); i++) {
            Rectangle bkg = Game.getInstance().getBkgs().get(i);
            g.drawImage(backgroundView.bkgs.get(Game.getInstance().getSeed()%2), bkg.x, bkg.y, backgroundView.width, backgroundView.height,null);
        }

        g.drawImage(birdView.currentImage,Game.getInstance().getBird().getBird().x,Game.getInstance().getBird().getBird().y, birdView.dim, birdView.dim, null);

        for(int i = 0; i < Game.getInstance().getTubesDown().size(); i++) {
            Rectangle tube = Game.getInstance().getTubesDown().get(i);
            g.drawImage(tubeView.images.get(0),tube.x-2, tube.y, tubeView.width, tubeView.height,null);
        }

        for(int i = 0; i < Game.getInstance().getTubesUp().size(); i++) {
            Rectangle tube = Game.getInstance().getTubesUp().get(i);
            g.setColor(Color.green);
            g.drawImage(tubeView.images.get(1),tube.x-2, 0 + (tube.height - tubeView.height), tubeView.width, tubeView.height,null);
        }

        for(int i = 0; i < Game.getInstance().getBase().size(); i++) {
            Rectangle b = Game.getInstance().getBase().get(i);
            g.drawImage(baseView.currentImage, b.x, HEIGHT - 100, baseView.width, 150, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(customFont);

        if(!Game.getInstance().getIsStarted()) {
            float scale = 0.70f;
            int imageW = (int)(Utilities.viewportWidth*scale);
            int imageH = (int)(Utilities.viewportHeight*scale);
            g.drawImage(ImageLoader.getInstance().getImage(1), imageW*2/9,imageW/8,imageW, imageH, null);
        }

        if(Game.getInstance().getGameOver()) {
            g.setColor(Color.WHITE);
            g.drawImage(ImageLoader.getInstance().getImage(0), (Utilities.viewportWidth/2) - 250, Utilities.viewportHeight/2 - 300, 500, 125 , null);
            g.drawString("Score: " + String.valueOf(Game.getInstance().getScore()), Utilities.viewportWidth / 4 , Utilities.viewportHeight/2);
        }

        if (!Game.getInstance().getGameOver() && Game.getInstance().getIsStarted()) {
            g.drawString(String.valueOf(Game.getInstance().getScore()), WIDTH / 2 - 25, 150);
        }

    }

    public void update() {
        birdView.update();
        repaint();
    }
}
