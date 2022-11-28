package object;

import entity.Entity;
import main.GamePanel;

public class HeartObject extends Entity {
    public HeartObject(GamePanel gp){
        super(gp);
        name="Heart";
        image= setUp("/res/Objects/heart_full",gp.TileSize, gp.TileSize);
        image2= setUp("/res/Objects/heart_half",gp.TileSize, gp.TileSize);
        image3= setUp("/res/Objects/heart_blank",gp.TileSize, gp.TileSize);

    }
}
