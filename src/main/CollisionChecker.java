package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    CollisionChecker(GamePanel gp){
        this.gp=gp;
    }
    //We called Entity class because we will test/check every object in the world as(NPC,Monsters,players)
    public void CheckTile(Entity entity){
        int SolidLeftPartX=entity.WorldX+entity.SolidArea.x;
        int SolidRightPartX=entity.WorldX+entity.SolidArea.x+entity.SolidArea.width;
        int SolidTopPartY=entity.WorldY+(entity.SolidArea.y);
        int SolidBottomPartY=(entity.WorldY+entity.SolidArea.y+entity.SolidArea.height)-16;
        //Row&Col
        int LeftCol=SolidLeftPartX/ gp.TileSize;
        int RightCol=SolidRightPartX/ gp.TileSize;
        int TopRow=SolidTopPartY/ gp.TileSize;
        int BottomRow=SolidBottomPartY/ gp.TileSize;

        int TileNum1,TileNum2;

        switch (entity.direction){
            case "up":
                TopRow=(SolidTopPartY-entity.speed)/ gp.TileSize;//predict where the collision may happen
                TileNum1=gp.tileM.mapTileNum[LeftCol][TopRow];
                TileNum2= gp.tileM.mapTileNum[RightCol][TopRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision){
                    entity.collisionStatus=true;
                }
                break;
            case "down":
                BottomRow=(SolidTopPartY+entity.speed)/ gp.TileSize;//predict where the collision may happen
                TileNum1=gp.tileM.mapTileNum[LeftCol][BottomRow];
                TileNum2= gp.tileM.mapTileNum[RightCol][BottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision){
                    entity.collisionStatus=true;}
                break;

            case "left":
                LeftCol=(SolidLeftPartX-entity.speed)/ gp.TileSize;//predict where the collision may happen
                TileNum1=gp.tileM.mapTileNum[LeftCol][TopRow];
                TileNum2= gp.tileM.mapTileNum[LeftCol][BottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision){
                    entity.collisionStatus=true;}
                break;
            case "right":
                RightCol=(SolidRightPartX+entity.speed)/ gp.TileSize;//predict where the collision may happen
                TileNum1=gp.tileM.mapTileNum[RightCol][TopRow];
                TileNum2= gp.tileM.mapTileNum[RightCol][BottomRow];
                if (gp.tileM.tile[TileNum1].collision || gp.tileM.tile[TileNum2].collision){
                    entity.collisionStatus=true;}
                break;
        }

    }
    /**
     * The difference between two functions that if we need to scan all the tiles around the player and check
     * if it makes collision so second check for the object array only
     * */
    public int CheckObj(Entity entity,boolean player){
        int index=999;
        for(int i=0;i<gp.obj.length;i++){
            if (gp.obj[i]!=null){
                //Get entity's solid area position
                entity.SolidArea.x=entity.WorldX+entity.SolidArea.x;
                entity.SolidArea.y=entity.WorldY+entity.SolidArea.y;
                //Get the objects solid area position
                gp.obj[i].SolidPart.x=gp.obj[i].WorldX+gp.obj[i].SolidPart.x;
                gp.obj[i].SolidPart.y=gp.obj[i].WorldY+gp.obj[i].SolidPart.y;
                switch (entity.direction){
                    case "up":
                        entity.SolidArea.y-=entity.speed;
                        break;
                    case "down":
                        entity.SolidArea.y+=entity.speed;
                        break;
                    case "left":
                        entity.SolidArea.x-=entity.speed;
                        break;
                    case "right":
                        entity.SolidArea.x+=entity.speed;
                        break;
                }
                if (entity.SolidArea.intersects(gp.obj[i].SolidPart)){
                    if (gp.obj[i].collision){
                        entity.collisionStatus=true;
                    }
                    if (player){
                        index =i;
                    }
                }
                entity.SolidArea.x=entity.DefaultSolidPartX;
                entity.SolidArea.y=entity.DefaultSolidPartY;
                gp.obj[i].SolidPart.x=  gp.obj[i].DefaultSolidPartX;
                gp.obj[i].SolidPart.y=  gp.obj[i].DefaultSolidPartY;
            }
        }
        return index;
    }
    //NPC OR Monster Collision
    public int CheckEntity(Entity entity,Entity[] target){
        int index=999;
        for(int i=0;i<target.length;i++){
            if (target[i]!=null){
                //Get entity's solid area position
                entity.SolidArea.x=entity.WorldX+entity.SolidArea.x;
                entity.SolidArea.y=entity.WorldY+entity.SolidArea.y;
                //Get the objects solid area position
                target[i].SolidPart.x=target[i].WorldX+target[i].SolidPart.x;
                target[i].SolidPart.y=target[i].WorldY+target[i].SolidPart.y;
                switch (entity.direction){
                    case "up":
                        entity.SolidArea.y-=entity.speed;
                        break;
                    case "down":
                        entity.SolidArea.y+=entity.speed;
                        break;
                    case "left":
                        entity.SolidArea.x-=entity.speed;
                        break;
                    case "right":
                        entity.SolidArea.x+=entity.speed;
                        break;
                }
                //not include the entity in its self
                if (entity.SolidArea.intersects(target[i].SolidPart)){
                    if (target[i]!=entity) {
                        entity.collisionStatus = true;
                        index = i;
                    }
                }
                entity.SolidArea.x=entity.DefaultSolidPartX;
                entity.SolidArea.y=entity.DefaultSolidPartY;
                target[i].SolidPart.x=  target[i].DefaultSolidPartX;
                target[i].SolidPart.y=  target[i].DefaultSolidPartY;
            }
        }
        return index;
    }

    public Boolean checkPlayer(Entity entity){
        boolean contactPlayer=false;
        //Get entity's solid area position
        entity.SolidArea.x=entity.WorldX+entity.SolidArea.x;
        entity.SolidArea.y=entity.WorldY+entity.SolidArea.y;
        //Get the objects solid area position
        gp.player.SolidPart.x=gp.player.WorldX+gp.player.SolidPart.x;
        gp.player.SolidPart.y=gp.player.WorldY+gp.player.SolidPart.y;
        switch (entity.direction){
            case "up":
                entity.SolidArea.y-=entity.speed;
                break;
            case "down":
                entity.SolidArea.y+=entity.speed;
                break;
            case "left":
                entity.SolidArea.x-=entity.speed;
                break;
            case "right":
                entity.SolidArea.x+=entity.speed;

                break;
        }
        if (entity.SolidArea.intersects(gp.player.SolidPart)){
            entity.collisionStatus=true;
            contactPlayer=true;
        }
        entity.SolidArea.x=entity.DefaultSolidPartX;
        entity.SolidArea.y=entity.DefaultSolidPartY;
        gp.player.SolidPart.x=  gp.player.DefaultSolidPartX;
        gp.player.SolidPart.y=  gp.player.DefaultSolidPartY;
        return contactPlayer;
    }
}
