/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Button {
    BufferedImage[] buttonImage;
    
    boolean display;
    int X, Y, holdY, width, height;
    
    public Button(BufferedImage image, int x,int y,int width,int height){
        buttonImage = new BufferedImage[1];
        buttonImage[0] = image;
        X = x;
        Y = y;
        this.width = width;
        this.height = height;
        display = true;
    }
    public Button(BufferedImage image, int x,int y,int width,int height, boolean display){
        buttonImage = new BufferedImage[1];
        buttonImage[0] = image;
        X = x;
        Y = y;
        this.width = width;
        this.height = height;
        this.display = display;
    }
    
    public Button(BufferedImage image1, BufferedImage image2, int x,int y,int width,int height){
        buttonImage = new BufferedImage[2];
        buttonImage[0] = image1;
        buttonImage[1] = image2;
        X = x;
        Y = y;
        this.width = width;
        this.height = height;
        display = true;
    }
    public Button(BufferedImage image1, BufferedImage image2, int x,int y,int width,int height, boolean display){
        buttonImage = new BufferedImage[2];
        buttonImage[0] = image1;
        buttonImage[1] = image2;
        X = x;
        Y = y;
        this.width = width;
        this.height = height;
        this.display = display;
    }
    
    public boolean mouseIntersects(Point position){
        if(position.getX() > X &&position.getX() < X + width
           &&position.getY() > Y&&position.getY() < Y + height){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void draw(Graphics2D gr){
       if(display){
           gr.drawImage(buttonImage[0] ,X, Y, width, height,null);
       }
    }
    
    public void draw(Graphics2D gr, boolean picSelect){
        if(display){
            if(picSelect == true && buttonImage.length == 2){
                gr.drawImage(buttonImage[1] ,X, Y, width, height,null);  
            }
            else{
               gr.drawImage(buttonImage[0] ,X, Y, width, height,null);
            }
        }
    }
    
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void changeLocation(int newX, int newY, int newWidth, int newHeight){
        setX(newX);
        setY(newY);
        setWidth(newWidth);
        setHeight(newHeight);
    }
    public void setX(int newX){
        X = newX;
    }
    public void setY(int newY){
        Y = newY;
    }
    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }
    
    public void display(boolean select){
        display = select;
    }
    
    public boolean isDisplayed(){
        return display;
    }
}
