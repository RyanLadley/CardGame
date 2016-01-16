/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author Leulad
 */
public class LoadingScreen extends JLabel {
    int width, height;
    
    Font LoadingFont = new Font("Sans Serif", Font.BOLD, width/50);
    BufferedImage loading;
    
    public LoadingScreen(int screenWidth, int screenHeight){
        try{
            loading = ImageIO.read(new File("Resources/Skins/Loading.jpg"));
            width = screenWidth;
            height = screenHeight;
       }
       catch (IOException ex){
           System.out.println(ex.toString());
       }
        
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D gr = (Graphics2D)g;
        gr.setColor(Color.black);
        gr.fillRect(0,0,width,height);
        
        gr.drawImage(loading,0,0, null);
        
        gr.setColor(Color.white);
        gr.setFont(LoadingFont);
        gr.drawString("Loading...", 8*width/9, 98*height/100);
    }
}
