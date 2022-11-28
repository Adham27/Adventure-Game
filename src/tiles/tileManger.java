package tiles;

import main.GamePanel;
import main.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class tileManger {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public tileManger(GamePanel gp){
        this.gp=gp;
        tile=new Tile[10];
        mapTileNum=new int [gp.maxWorldCol][gp.maxWorldRow];//20,16
        getTileImage();
        loadMap("/res/maps/map01");
    }

    public void getTileImage() {

        //New Method
        setUp(0,"grass",false);
        setUp(1,"wall",true);
        setUp(2,"water",true);
        setUp(3,"earth",false);
        setUp(4,"tree",true);
        setUp(5,"sand",false);
//            Old Method
//            tile[0]=new Tile();
//            tile[0].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/grass.png")));
//
//            tile[1]=new Tile();
//            tile[1].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/wall.png")));
//            tile[1].collision=true;
//
//            tile[2]=new Tile();
//            tile[2].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/water.png")));
//            tile[2].collision=true;
//
//            tile[3]=new Tile();
//            tile[3].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/earth.png")));
//
//            tile[4]=new Tile();
//            tile[4].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/tree.png")));
//            tile[4].collision=true;
//
//            tile[5]=new Tile();
//            tile[5].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/sand.png")));
    }

    public void loadMap(String filePath){
        try{
            InputStream is=getClass().getResourceAsStream(filePath);
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            int col=0;
            int row=0;
            while (col<gp.maxWorldCol&&row< gp.maxWorldRow){
                String line= br.readLine();
                while (col< gp.maxWorldCol){
                    String[] Numbers =line.split(" ");
                    int num=Integer.parseInt(Numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col== gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error In Load Method");
            e.printStackTrace();
        }
    }
    public void setUp(int index,String imagePath,Boolean collision){
        Tools tool=new Tools();
        try{
            tile[index]=new Tile();
            tile[index].image=ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/"+imagePath+".png")));
            tile[index].image=tool.ScaledImage(tile[index].image, gp.TileSize,gp.TileSize);
            tile[index].collision=collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int WorldCol=0;
        int WorldRow=0;
        while(WorldCol< gp.maxWorldCol && WorldRow<gp.maxWorldRow){
            /**
             * Explanation
             * 1-WorldX,WorldY the Position of the Player on map
             * 2-ScreenXScreenY,where the tile will be drawn
             * Player will be always in the center of screen
             * ,so we need to offset this difference
             * The zero position 0,0 should be in the top left corner
             * but in our situation its open world game so 0,0 tile will be out of range of our window,
             * so we make var ScreenX,ScreenY this draw the tile in the right position
             * Example
             * so tile[0][0]
             * screenX=-500
             * screenY=-500
             *say we called function draw  [0][1]
             */
            int tileNum=mapTileNum[WorldCol][WorldRow];
            int worldX=WorldCol*gp.TileSize;
            int worldY=WorldRow*gp.TileSize;
            int screenX=worldX- gp.player.WorldX+gp.player.screenX;
            int screenY=worldY- gp.player.WorldY+gp.player.screenY;
            /** underDevelopment
            //           Stop the camera at the age
            //            if (gp.player.screenX>gp.player.WorldX){
            //                screenX=worldX;
            //            }if (gp.player.screenY>gp.player.WorldY){
            //                screenY=worldY;
            //            }
            //            int rightOffset=gp.screenWidth-gp.player.screenX;
            //            if (rightOffset>gp.screenWidth-gp.player.WorldX){
            //                screenX=gp.screenWidth-(gp.screenWidth-worldX);
            //            }

                    //draw next tile
                    //This if condition load the tiles around the player only without load the whole map*/

            if(     worldX+gp.TileSize>gp.player.WorldX-gp.player.screenX&&
                    worldX-gp.TileSize<gp.player.WorldX+gp.player.screenX&&
                    worldY+gp.TileSize>gp.player.WorldY-gp.player.screenY&&
                    worldY-gp.TileSize<gp.player.WorldY+gp.player.screenY ){
                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }
            WorldCol++;
            if (WorldCol== gp.maxWorldCol){
                WorldCol=0;
                WorldRow++;
            }
        }
    }
}