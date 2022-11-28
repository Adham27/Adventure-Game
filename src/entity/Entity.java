package entity;
import main.GamePanel;
import main.Tools;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    public int WorldX,WorldY;//Reference to Player Position at the map
    public int speed;
    public String direction="down";
    public int spriteCounter=0;
    public int spriteNum=1;



    public BufferedImage image,image2,image3;//Heart images
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;//Player
    public BufferedImage attackup1,attackup2,attackdown1,attackdown2,attackattackleft1,attackleft2,attackright1,attackright2;//Attacking Player
    public boolean attack=false;



    public Rectangle SolidArea=new Rectangle(0,0,32,32);//create obj class rectangle
    public Rectangle attackArea=new Rectangle(0,0,0,0);
    public Rectangle SolidPart=new Rectangle(0,0,32,32);//player


    public int DefaultSolidPartX,DefaultSolidPartY;
    public boolean collision=false;
    public boolean collisionStatus=false;

    //Character Status
    public int MAXHealth;
    public boolean invincible=false;
    public int invincibleCounter=0;
    public int Life;
    public int dialogueIndex=0;
    String[] Dialogues=new String[10];


    //Obj Characteristic
    public String name;
    public int actionLockCounter;
    public Tools tool=new Tools();//Scale image foe better drawing
    public int type;//0 is player //1 is npc // 2 is monster
    public boolean dying=false;


    public Entity(GamePanel gp) {
        this.gp=gp;
    }
    public void setAction(){}
    public void speak(){}

    public void update(){
        setAction();
        collisionStatus=false;
        gp.Checker.CheckTile(this);
        gp.Checker.CheckObj(this,false);
        gp.Checker.CheckEntity(this,gp.npc);
        gp.Checker.CheckEntity(this, gp.mon);
        Boolean ContactPlayer=gp.Checker.checkPlayer(this);
        //type=0(player),type=1(npc),type=2(monster)
        if (this.type==2&&ContactPlayer){
            if (!gp.player.invincible){
                gp.player.Life-=1;
                gp.player.invincible=true;
            }
        }
        if (!collisionStatus){
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
        spriteCounter++;
        if (spriteCounter>14) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter>10){
                invincible=false;
                invincibleCounter=0;
            }
        }

    }


public BufferedImage setUp(String imageName,int width,int height){
    BufferedImage Image=null;
    try{
        Image= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName+".png")));
        Image=tool.ScaledImage(Image,width,height);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Image;
}

    public void draw(Graphics2D g2){
        BufferedImage image=null;
        int screenX=WorldX- gp.player.WorldX+gp.player.screenX;
        int screenY=WorldY- gp.player.WorldY+gp.player.screenY;
        //draw next tile
        //This if condition load the tiles around the player only without load the whole map
        if(     WorldX+gp.TileSize>gp.player.WorldX-gp.player.screenX&&
                WorldX-gp.TileSize<gp.player.WorldX+gp.player.screenX&&
                WorldY+gp.TileSize>gp.player.WorldY-gp.player.screenY&&
                WorldY-gp.TileSize<gp.player.WorldY+gp.player.screenY ){
            switch (direction){
                case "up":
                    if (spriteNum==1){
                        image=up1;
                    }
                    if (spriteNum==2){
                        image=up2;
                    }
                    break;
                case "down":
                    if (spriteNum==1){
                        image=down1;
                    }
                    if (spriteNum==2){
                        image=down2;
                    }

                    break;
                case "left":
                    if (spriteNum==1){
                        image=left1;
                    }
                    if (spriteNum==2){
                        image=left2;
                    }
                    break;
                case "right":
                    if (spriteNum==1){
                        image=right1;
                    }
                    if (spriteNum==2){
                        image=right2;
                    }
                    break;
            }
            //when the player hit the monster or vice versa

            if (invincible){
                //obesity =0.3;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
            }
            g2.drawImage(image,screenX,screenY,null);

        }
    }

}

