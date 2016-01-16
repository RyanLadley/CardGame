/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Leulad
 */
public class MenuResources {
    private BufferedImage lightsaber, background, logo, loading;
    
    //This methods prepares all Images including all cards and backgrounds
    public MenuResources(){
        try{
            lightsaber = ImageIO.read(new File("Resources/Skins/DarkThumb.jpg"));
            background = ImageIO.read(new File("Resources/Skins/Background.jpg"));
            logo = ImageIO.read(new File("Resources/Skins/StarWars.jpg"));
            loading = ImageIO.read(new File("Resources/Skins/Loading.jpg"));
            
        }
        catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
    
    public BufferedImage getBackground(){
        return background;
    }
    
    public BufferedImage getLogo(){
        return logo;
    }
    
    public BufferedImage getSaber(){
        return lightsaber;
    }
    
    public BufferedImage getLoading(){
        return loading;
    }
}
