package controllers;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Model;
import view.View;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllerMouse implements EventHandler<MouseEvent> {
    private static final int NREPONSES = 4;
    Model model;
    View view;

    public ControllerMouse(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == view.getGridPaneQu()) {
            view.getGridPaneQu().setOnMouseClicked(null);

            AtomicInteger finalI = new AtomicInteger(0);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event ->
                view.getRootPane().getChildren().add(view.getButtonTextRep(finalI.getAndIncrement()))
            );

            SequentialTransition timer = new SequentialTransition(pause);
            timer.setCycleCount(NREPONSES);
            timer.play();
        }

        if(mouseEvent.getSource() == view.getGridPaneSomme()) {
            try {
                view.beforeQuestion(view.getNumActualQ() + 1);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(mouseEvent.getSource() == view.getGridPaneArret()) {
            try {
                view.stopGame();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
