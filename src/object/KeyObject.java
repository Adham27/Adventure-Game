package object;

import entity.Entity;
import main.GamePanel;

public class KeyObject extends Entity {

    public KeyObject(GamePanel gp){
        super(gp);
        name="key";
        down1=setUp("/res/Objects/key",gp.TileSize, gp.TileSize);
    }
}

