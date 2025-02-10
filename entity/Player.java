package entity;

import main.Gamepanel;
import main.KeyHandler;

public class Player extends Entity {

    Gamepanel gp;
    KeyHandler keyH;

    public Player(Gamepanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }


}
