package entity;

import main.GamePanel;
import java.util.Random;

public class NPC_Oldman extends Entity{
    public NPC_Oldman(GamePanel gp) {
        super(gp);
        direction="down";
        speed=1;
        getNPCImage();
        SetDialogue();
    }
    public void getNPCImage(){
        //New Method
        up1    =setUp("/res/NPC/oldman_up_1",gp.TileSize, gp.TileSize);
        up2    =setUp("/res/NPC/oldman_up_2",gp.TileSize, gp.TileSize);
        down1  =setUp("/res/NPC/oldman_down_1",gp.TileSize, gp.TileSize);
        down2  =setUp("/res/NPC/oldman_down_2",gp.TileSize, gp.TileSize);
        left1  =setUp("/res/NPC/oldman_left_1",gp.TileSize, gp.TileSize);
        left2  =setUp("/res/NPC/oldman_left_2",gp.TileSize, gp.TileSize);
        right1 =setUp("/res/NPC/oldman_right_1",gp.TileSize, gp.TileSize);
        right2 =setUp("/res/NPC/oldman_right_2",gp.TileSize, gp.TileSize);
    }
    public void SetDialogue(){
        Dialogues[0]="Yawwwwwwwwwwwww!!";
        Dialogues[1]="Hanooof!!";
        Dialogues[2]="lol!!";
        Dialogues[3]="Adhom!!";
        Dialogues[4]="Yawwwwwwwwwwwwww!!";
        Dialogues[5]="Yawwwwwwwwwwwwww!!";
        Dialogues[6]="Yawwwwwwwwwwwwww!!";


    }
    public void speak(){
        //check if dialogue
        if (Dialogues[dialogueIndex]!=null){
        gp.UI.CurrentDialogue=Dialogues[dialogueIndex];
        dialogueIndex++;}
        switch (gp.player.direction){
            case "up":
                direction="down";
                break;
            case "down":
                direction="up";
                break;
            case"right":
                direction="left";
                break;
            case "left":
                direction="right";
                break;
        }
    }
    public void setAction() {
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
