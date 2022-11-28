package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Graphics2D g2;
    Rectangle eventRect;
    int eventRectDefaultX,eventRectDefaultY;
    public EventHandler(GamePanel gp){
        this.gp=gp;
        eventRect = new Rectangle();
        eventRect.x=23;
        eventRect.y=32;
        eventRect.width=112;
        eventRect.height=112;
        eventRectDefaultX=eventRect.x;
        eventRectDefaultY=eventRect.y;

    }
    public void checkEvent(){

        if (hit(27,26,"right")){
            DamagePit(44);
        }
    }
    public boolean hit(int eventCol,int eventRow,String requiredDirection){
        boolean hit=false;
        //Getting pLayer's Current SolidArea Positions
        gp.player.SolidPart.x=gp.player.WorldX+gp.player.SolidPart.x;
        gp.player.SolidPart.y=gp.player.WorldY+gp.player.SolidPart.y;
        eventRect.x=eventCol* gp.TileSize+eventRect.x;
        eventRect.y=eventRow* gp.TileSize+eventRect.y;
        if(gp.player.SolidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(requiredDirection)||requiredDirection.contentEquals("any")){
                hit=true;
                System.out.println("hi");
            }
        }
        gp.player.SolidPart.x=gp.player.DefaultSolidPartX;
        gp.player.SolidPart.y=gp.player.DefaultSolidPartY;
        eventRect.x=eventRectDefaultX;
        eventRect.y=eventRectDefaultY;
        return hit;
    }
    public void DamagePit(int gameState){
        gameState=44;
        gp.gameState=gameState ;
        gp.player.Life-=1;
        System.out.println(gp.player.Life);
    }
}
