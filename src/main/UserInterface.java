package main;

import entity.Entity;
import object.HeartObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font arial_30;
    BufferedImage heart_full,heart_half,heart_blank;
    public boolean messageStatus=false;
    public String message="";
    public String CurrentDialogue="";
    public boolean GameFinished=false;
    int commendNum=0;
    public UserInterface(GamePanel gp){
        this.gp=gp;
        arial_30=new Font("Arial",Font.PLAIN,30);
//        KeyObject key=new KeyObject(gp);
  //      Image=key.image;
        //Create HUD obj
        Entity heart=new HeartObject(gp);
        heart_full=heart.image;
        heart_half=heart.image2;
        heart_blank=heart.image3;
    }
    public void draw(Graphics2D g2){
        this.g2=g2;
        g2.setColor(Color.WHITE);
        g2.setFont(arial_30);
        //Title State
        if (gp.gameState==gp.titleState){
            DrawTileScreen();
        }
        //play State
        if (gp.gameState==gp.playState){
            gp.music.Play();
            DrawPlayerLife();
        }
        //Pause State
        if (gp.gameState==gp.pauseState){
            DrawPauseScreen();
            DrawPlayerLife();
        }
        //Dialogue State
        if (gp.gameState== gp.dialogueState){
            DrawDialogue();
        }
        //GameOver State
        if (gp.gameState==gp.GameOver){
            GameOverScreen();
        }
        if (gp.UI.GameFinished){
            gp.stopMusic();
            DrawVectory();

        }
    }
    public int GetXCenterText(String Txt){
        int len=(int)g2.getFontMetrics().getStringBounds(Txt,g2).getWidth();
        int x=gp.screenWidth/2-len/2;
        return x;
    }
    public void DrawTileScreen(){
        g2.setColor(new Color(70,120,80));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,62F));
        String text="Adventure";
        int x=GetXCenterText(text);
        int y=gp.TileSize*4;
        //TextShadow
        g2.setColor(Color.black);
        g2.drawString(text,x+5,y+5);
        //TileText
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
        //ourHero
        x=gp.screenWidth/2-(gp.TileSize*2)/2;
        y+=gp.TileSize*3;
        g2.drawImage(gp.player.down1,x,y,gp.TileSize*2,gp.TileSize*2,null);
        //Menu Options
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC,24F));
        text="New Game";
        x=GetXCenterText(text);
        y+=gp.TileSize*4;
        g2.drawString(text,x,y);
        if(commendNum==0){
            g2.drawString(">",x-gp.TileSize,y);
        }
        //UnderDevelop
//        text="Load Game";
//        x=GetXCenterText(text);
//        y+=gp.TileSize;
//        g2.drawString(text,x,y);
//        if(commendNum==1){
//            g2.drawString(">",x-gp.TileSize,y);
//        }
        text="QUIT";
        x=GetXCenterText(text);
        y+=gp.TileSize;
        g2.drawString(text,x,y);
        if(commendNum==2){
            g2.drawString(">",x-gp.TileSize,y);
        }

    }
    public void DrawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
       String text="PAUSED";
       int x=GetXCenterText(text);
       int y=gp.screenHeight/2;
       g2.drawString(text,x,y);
       gp.music.stop();

    }
    public void DrawPlayerLife(){
        int x=gp.TileSize/2;
        int y=gp.TileSize/2;
        int i=0;

        //Draw Max Life
        while(i<gp.player.MAXHealth/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+=gp.TileSize;

        }
        //Reset
         x=gp.TileSize/2;
         y=gp.TileSize/2;
         i=0;
         //Draw Current Player health
        while (i<gp.player.Life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.Life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+= gp.TileSize;
        }
    }
    public void DrawDialogue(){
        //Window
        int x=gp.TileSize*2;
        int y=gp.TileSize/2;
        int width=gp.screenWidth-(gp.TileSize*4);
        int height=gp.TileSize*4;

        drawSubWindow(x,y,width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32f));
        x+=gp.TileSize;
        y+= gp.TileSize;
        g2.drawString(CurrentDialogue,x,y);
    }
    public void drawSubWindow(int x,int y, int width, int height){
        Color c=new Color(0,0,0,0.5f);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c=new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }
    public void GameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        int x;
        int y;
        String txt;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,90f));
        txt="Game Over";
        //shadow
        g2.setColor(Color.black);
        x= GetXCenterText(txt);
        y=gp.TileSize*4;
        g2.drawString(txt,x,y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(txt,x,y);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        txt="Retry";
        x=GetXCenterText(txt);
        y+= gp.TileSize*4;
        g2.drawString(txt,x,y);
        if (commendNum==0){
            g2.drawString(">",x-40,y);
        }

        //Quit
        txt="Quit";
        x=GetXCenterText(txt);
        y+=55;
        g2.drawString(txt,x,y);
        if (commendNum==1){
            g2.drawString(">",x-40,y);
        }
    }
    public void DrawVectory(){
        g2.setFont(arial_30);
        g2.setColor(Color.yellow);
        String txt;
        int txtLen;
        int x;
        int y;
        txt="You Found The treasure";
        txtLen=(int)g2.getFontMetrics().getStringBounds(txt,g2).getWidth();
        x=gp.screenWidth/2-txtLen/2;
        y=gp.screenHeight/2-(gp.TileSize*3);

        g2.drawString(txt,x,y);
        g2.setFont(arial_30);
        g2.setColor(Color.yellow);
        txt="You Found The treasure";
        txtLen=(int)g2.getFontMetrics().getStringBounds(txt,g2).getWidth();
        x=gp.screenWidth/2-txtLen/2;
        y=gp.screenHeight/2-(gp.TileSize*3);
        g2.drawString(txt,x,y);


    }

}
