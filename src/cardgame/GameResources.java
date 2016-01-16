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
public class GameResources {
    
    private BufferedImage table, flippedTable, darkDeck, darkThumb, lightThumb, lightDeck, 
                            minus, plus, menuIcon, lightning, shuffle, upArrow, downArrow,
                          bin, leftArrow, rightArrow;
    
    
    
    //This methods prepares all Images including all cards and backgrounds
    public GameResources(){
        try{
            table = ImageIO.read(new File("Resources/Skins/Table.jpg"));
            flippedTable = ImageIO.read(new File("Resources/Skins/FlippedTable.jpg"));
            darkDeck = ImageIO.read(new File("Resources/Skins/DarkDeck.jpg"));
            lightDeck = ImageIO.read(new File("Resources/Skins/LightDeck.jpg"));
            darkThumb = ImageIO.read(new File("Resources/Skins/DarkThumb.jpg"));
            lightThumb = ImageIO.read(new File("Resources/Skins/LightThumb.jpg"));
            minus = ImageIO.read(new File("Resources/Skins/Minus.png"));
            plus = ImageIO.read(new File("Resources/Skins/Plus.png"));
            menuIcon = ImageIO.read(new File("Resources/Skins/MenuIcon.png"));
            lightning = ImageIO.read(new File("Resources/Skins/Lightning.png"));
            shuffle = ImageIO.read(new File("Resources/Skins/Shuffle.png"));
            upArrow = ImageIO.read(new File("Resources/Skins/UpArrow.png"));
            downArrow = ImageIO.read(new File("Resources/Skins/DownArrow.png"));
            bin = ImageIO.read(new File("Resources/Skins/Bin.png"));
            leftArrow = ImageIO.read(new File("Resources/Skins/LeftArrow.png"));
            rightArrow = ImageIO.read(new File("Resources/Skins/RightArrow.png"));
        }
        catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
    
    public BufferedImage getTable(){
        return table;
    }
    
    public BufferedImage getFlippedTable(){
        return flippedTable;
    }
    
    public BufferedImage getDarkDeck(){
        return darkDeck;
    }
    
    public BufferedImage getLightDeck(){
        return lightDeck;
    }
    
    public BufferedImage getDarkThumb(){
        return darkThumb;
    }
    
    public BufferedImage getLightThumb(){
        return lightThumb;
    }
    
    public BufferedImage getMinus(){
        return minus;
    }
    
    public BufferedImage getPlus(){
        return plus;
    }
    
    public BufferedImage getMenuIcon(){
        return menuIcon;
    }
    
    public BufferedImage getLightning(){
        return lightning;
    }
    
    public BufferedImage getShuffle(){
        return shuffle;
    }
    
    public BufferedImage getUpArrow(){
        return upArrow;
    }
    
    public BufferedImage getDownArrow(){
        return downArrow;
    }
    
    public BufferedImage getBin(){
        return bin;
    }
    
    public BufferedImage getLeftArrow(){
        return leftArrow;
    }
    
    public BufferedImage getRightArrow(){
        return rightArrow;
    }
}
