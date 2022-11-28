package main;

import entity.NPC_Oldman;
import monster.Monster;
import object.ChestObject;
import object.DoorObject;
import object.KeyObject;

public class ObjectSetter {
    GamePanel gp;

    public ObjectSetter(GamePanel gp) {
        this.gp=gp;
    }
    public void setObj(){
        gp.obj[0]=new DoorObject(gp);
        gp.obj[0].WorldX= gp.TileSize*10;
        gp.obj[0].WorldY= gp.TileSize*12;

        gp.obj[1]=new ChestObject(gp);
        gp.obj[1].WorldX= gp.TileSize*10;
        gp.obj[1].WorldY= gp.TileSize*8;

        gp.obj[2]=new KeyObject(gp);
        gp.obj[2].WorldX= gp.TileSize*23;
        gp.obj[2].WorldY= gp.TileSize*38;
    }
    public void setNPC(){
        gp.npc[0]=new NPC_Oldman(gp);
        gp.npc[0].WorldX= gp.TileSize*10;
        gp.npc[0].WorldY= gp.TileSize*11;
    }
    public void setMonster(){
        gp.mon[0]=new Monster(gp);
        gp.mon[0].WorldX= gp.TileSize*23;
        gp.mon[0].WorldY= gp.TileSize*36;

        gp.mon[1]=new Monster(gp);
        gp.mon[1].WorldX= gp.TileSize*23;
        gp.mon[1].WorldY= gp.TileSize*37;

        gp.mon[2]=new Monster(gp);
        gp.mon[2].WorldX= gp.TileSize*23;
        gp.mon[2].WorldY= gp.TileSize*38;
    }
}
