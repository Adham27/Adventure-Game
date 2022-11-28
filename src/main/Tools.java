package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tools {
    public BufferedImage ScaledImage(BufferedImage original,int width,int height){
        BufferedImage ScaledImaged=new BufferedImage(width,height,original.getType());
        Graphics2D g2=ScaledImaged.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();
        return ScaledImaged;
    }

}
