package controllers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Model;
import view.View;

public class ControllerKeyBoard implements EventHandler<KeyEvent> {
    Model model;
    View view;
    Controller control;

    public ControllerKeyBoard(Model model, View view, Controller control) {
        this.model = model;
        this.view = view;
        this.control = control;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}
