package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,EnteredPressed;
    public boolean checkDrawTime=false;
    public KeyHandler(GamePanel gp){
        this.gp=gp;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        //Title State
        if (gp.gameState== gp.titleState){
            if (code==KeyEvent.VK_UP||code==KeyEvent.VK_W){
                gp.UI.commendNum--;
                if (gp.UI.commendNum<0){
                    gp.UI.commendNum=2;
                }
            }
            if (code==KeyEvent.VK_DOWN||code==KeyEvent.VK_S){
                gp.UI.commendNum++;
                if (gp.UI.commendNum==2){
                    gp.UI.commendNum=0;
                }
            }
            if(code==KeyEvent.VK_ENTER){
                if(gp.UI.commendNum==0){
                    gp.gameState= gp.playState;
                    gp.playMusic(5);
                }
                if(gp.UI.commendNum==1){
                    //add later
                    gp.playMusic(5);
                }
                if(gp.UI.commendNum==2){
                    System.exit(0);
                }
            }
        }

        if(gp.gameState== gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code==KeyEvent.VK_ENTER){
                EnteredPressed=true;
            }

        }
        if (gp.gameState==gp.pauseState){
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
            }
        }
        if(gp.gameState== gp.dialogueState){
            if (code==KeyEvent.VK_ENTER){
                gp.gameState= gp.playState;
            }
        }
        if (gp.gameState==gp.GameOver){
            GameOver(code);

        }
            //DEBUG
            else if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }


    }
    public void GameOver(int code){
        if (code==KeyEvent.VK_UP||code==KeyEvent.VK_W){
            gp.UI.commendNum--;
            if (gp.UI.commendNum<0){
                gp.UI.commendNum=1;
            }
        }
        if (code==KeyEvent.VK_DOWN||code==KeyEvent.VK_S){
            gp.UI.commendNum++;
            if (gp.UI.commendNum>0){
                gp.UI.commendNum=2;
            }

    }
        if (code==KeyEvent.VK_ENTER){
            if (gp.UI.commendNum==0){
                gp.gameState= gp.playState;
            }
            else if (gp.UI.commendNum==1){
                System.exit(0);
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code=e.getKeyCode();
        if (code==KeyEvent.VK_W){
            upPressed=false;
        }
        if (code==KeyEvent.VK_S){
            downPressed=false;
        }
        if (code==KeyEvent.VK_A){
            leftPressed=false;
        }
        if (code==KeyEvent.VK_D){
            rightPressed=false;
        }
        if (code==KeyEvent.VK_ENTER){
            EnteredPressed=false;
        }

    }
}
