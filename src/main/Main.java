package main;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args){
        JFrame window=new JFrame();//New Window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//This let the widow close when the user pressed the close button
        window.setResizable(false);//This not let the user expand the widow from any part (increase the width or height)
        window.setTitle("Adventure");//Title of our gamer
        GamePanel gamePanel=new GamePanel();//create an object from GamePanel Class
        gamePanel.SetUpGame();//Set objects on map as (key-door....etc)
        gamePanel.startGameThread();//This makes the program start and run
        window.add (gamePanel);//add the object to our window to appear on the desk
        window.pack();//Causes this Window to be sized to fit the preferred size and layouts of its subcomponents (=GamePanel).
        window.setVisible(true);//make our window appear on our desk
        window.setLocationRelativeTo(null);//make our window location in the middle
    }
}
