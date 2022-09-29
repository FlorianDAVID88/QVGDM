package controllers;

import model.Model;
import view.View;

public class Controller {
    protected Model model;
    protected View view;
    protected ControllerKeyBoard controllerKeyBoard;
    protected ControllerAnimation controllerAnimation;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        controllerKeyBoard = new ControllerKeyBoard(model, view, this);
        controllerAnimation = new ControllerAnimation(model,view,this);
    }

    public void startAnimation() {
        controllerAnimation.start();
    }
}
