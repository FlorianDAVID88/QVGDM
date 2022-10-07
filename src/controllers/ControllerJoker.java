package controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Model;
import view.View;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ControllerJoker implements EventHandler<MouseEvent> {
    private static final int NJOKERS = 4;
    Model model;
    View view;

    /**
     * Initialise un ControllerJoker courant avec le modèle et la vue de l'application
     * @param model le Model de l'application
     * @param view la vue de l'application
     */
    public ControllerJoker(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        for(int i = 0; i < NJOKERS; i++) {
            if(mouseEvent.getSource() == view.getImgJoker(i)) {
                System.out.println("Clicked on joker " + model.getJeu().getJokerNum(i).getNameJoker());
                view.getImgJokerUsed(i).setVisible(true);
                view.getImgJokerUsed(i).setOnMouseClicked(null);
                model.getJeu().getJokerNum(i).setUseJoker();
                try {
                    view.utiliserJoker(view.getNumActualQ(), i);
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
