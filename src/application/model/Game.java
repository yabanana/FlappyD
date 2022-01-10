package application.model;

import application.Utilities;


import java.awt.*;
import java.util.*;

import application.sound.Sound;

public class Game {

    private int WIDTH = Utilities.viewportWidth, HEIGHT = Utilities.viewportHeight, BIRD_SIZE = Utilities.birdSize;

    private Vector<Rectangle> tubesDown;
    private Vector<Rectangle> tubesUp;

    private Bird bird;

    private Vector<Rectangle> bkgs;
    private Vector<Rectangle> base;

    private boolean isStarted, gameOver;
    private Random random;

    public int yMotion;
    public float score;
    private int speed = Utilities.speed;

    public int seed;

    private static Game instance = null;

    private Game() {
        bird = new Bird(WIDTH/2 - 150, HEIGHT/2, BIRD_SIZE);
        tubesDown = new Vector<Rectangle>(3);
        tubesUp = new Vector<Rectangle>(3);

        bkgs = new Vector<Rectangle>(2);
        base = new Vector<Rectangle>(2);

        random = new Random();
        gameOver = false;
        score = 0;
        yMotion = 0;

        seed = 0;

        init();
    }

    public static Game getInstance() {
        if(instance == null)
            instance = new Game();
        return instance;
    }

    public void init() {

        int space = 250;
        int width = 100;
        int height = 50 + random.nextInt(300);

        Rectangle rD = new Rectangle(700, HEIGHT - height - 100, width, height);
        tubesDown.add(rD);

        Rectangle rU = new Rectangle(700, 0, width, HEIGHT - height - space);
        tubesUp.add(rU);

        addTube();

        bkgs.add(new Rectangle(0,0, WIDTH, HEIGHT));
        addBkg();
        addBkg();

        base.add(new Rectangle(0,HEIGHT-100, WIDTH, 150));
        addBase();
        addBase();
    }

    public synchronized void addTube() {

        int space = 250;
        int width = 100;
        int height = 50 + random.nextInt(300);

        tubesDown.add(new Rectangle(tubesDown.get(tubesDown.size() - 1).x + 400, HEIGHT - height - 100, width, height));
        tubesUp.add(new Rectangle(tubesDown.get(tubesDown.size() - 1).x, 0, width, HEIGHT - height - space));
    }

    public synchronized void deleteTube() {

        if(isStarted && ! gameOver) {
            for(int i = 0; i < tubesDown.size(); i++) {
                Rectangle tmp1 = tubesDown.get(i);
                Rectangle tmp2 = tubesUp.get(i);


                if(tmp1.x < - tmp1.width ) {
                    tubesDown.remove(tmp1);
                    tubesUp.remove(tmp2);
                    addTube();
                }
            }
        }
    }

    private synchronized void moveTubes() {
        for(Rectangle tube : tubesDown) {
            tube.x -= speed;
        }
        for(Rectangle tube : tubesUp) {
            tube.x -= speed;
        }
    }

    public synchronized void addBkg() {
        int width = WIDTH;
        int height = HEIGHT;

        bkgs.add(new Rectangle(bkgs.get(bkgs.size()-1).x + WIDTH, 0, width, height));
    }

    public synchronized void deleteBkg() {

        if(isStarted && ! gameOver) {
            for(int i = 0; i < bkgs.size(); i++) {
                Rectangle tmp = bkgs.get(i);

                if(tmp.x < - tmp.width ) {
                    bkgs.remove(tmp);

                        addBkg();
                }
            }
        }
        if(!isStarted) {
            for(int i = 0; i < bkgs.size(); i++) {
                Rectangle tmp = bkgs.get(i);

                if(tmp.x < - tmp.width ) {
                    bkgs.remove(tmp);

                        addBkg();
                }
            }
        }
    }

    public synchronized void moveBkg() {
        for(Rectangle bkg : bkgs) {
            bkg.x -= speed/5;
        }
    }

