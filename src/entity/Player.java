package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{
    //create an Object from GamePanel
    GamePanel gp;
    //create an Object from KeyHandler
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey=0;
    public Player(GamePanel gp,KeyHandler keyH){
        super(gp);
        this.gp=gp;
        this.keyH=keyH;
        SolidArea=new Rectangle();
        SolidArea.x=8;
        SolidArea.y=16;
        SolidArea.width=20;
        SolidArea.height=24;
        DefaultSolidPartX=SolidArea.x;
        DefaultSolidPartY=SolidArea.y;
        screenX=gp.screenWidth/2-(gp.TileSize/2);
        screenY=gp.screenHeight/2-(gp.TileSize/2);

        attackArea.width=46;
        attackArea.height=46;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        //STARTING Positions
        WorldX=gp.TileSize*40;
        WorldY= gp.TileSize*10;
        speed=3;
        direction="down";
        //playerStatus
        MAXHealth=6;
        Life=MAXHealth;
    }
    public void getPlayerImage(){
        //New Method
        up1    =setUp("/res/player/boy_up_1",gp.TileSize, gp.TileSize);
        up2    =setUp("/res/player/boy_up_2",gp.TileSize, gp.TileSize);
        down1  =setUp("/res/player/boy_down_1",gp.TileSize, gp.TileSize);
        down2  =setUp("/res/player/boy_down_2",gp.TileSize, gp.TileSize);
        left1  =setUp("/res/player/boy_left_1",gp.TileSize, gp.TileSize);
        left2  =setUp("/res/player/boy_left_2",gp.TileSize, gp.TileSize);
        right1 =setUp("/res/player/boy_right_1",gp.TileSize, gp.TileSize);
        right2 =setUp("/res/player/boy_right_2",gp.TileSize, gp.TileSize);
        //Old Method
        //        try{
        //            up1    =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_1.png")));
        //            up2    =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_2.png")));
        //            down1  =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_1.png")));
        //            down2  =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_2.png")));
        //            left1  =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_1.png")));
        //            left2  =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_2.png")));
        //            right1 =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_1.png")));
        //            right2 =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_2.png")));
        //
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }
    public void getPlayerAttackImage(){
        //New Method
        attackup1    =setUp("/res/player/boy_attack_up_1",gp.TileSize, gp.TileSize*2);
        attackup2    =setUp("/res/player/boy_attack_up_2",gp.TileSize, gp.TileSize*2);
        attackdown1  =setUp("/res/player/boy_attack_down_1",gp.TileSize, gp.TileSize*2);
        attackdown2  =setUp("/res/player/boy_attack_down_2",gp.TileSize, gp.TileSize*2);
        attackattackleft1  =setUp("/res/player/boy_attack_left_1",gp.TileSize*2, gp.TileSize);
        attackleft2  =setUp("/res/player/boy_attack_left_2",gp.TileSize*2, gp.TileSize);
        attackright1 =setUp("/res/player/boy_attack_right_1",gp.TileSize*2, gp.TileSize);
        attackright2 =setUp("/res/player/boy_attack_right_2",gp.TileSize*2, gp.TileSize);
    }
    public void Attacking(){
        spriteCounter++;
        if (spriteCounter<=5){
            spriteNum=1;
        }else if (spriteCounter>5&&spriteCounter<=25){
            spriteNum=2;
       }else if(spriteCounter>25){
            spriteNum=1;
            spriteCounter=0;
            attack=false;
        }
   }
    public void interactNPC(int i){

       if (gp.keyH.EnteredPressed){
       if (i!=999){
            gp.gameState= gp.dialogueState;
            gp.npc[i].speak();
           }else{
           gp.PlaySE(6);
           attack=true;
          }
       }

   }
    public void PickUpObject(int index){
        if(index!=999){
        String objName=gp.obj[index].name;

            switch (objName){
                case "key":
                    hasKey++;
                    gp.obj[index]=null;
                    break;
                case "Door":
                    if (hasKey>0){
                        gp.obj[index]=null;
                        gp.stopMusic();
                        gp.PlaySE(3);
                    }
                    break;
                case "Chest":
                    gp.UI.GameFinished=true;
                    if ( gp.UI.GameFinished){
                        gp.UI.DrawVectory();
                        gp.SE.setFile(0);
                        gp.SE.Play();
                    }
                    break;
                 }
        }
    }
    public void ContactMonster(int i){
        if(i!=999){
            if (!invincible)
            Life-=1;
            invincible=true;
        }
    }
    public void damageMonster(int i){
        if(i!=999) {
            if (!gp.mon[i].invincible){
                gp.mon[i].Life-=1;
                gp.mon[i].invincible=true;
                if (gp.mon[i].Life<=0){
                    gp.mon[i].dying=true;
                    gp.mon[i]=null;
                }
            }
        }
    }
    public void update(){
        if (attack){
            Attacking();
        }else if (keyH.upPressed|| keyH.downPressed|| keyH.leftPressed|| keyH.rightPressed||keyH.EnteredPressed){
            if(keyH.upPressed){
                direction="up";
            }else if(keyH.downPressed){
                direction="down";

            }else if (keyH.rightPressed){
                direction="right";

            }else if (keyH.leftPressed){
                direction="left";
            }

            //Check Tile Collision
            collisionStatus=false;
            gp.Checker.CheckTile(this);//this receives player class as entity
            //Check Object Collision

            int objectIndex= gp.Checker.CheckObj(this,true);
            PickUpObject(objectIndex);

            int npcIndex=gp.Checker.CheckEntity(this, gp.npc);
            interactNPC(npcIndex);

            //checkEvent
            gp.EH.checkEvent();

            //Check Monster Collision
            int monsterIndex=gp.Checker.CheckEntity(this,gp.mon);
            ContactMonster(monsterIndex);
            //If Collision is true the player cannot move
            if (!collisionStatus&& !keyH.EnteredPressed){
                switch (direction){
                    case "up":
                        WorldY-=speed;
                        break;
                    case "down":
                        WorldY+=speed;
                        break;
                    case "left":
                        WorldX-=speed;
                        break;
                    case "right":
                        WorldX+=speed;
                        break;
                }
            }
            keyH.EnteredPressed=false;
            spriteCounter++;
            if (spriteCounter>14) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                    //We store in variables a temp data to know where the collision happened
                    int currentWorldX=WorldX;
                    int currentWorldY=WorldY;
                    int SolidAreaWidth=SolidArea.width;
                    int SolidAreaHeight=SolidArea.height;
                    //Adjust players WorldX/Y for AttackArea
                    switch (direction){
                        case "up":
                            WorldY-=attackArea.height;
                            break;
                        case "down":
                            WorldY+=attackArea.height;
                            break;
                        case "left" :
                            WorldX-=attackArea.width;
                            break;
                        case "right":
                            WorldX+=attackArea.width;
                            break;
                    }
                    SolidArea.width=attackArea.width;
                    SolidArea.height=attackArea.height;
                    //Check Monster collision with the updated WorldY,WorldX and SolidArea
                    int MonsterIndex=gp.Checker.CheckEntity(this,gp.mon);
                    damageMonster(MonsterIndex);

                    //After Checking the condition
                    WorldX=currentWorldX;
                    WorldY=currentWorldY;
                    SolidArea.width=SolidAreaWidth;
                    SolidArea.height=SolidAreaHeight;

                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            spriteNum=1;//stand position
        }
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter>60){
                invincible=false;
                invincibleCounter=0;
            }
        }
        if (Life<=0){
            gp.gameState=gp.GameOver;

        }
    }
    public void draw(Graphics2D g2){

        BufferedImage Image=null;
        int tempScreenX=screenX;
        int tempScreenY=screenY;
        switch (direction){
            case "up":
                if (!attack){
                    if (spriteNum==1){
                        Image=up1;
                    }
                    if (spriteNum==2){
                        Image=up2;
                    }
                } if (attack){
                    tempScreenY=screenY-gp.TileSize;
                if (spriteNum==1){
                    Image=attackup1;
                }
                if (spriteNum==2){
                    Image=attackup2;
                }
                }

                break;
            case "down":
                if (!attack) {
                    if (spriteNum == 1) {
                        Image = down1;
                    }
                    if (spriteNum == 2) {
                        Image = down2;
                    }
                }
                if (attack){
                    if (spriteNum == 1) {
                        Image = attackdown1;
                    }
                    if (spriteNum == 2) {
                        Image = attackdown2;
                    }
                }
                break;
            case "left":
                if (!attack) {
                    if (spriteNum == 1) {
                        Image = left1;
                    }
                    if (spriteNum == 2) {
                        Image = left2;
                    }
                }if (attack){
                    tempScreenX=screenX-gp.TileSize;
                if (spriteNum == 1) {
                    Image = attackattackleft1;
                }
                if (spriteNum == 2) {
                    Image = attackleft2;
                }
            }
                break;
            case "right":
                    if (!attack) {
                        if (spriteNum == 1) {
                            Image = right1;
                        }
                        if (spriteNum == 2) {
                            Image = right2;
                        }
                    }if (attack){
                if (spriteNum == 1) {
                    Image = attackright1;
                }
                if (spriteNum == 2) {
                    Image = attackright2;
                }
            }
                break;
        }
        //if the player take damage from monster
        if (invincible){
            //obesity =0.3;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2.drawImage(Image,tempScreenX,tempScreenY, null);
        //Reset the obesity;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

//        g2.setColor(Color.cyan);
//        g2.drawRect(screenX+SolidArea.x,screenY+SolidArea.y,SolidArea.width,SolidArea.height);
    }

}
