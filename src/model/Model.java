package model;

import java.sql.SQLException;
import java.util.List;

public class Model {
    private int width, height;
    private Jeu jeu;
    private List<Jeu> jeux;
    private long lastFrame; // used to regulate FPS
    private long frameGap; // used to regulate FPS
    public Model(int width, int height) {
        this.width = width;
        this.height = height;
        jeu = new Jeu();
        lastFrame = -1;
        frameGap = 10000000;
    }

    public Model(int width, int height, int nbJeux) throws SQLException {
        this.width = width;
        this.height = height;
        lastFrame = -1;
        frameGap = 10000000;

        for(int j = 0; j < nbJeux; j++) {
            jeux.add(j,new Jeu(j+1));
        }
    }

    public Jeu getJeu() {
        return jeu;
    }

    public Jeu getJeuNum(int n) {
        return jeux.get(n);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setLastFrame(long lastFrame) {
        this.lastFrame = lastFrame;
    }

    public long getLastFrame() {
        return lastFrame;
    }

    public long getFrameGap() {
        return frameGap;
    }

    public void setFrameGap(long frameGap) {
        this.frameGap = frameGap;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