    public synchronized void addBase() {
        int width = WIDTH;
        int height = 150;

        base.add(new Rectangle(base.get(base.size()-1).x + WIDTH, HEIGHT -100, width, height));
    }

    public synchronized void deleteBase() {

        if(isStarted && ! gameOver) {
            for(int i = 0; i < base.size(); i++) {
                Rectangle tmp = base.get(i);


                if(tmp.x < - tmp.width ) {
                    base.remove(tmp);

                    addBase();
                }
            }
        }

        if(!isStarted) {
            for(int i = 0; i < base.size(); i++) {
                Rectangle tmp = base.get(i);


                if(tmp.x < - tmp.width ) {
                    base.remove(tmp);

                    addBase();
                }
            }
        }
    }

    public synchronized void moveBase() {
        for(Rectangle b : base) {
            b.x -= speed*3/2;
        }
    }

    private void setScore(Rectangle tube) {
        if(tube.y == 0 && bird.getBird().x + bird.getBird().width/2 > tube.x + tube.width/2 -10 && bird.getBird().x + bird.getBird().width/2 < tube.x + tube.width/2 +10) {
            score += 0.25;
            Sound.getInstance().startPoint();
        }
    }

    private void setGameOver(Rectangle tube) {
        if(tube.intersects(bird.getBird()) || (bird.getBird().y > HEIGHT - 120) || (bird.getBird().y < 0)) {
            gameOver = true;
            Sound.getInstance().startHit();
        }
    }

    public synchronized void isGameOver() {

        for(Rectangle tube : tubesDown) {
            setScore(tube);
            setGameOver(tube);
        }
        for(Rectangle tube : tubesUp) {
            setScore(tube);
            setGameOver(tube);
        }
    }

    public void bump() {

        if(!isStarted) {
            if(yMotion > 0)
                yMotion = 0;
            yMotion -= 36;
        }

        if(yMotion > 0)
            yMotion = 0;
        yMotion -= 24;
    }

    public void restart() {
        if(gameOver) {
            bird = new Bird(WIDTH / 2 - 150 , HEIGHT / 2 , BIRD_SIZE);
            tubesDown.clear();
            tubesUp.clear();
            bkgs.clear();
            base.clear();
            yMotion = 0;
            score = 0;

            seed++;

            init();

            gameOver = false;
        }
    }

    public void jump() {
        if(!isStarted){
            isStarted = true;
            Sound.getInstance().startSwoosh();
        }

        if(!gameOver && isStarted) {
            bump();
            Sound.getInstance().startWing();
        }
    }

    public void initScreenUpdate() {
        Game.getInstance().deleteBase();
        Game.getInstance().deleteBkg();
        Game.getInstance().moveBase();
        Game.getInstance().moveBkg();
        Game.getInstance().addBirdPos();
        Game.getInstance().moveBird();
        if(Game.getInstance().getBird().getBird().y > 720)
            Game.getInstance().bump();
    }

    public void update() {

        deleteTube();
        deleteBkg();
        deleteBase();
        addBirdPos();
        moveBird();
        isGameOver();
        moveTubes();
        moveBkg();
        moveBase();
    }

    public void moveBird()
    {
        bird.getBird().y += yMotion/3;
    }

    public void addBirdPos()
    {
        yMotion += Utilities.GRAVITY;
    }

    public Bird getBird() {return bird;}

    public Vector<Rectangle> getTubesDown() {return tubesDown;}
    public Vector<Rectangle> getTubesUp() {return tubesUp;}
    public Vector<Rectangle> getBkgs() {return bkgs;}
    public Vector<Rectangle> getBase() {return base;}

    public boolean getIsStarted() {return isStarted;}

    public boolean getGameOver() {return gameOver;}

    public int getScore() {return (int)score;}

    public boolean upwards() {
        return  yMotion < 0;
    }

    public int getSeed() {return seed;}
}