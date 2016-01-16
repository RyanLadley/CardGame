package cardgame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Card {
    
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenWidth = screenSize.width, 
                       screenHeight = screenSize.height;
    
    static int defaultWidth = screenWidth /12, defaultHeight = screenHeight/5;
    BufferedImage CardImage;
    
    int x, y, holdY, width, height, rotationAngle = 0, imageNumber;
    boolean inPlay, rotate = false, darkSide, inHand = true, hideHand = false , discarded = false;
    
    
    public Card(BufferedImage image, int x,int y,int width,int height, boolean Inplay, boolean Dark, int Image){
        CardImage = image;
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
        inPlay = Inplay;
        darkSide = Dark;
        imageNumber = Image;
    }
    public Card(){
        //Only Used in Load
    }
    
    public static void changeScreenSize(int newWidth, int newHeight){
        screenWidth = newWidth;
        screenHeight = newHeight;
        
        defaultWidth = newWidth /12;
        defaultHeight = newHeight/5;
    }
    public BufferedImage getImage(){
        return CardImage;
    }
    
    public void changeImage(BufferedImage NewImage){
        CardImage = NewImage;
    }
    
    public void setImageNumber(int Number){
        imageNumber = Number;
    }
    
    public int getImageNumber(){
        return imageNumber;
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int NewX){
         x= NewX;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int NewY){
         y= NewY;
    }
    
    public int getWidth(){
        return width;
    }
    
    public void expandWidth(){
        if(width*2 <= defaultWidth*2){
           width = 5*width/2;
        }
    }
    
    public void returnDefaultWidth(){
        width = defaultWidth;
    }
    public void changeWidthBy(int n, int m){
        width = n*width/m;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void expandHeight(){
        if(height*2 <= defaultHeight*2){
            holdY = y;
            y -= 2*height/3;
            height= 5*height/2;
            if(y+height > screenHeight){
                y = screenHeight - height-10;
            }
        }
    }
    
    public void returnDefaultHeight(){
        if(height != defaultHeight){
            y=holdY;
            height = defaultHeight;
        }
    }
    public void trueDefaultHeight(){
        height = defaultHeight;
    }
    public void changeHeightBy(int n, int m){
        height = n*height/m;
    }
    
    public void enlargeCard(){
        expandWidth();
        expandHeight();
    }
    
    public void returnDefaultSize(){
        returnDefaultHeight();
        returnDefaultWidth();
    }
    
    public boolean isInPlay(){
        return inPlay;
    }
    
    public void changeInPlay(boolean Status){
        inPlay = Status;
    }
    
    public boolean isRotated(){
        return rotate;
    }
    
    public void rotateCard(boolean Answer){
        rotate = Answer;
    }
    
    public int getRotation(){
        return rotationAngle;
    }
    
    public void setRotationAngle(int Angle){
        rotationAngle = Angle;
    }
    
    public void increaseRotation(){
        if(rotationAngle == 270){
            rotationAngle = 0;
        }
        else{
            rotationAngle += 90;
        }
    }
    
    public void changeDarkSide(boolean Value){
        darkSide = Value;
    }
    
    public boolean isDarkSide(){
        return darkSide;
    }
    
    public boolean isInHand(){
        return inHand;
    }
    
    public void changeHandStatus(boolean Status){
        inHand = Status;
    }
    
    public boolean isHandHidden(){
        return hideHand;
    }
    
    public boolean isDiscarded(){
        return discarded;
    }
    
    public void changeDiscarded(boolean Status){
        discarded = Status;
    }
    
    public void changeHiddenHand(boolean Status){
        hideHand = Status;
    }
    //This Method preforms checks if point, notably a mouse point, is over card 
    //Returns True If it does
    public boolean isIntsersecting(Point mousePoint){
        if(  mousePoint.getX() >= x &&mousePoint.getX() <= x+width
           &&mousePoint.getY() >= y &&mousePoint.getY() <=y+height){
           
           return true;
        }
        else{
            return false;
        }
    }
    
    public String saveProperties(){
        return x +"\n"+ y + "\n"+ rotationAngle +"\n"+ imageNumber+"\n"+inPlay+"\n"+rotate +"\n"+ darkSide+"\n"+ inHand +"\n"+ hideHand;
        
    }
}
