package main;
import entity.Entity;
import entity.Player;
import tiles.tileManger;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    //Display the game frame
    public final int TileSize =32;// Any object in the game size will be=32x32 ,but it will be very small .so it will be scaled.
    final int scale=4;
  //  final int totalSize=TileSize*scale;//48x48
    public final int maxScreenCol=20;
    public final int maxScreenRow=16;
    public final int screenWidth=(TileSize*maxScreenCol)-100;//640 Pixels
    public final int screenHeight=(TileSize*maxScreenRow)-60;//512 Pixels

    //__System__
    public tileManger tileM=new tileManger(this);
    public Sound music=new Sound();//Handle the music sound
    public Sound SE=new Sound();
    public KeyHandler keyH=new KeyHandler(this);
    public CollisionChecker Checker=new CollisionChecker(this);
    public ObjectSetter OSetter=new ObjectSetter(this);
    public UserInterface UI=new UserInterface(this);
    public EventHandler EH=new EventHandler(this);
    public Player player=new Player(this,keyH);



    Thread gameThread;// create an object from the class (Thread) and to use this class we should implement runnable method to define run method to use the thread once we created the object from the thread class run method will be called
         //--Entity and Object--
         ArrayList<Entity>entities=new ArrayList<>();
        public Entity[] obj=new Entity[10];
        public Entity[] npc=new Entity[10];
        public Entity[] mon=new Entity[10];
    //this mean that we can display up to 10 object at the sane time


    //We sort the order of the array so the entity that has the lowest WorldY comes in index 0;
    //Game State //to take action according to this game stata as maybe there is one key do 2 different action

    public int gameState;
    public final int titleState=0;//Main Screen
    public final int playState=1;//PlayMood Screen
    public final int pauseState=2;//When the screen stop
    public final int dialogueState=3;//NPC
    public final int GameOver=4;//GameOver Screen

    //__World SETTINGS__//
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;

    //Frame Per Second (FPS) this is mean that you update the screen 60 times per second
    int FPS=60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));//set size of our screen
        this.setBackground(Color.black);//main color to our screen
        this.setDoubleBuffered(true);//if set to true ,all drawing from this component will be done in an offscreen it used for better rendering performance
        this.addKeyListener(keyH);//method to get the key events and take inputs from the user
        this.setFocusable(true);
    }
    //Create this method ,so we can add other setup stuff in the future
    public void SetUpGame(){
        OSetter.setObj();
        OSetter.setNPC();
        OSetter.setMonster();
        gameState=titleState;
    }
    //to Make our Program run without stop
    public void startGameThread (){
        gameThread=new Thread(this);
        gameThread.start();
    }
    //Function to play Music
    public void playMusic(int i){
        music.setFile(i);
        music.Play();
        music.Loop();
    }
    //Function to stop Music
    public void stopMusic(){
        music.stop();
    }
    //sound Effect
    public void PlaySE(int i){
        SE.setFile(i);
        SE.Play();
    }
    ///  Game core
    @Override
    public void run() {
        /*
            ----------//Sleep Method//-----------------
            double drawInterval =10000000000/FBS;
            double nextDrawTile=System.nanoTime()+drawInterval;

                while(gameThread !=null){
                    update();
                    reprint();
                }
                try{
                     double remaining =nextDrawTime-System.nanoTime()
                     remainingTime=remainingTime/1000000;
                     if(remainingTime<0){
                        remainingTime=0;
                     }
                     Thread.sleep((long) remainingTime));
                     nextDraw+=drawInterval;
                 }catch(IOException e) {
                     e.printStackTrace();
                 }

           ----------------------------------------------------
            * */
        //delta method//
        double nanoTime=1000000000;
        double nano=1000000000;//nano Time
        double drawInterval=nano/FPS;//1000000000/60
        double delta = 0;
        long lastTime=System.nanoTime();//Time of Machine
        long currentTime;
        long timer=0;
        int drawCount=0;
        // this loop aim to repeat the process written between this bracket
        while(gameThread!=null){
//            /*Time :System.nanoTime() Return the current value of running java virtual
//            Machine high-resolution time source in nanoSec as 1,000,000,000 NanoSec=1 Sec*/
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            timer+=(currentTime-lastTime);
            lastTime=currentTime;
            if (delta>=1){
//                //1-Update the information's :Update the information of character position
                update();
                //2-Update the Screen:Draw the screen with updated information
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=nanoTime){
                timer=0;
                drawCount=0;
            }
        }
    }
    ///Notes In java the upper left corner is X:0,Y:0 so Y increase by going down and x increase by going right
    public void update(){
        if (gameState==playState){
            //Player
            player.update();
            //NPC
            for (int i=0;i<npc.length;i++){
                if (npc[i]!=null){
                    npc[i].update();
                }
            }
            //Monsters
            for (int i=0;i<mon.length;i++){
                if (mon[i]!=null){
                    mon[i].update();
                    if (mon[i].Life<=0){
                        mon[i]=null;
                    }
                }
            }

         }
    }
    public void paintComponent(Graphics g){
        //format when you use JPanel you should write this as GamePanel is a subclass od JPanel
        super.paintComponent(g);
        //Change the graphics from 1D to 2D
        Graphics2D g2=(Graphics2D)g;//Graphics2D class extends from Graphics to provide more control on geometry,coordinates transformations ,color management and text layout
        long drawStart=0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        //Title Screen
        if (gameState==titleState){
            UI.draw(g2);

        }else{
            tileM.draw(g2);//Draw Tile
//            player.draw(g2);
//            for(int i=0;i<obj.length;i++){
//                if(obj[i]!=null){
//                    obj[i].draw(g2);
//                }
//            }
//            for(int i=0;i<npc.length;i++){
//                if(npc[i]!=null){
//                    npc[i].draw(g2);
//                }
//            }
//            for(int i=0;i<mon.length;i++){
//                if(mon[i]!=null){
//                    mon[i].draw(g2);
//                }
//            }
            entities.add(player);
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                   entities.add(npc[i]);
                }
            }
            for(int i=0;i<obj.length;i++){
                if(obj[i]!=null){
                    entities.add(obj[i]);
                }
            }
            for(int i=0;i<mon.length;i++){
                if(mon[i]!=null){
                    entities.add(mon[i]);
                }
            }
            Collections.sort(entities, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result=Integer.compare(e1.WorldY,e2.WorldY);
                    return result;
                }
            });
            for (int i=0;i<entities.size();i++){
                entities.get(i).draw(g2);
            }
                entities.clear();

            UI.draw(g2);
            //Debug
            if (keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.WHITE);
                g2.drawString("Draw Time :" + passed, 10, 400);
            }
            g2.dispose();
        }

    }


}
