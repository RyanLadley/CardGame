/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class CardFactory {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenSize.width, 
                height = screenSize.height;
    
    
    private int totalActive = 0,
                deckX = 9*width/10, 
                deckY = 780*height/1000,
                deckSize = 10;
    
    private CardDeck lightDeck, darkDeck;
    
    private BufferedImage[] lightCardImages = new BufferedImage[deckSize], darkCardImages = new BufferedImage[deckSize];
    private Card[] activeCards;
    
    
    private static CardFactory instance;
    private CardFactory(){
        initiateDecks();
    }
    
    public static CardFactory getInstance(){
        if(instance == null){
            instance = new CardFactory();
        }
        return instance;
    }
    
    public void reset(){
        getCardImages();
        initiateDecks();
    }
    
    public void changeScreenSize(int newWidth, int newHeight){
        if(width != newWidth && height != newHeight){
            width = newWidth; 
            height = newHeight;
            lightDeck.changeScreenSize(newWidth, newHeight);
            darkDeck.changeScreenSize(newWidth, newHeight);
            for(int n = 0; n<activeCards.length; n++){
                activeCards[n].returnDefaultSize();
            }
        }
    }
    
    private void getCardImages(){
        try{
            
            /*AffineTransform tx = new AffineTransform();
            tx.scale(2.2, 2.2);

            AffineTransformOp op = new AffineTransformOp(tx,
            AffineTransformOp.TYPE_BILINEAR);
            */
            
            for(int n = 0; n<lightCardImages.length;n++){
                lightCardImages[n] = ImageIO.read(new File("Resources/Cards/LightCards/Light" +n +".jpg"));
                //lightCardImages[n] = op.filter(lightCardImages[n], null);
            }
            for(int n = 0; n<lightCardImages.length;n++){
                darkCardImages[n] = ImageIO.read(new File("Resources/Cards/DarkCards/Dark" +n +".jpg"));
                //darkCardImages[n] = op.filter(darkCardImages[n], null);
            }
        }
        catch (IOException ex){
            System.out.println(ex.toString());
        }
    }
    //This method creates an Array of LightCards that is used in Game
    //Implemented in GameSetup.beginNewGame();
    private void initiateDecks(){
        Card[] LightCards = new Card[deckSize]; 
        Card[] DarkCards = new Card[deckSize];
        
        activeCards = new Card[2*deckSize]; //Length equals both light and dark decks added together
        
        for(int n = 0; n < LightCards.length; n++){
            LightCards[n] = new Card(lightCardImages[n], deckX-width/10, deckY, width/12, height/5, false,false, n);
        }
        for(int n = 0; n < DarkCards.length; n++){
            DarkCards[n] = new Card(darkCardImages[n], deckX-width/10, deckY, width/12, height/5, false,true, n);
        }
        
        lightDeck = new CardDeck(LightCards, true);
        darkDeck = new CardDeck(DarkCards, true);
        
    }
    
    public boolean dealCard(boolean isDarkSide){
            if(isDarkSide){
                if(darkDeck.isEmpty()){//Check to avoid exception
                    return false;
                }
                else{//Deal DarkSide
                    activeCards[totalActive] = darkDeck.dequeue();
                    activeCards[totalActive].changeInPlay(true);
                    moveCardToFront(totalActive++);
                    if(darkDeck.isEmpty()){//
                        return false;
                    }
                    return true;
                }
            }
            else{//Deal Light Side
                if(lightDeck.isEmpty()){
                    return false;
                }
                else{
                    activeCards[totalActive] = lightDeck.dequeue();
                    activeCards[totalActive].changeInPlay(true);
                    moveCardToFront(totalActive++);
                    if(lightDeck.isEmpty()){
                        return false;
                    }
                    return true;
                }
            } 
        
    }
    
   //All Cards Moves by deltaX if they are not in the hand or discarded
    public void moveTableX(int deltaX){
       for(int n = 0; n<activeCards.length; n++){

            if(activeCards[n].isInPlay() == true 
               &&activeCards[n].isInHand() == false
               &&activeCards[n].isDiscarded() == false){

                //Card Moves here if it is not in the hand or discarded
                activeCards[n].setX(activeCards[n].getX() +deltaX);

            }
        }
    }
   
   //All Cards Moves by deltaY if they are not in the hand or discarded
    public void moveTableY(int deltaY){
       for(int n = 0; n<activeCards.length; n++){

            if(activeCards[n].isInPlay() == true 
               &&activeCards[n].isInHand() == false
               &&activeCards[n].isDiscarded() == false){

                //Card Moves here if it is not in the hand or discarded
                activeCards[n].setY(activeCards[n].getY() +deltaY);

            }
        }
    }
    //This Method activates after a left Mouse Click and check if the mouse and card
    //Match Up. If they do, the (center of the) card is moved to mouse location
    //If a card is placed in hand its InHand Variable changes to true.
    //Scrolling does not change inhand varaible
    public boolean moveCard(Boolean cardMoving, Point mousePos, int bottomHeight, 
                  int discardX, int discardY, int discardWidth, int discardHeight, boolean showDiscard){
        try{//getMousePosition() Can sometimes return Null
            if(cardMoving == false){
                for(int n = 0; n<activeCards.length; n++){
                    if(activeCards[n].isIntsersecting(mousePos)){
                        // Check if the card is hidden behind the hand, therefore inaccesable 
                        if(activeCards[n].getY()+3*activeCards[n].getHeight()/4 > bottomHeight && 
                           activeCards[n].isInHand() == false &&
                           !(mousePos.getY() < activeCards[n].getY()+bottomHeight-activeCards[n].getY())
                            //or if Card is hidden in the discard pile, aslso inaccesable
                            ||(activeCards[n].isDiscarded() == true && showDiscard == false)){ 
                                /////////////////////////////////////////////////
                                //Do Nothing: The player cannot reach the cards//
                                /////////////////////////////////////////////////
                        }
                        else{//This Moves the Card if conditions are met
                            if(activeCards[n].isHandHidden() == false){//If t
                                    if(n != 0){
                                        moveCardToFront(n);
                                    }
                                    cardMoving = true;
                                    activeCards[0].returnDefaultSize(); 
                                    break;
                            }
                        }
                    }
                }
            }
            else{
                activeCards[0].setX((int)mousePos.getX() -activeCards[0].getWidth()/2);
                activeCards[0].setY((int)mousePos.getY() -activeCards[0].getHeight()/2);
                activeCards[0].changeHandStatus(inHand(0, bottomHeight));
                activeCards[0].changeDiscarded(discardCard(0, discardX, discardY, discardWidth,discardHeight,showDiscard));
            }
            return cardMoving;
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
            return cardMoving;
        } 
    }
    
    public void switchHands(){
        for(int n = 0; n< activeCards.length; n++){
            //Hand Statuses
            if(activeCards[n].isInHand() == true){
                if(activeCards[n].isHandHidden() == true){
                    activeCards[n].changeHiddenHand(false);
                }
                else{
                   activeCards[n].changeHiddenHand(true); 
                }
            }
            else{
                //Discard Statuses
                if(activeCards[n].isDiscarded() == true){
                    if(activeCards[n].isHandHidden() == true){
                        activeCards[n].changeHiddenHand(false);
                    }
                    else{
                        activeCards[n].changeHiddenHand(true);
                    }    
                }
                else{
                    activeCards[n].changeHiddenHand(false);
                }
            }
        }
    }
    //This Method Checks For Right Mouse Click and Card Match Up to enlarge the card
    public boolean enlargeCard(boolean rightMousePressed, boolean enlarge, Point mousePos){
       try{
            for(int n = 0; n<activeCards.length; n++){
                if(rightMousePressed == true && activeCards[n].isIntsersecting(mousePos)){

                    enlarge = true;
                    moveCardToFront(n);
                    activeCards[0].enlargeCard();
                    break;
                }
                else{
                    if(enlarge == true){
                        activeCards[0].returnDefaultSize();
                        enlarge = false;
                    }
                }
            }
            return enlarge;
       }
       catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
            return enlarge;
       }
    }
    
    //THis Method Activates with a Center Mouse Click
    //If Mouse and Card Line Up, the card will rotate
    public boolean rotateCard(boolean stopRotating, Point mousPos){
        try{
            if(stopRotating == false){
                for(int n = 0; n<lightCardImages.length; n++){
                    if(activeCards[n].isIntsersecting(mousPos)){
                        moveCardToFront(n);
                        activeCards[0].increaseRotation();
                        //StopRoting is used to Keep From COntant Rotation when button is pressed
                        //Changed to True when Middle Mouse is released;
                        stopRotating = true; 
                        break;
                    }
                }  
            }
       }
       catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
       }
        return stopRotating;
    }
    
    public void drawTabledCards(Graphics2D gr, AffineTransform Default){
        for(int n = activeCards.length-1; n >=0 ; n--){
          if(activeCards[n] != null && activeCards[n].isInPlay() == true && activeCards[n].isHandHidden()==false
             && activeCards[n].isInHand()==false && activeCards[n].isDiscarded() == false){
            gr.rotate(Math.toRadians(activeCards[n].getRotation()),activeCards[n].getX()+activeCards[n].getWidth()/2,activeCards[n].getY()+activeCards[n].getHeight()/2 );
            gr.drawImage(activeCards[n].getImage(),activeCards[n].getX(),activeCards[n].getY(), activeCards[n].getWidth(),activeCards[n].getHeight(), null);
            gr.setTransform(Default);
          }
        }
    }
    
    public void drawCardsinHand(Graphics2D gr, AffineTransform Default){
        for(int n = activeCards.length-1; n >=0 ; n--){
          if(activeCards[n] != null && activeCards[n].isInPlay() == true && activeCards[n].isHandHidden()==false 
            && activeCards[n].isInHand()==true && activeCards[n].isDiscarded() == false){
                gr.rotate(Math.toRadians(activeCards[n].getRotation()),activeCards[n].getX()+activeCards[n].getWidth()/2,activeCards[n].getY()+activeCards[n].getHeight()/2 );
                gr.drawImage(activeCards[n].getImage(),activeCards[n].getX(),activeCards[n].getY(), activeCards[n].getWidth(),activeCards[n].getHeight(), null);
                gr.setTransform(Default);
          }
        }
    }
    
    public void drawDiscardedCards(Graphics2D gr, AffineTransform Default){
        for(int n = activeCards.length-1; n >=0 ; n--){
              if(activeCards[n] != null && activeCards[n].isInPlay() == true && activeCards[n].isHandHidden()==false
                 && activeCards[n].isInHand()==false && activeCards[n].isDiscarded() == true){
                gr.rotate(Math.toRadians(activeCards[n].getRotation()),activeCards[n].getX()+activeCards[n].getWidth()/2,activeCards[n].getY()+activeCards[n].getHeight()/2 );
                gr.drawImage(activeCards[n].getImage(),activeCards[n].getX(),activeCards[n].getY(), activeCards[n].getWidth(),activeCards[n].getHeight(), null);
                gr.setTransform(Default);
            }
        }
    }
    public void saveDecks(PrintWriter save){
        darkDeck.saveQueue(save);
        lightDeck.saveQueue(save);
        save.println(totalActive);
        for(int n = 0; n< activeCards.length; n++){
            if(activeCards[n] != null){
                save.println(activeCards[n].getX());
                save.println(activeCards[n].getY());
                save.println(activeCards[n].getRotation());
                save.println(activeCards[n].getImageNumber());
                save.println(activeCards[n].isInPlay());
                save.println(activeCards[n].isRotated());
                save.println(activeCards[n].isDarkSide());
                save.println(activeCards[n].isInHand());
                save.println(activeCards[n].isHandHidden()); 
                save.println(activeCards[n].isDiscarded());
            }
        }
    }
    
    public void loadDecks(Scanner load){
        darkDeck.loadQueue(load,darkCardImages);
        lightDeck.loadQueue(load, lightCardImages);
        totalActive = load.nextInt();
        for(int n = 0; n< totalActive; n++){
                activeCards[n] = new Card();
                activeCards[n].setX(load.nextInt());
                activeCards[n].setY(load.nextInt());
                activeCards[n].setRotationAngle(load.nextInt());
                activeCards[n].setImageNumber(load.nextInt());
                activeCards[n].changeInPlay(Boolean.valueOf(load.next()));
                activeCards[n].rotateCard(Boolean.valueOf(load.next()));
                activeCards[n].changeDarkSide(Boolean.valueOf(load.next()));
                activeCards[n].changeHandStatus(Boolean.valueOf(load.next()));
                activeCards[n].changeHiddenHand(Boolean.valueOf(load.next()));
                activeCards[n].changeDiscarded(Boolean.valueOf(load.next()));

                if(activeCards[n].isDarkSide() == true){
                    activeCards[n].changeImage(darkCardImages[activeCards[n].getImageNumber()]);
                }
                else{
                    activeCards[n].changeImage(lightCardImages[activeCards[n].getImageNumber()]);
                }
                activeCards[n].returnDefaultWidth();
                activeCards[n].trueDefaultHeight();
        }
    }
    
    //This Method Moves the selected card to the 0 slot in the array
    //This sereves to Bring the card to the front of all other cards and 
    //Keeps the correct card selected/
    private void moveCardToFront(int currentPosition){
        if(currentPosition >= 0 && currentPosition < totalActive ){
            Card Temp = activeCards[currentPosition];

            for(int n = currentPosition; n>0; n--){
                activeCards[n] = activeCards[n-1];
            }
            activeCards[0] = Temp;
        }
    }
    
    //returns true is card is below the "Hand" threshold
    //Currently only used moveCard() Method;
    private boolean inHand(int position, int bottomHeight){
        if(position >= 0 && position < totalActive ){
            if(activeCards[position].getY()+5*activeCards[position].getHeight()/6 > bottomHeight){
                return true; 
            }
            else{
                return false;
            }
        }
        else{
            throw new NoSuchElementException("Error while testing if card was placed in hand");
        }
    }
    
    //Test if card has been placed within discard pile;
    private boolean discardCard(int position,int discardX, int discardY, int discardWidth, int discardHeight, boolean showDiscard){
        if(position >= 0 && position < totalActive ){
            if(showDiscard == true 
                &&activeCards[position].getX() + activeCards[position].getWidth()> discardX
                &&activeCards[position].getY() + activeCards[position].getHeight()> discardY
                &&activeCards[position].getY() < discardY + discardWidth){
                    return true;
                }
            else{
                  return false;
              } 
        }
        else{
            throw new NoSuchElementException("Error while testing if card was placed discard pile");
        }
    }
}
