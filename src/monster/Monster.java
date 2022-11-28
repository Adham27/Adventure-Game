package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Monster extends Entity {
    GamePanel gp;
    public Monster(GamePanel gp) {
        super(gp);
        this.gp=gp;
        name="Green Slime";
        speed=1;
        MAXHealth=4;
        Life=MAXHealth;
        type=2;
        SolidArea.x=3;
        SolidArea.y=18;
        SolidArea.width=42;
        SolidArea.height=30;
        DefaultSolidPartX=SolidArea.x;
        DefaultSolidPartY=SolidArea.y;
        getImage();
    }
    public void getImage(){
        up1    =setUp("/res/monster/greenslime_down_1",gp.TileSize, gp.TileSize);
        up2    =setUp("/res/monster/greenslime_down_2",gp.TileSize, gp.TileSize);
        down1  =setUp("/res/monster/greenslime_down_1",gp.TileSize, gp.TileSize);
        down2  =setUp("/res/monster/greenslime_down_2",gp.TileSize, gp.TileSize);
        left1  =setUp("/res/monster/greenslime_down_1",gp.TileSize, gp.TileSize);
        left2  =setUp("/res/monster/greenslime_down_2",gp.TileSize, gp.TileSize);
        right1 =setUp("/res/monster/greenslime_down_1",gp.TileSize, gp.TileSize);
        right2 =setUp("/res/monster/greenslime_down_2",gp.TileSize, gp.TileSize);
    }
    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//pickUp a number from 1-100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i < 50) {
                direction = "down";
            }
            if (i > 50 && i < 75) {
                direction = "left";
            }
            if (i > 75 && i < 100) {
                direction = "right";
            }
            actionLockCounter=0;
        }

    }
}
