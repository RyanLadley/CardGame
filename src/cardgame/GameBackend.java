package cardgame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON2;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GameBackend extends AbstractAction
                        implements MouseListener, KeyListener{ 
    private boolean cardMoving = false, 
                    enlarge = false,
                    stopRotating = false, 
                    stopSide = false,
                    shiftingTable = false,
                    changingCount = false, 
                    openingMenu = false,
                    //Mouse Presses
                    leftPressed = false, rightPressed = false, middlePressed = false;
    private Random rand = new Random();
    
    private JFrame programFrame;
    private GameUI gameUI;
    private CardFactory cardFactory;
    private int[] weightedArray;
    private AudioPlayer player;
    private Timer gameTimer;
    
    public GameBackend(GameUI field, JFrame frame){
        programFrame = frame;
        weightedArray = initializeWeightedRandomArray();
        gameUI = field;
        cardFactory = CardFactory.getInstance();
        player = AudioPlayer.getInstance();
    }
    
    @Override
    //All Methods Prefored within this method will activate every timer rotation
    //Mouse and key logic is located at bottom of this class
    public void actionPerformed(ActionEvent evt) {
        if(gameUI.DrawMenu == false){//THis stops all game action if menu is open;
            moveTableRight();
            moveTableLeft();
            moveTableUp();
            moveTableDown();
            if(leftPressed== true){
                if(shiftingTable == false){
                    if(cardMoving == false){//this condition statement fixes bug where the deck activates when holding a card and hover over a deck 
                        //These Have First Priority since they display in front pf cards
                        addForceCount();
                        subtractForceCount();
                        displayRandomGenerator();
                        randomNumberSelect();
                        showDiscardPile();
                    }
                    cardMoving = cardFactory.moveCard(cardMoving, gameUI.getMousePosition(), gameUI.bottomHeight, 
                               gameUI.discardX, gameUI.discardY, gameUI.discardWidth, gameUI.discardHeight, gameUI.ShowDiscard);
                    if(cardMoving == false){//this condition statement fixes bug where the deck activates when holding a card and hover over a deck
                        deal();
                        selectSide();
                        openMenu();
                    }
                }
            
            }
            else{
                cardMoving = false;
                enlarge = cardFactory.enlargeCard(rightPressed, enlarge, gameUI.getMousePosition()); 
                if(middlePressed == true){
                    stopRotating = cardFactory.rotateCard(stopRotating, gameUI.getMousePosition()); 
                } 
            }
        }
        else{
            highlightMenu();
            if(leftPressed == true){
                selectFromMenu();
                leftPressed = false;
            }
        }
        
            
        gameUI.revalidate();
        gameUI.repaint();
        
    }
    
    public void beginNewGame(){
        resetValues();
        gameTimer = new Timer(20, this);
        player.playBackgroundMusic();
        programFrame.setLayout(new GridLayout(1,1,0,0));
        gameUI.setFocusable(true);
        gameUI.requestFocusInWindow();
        gameUI.addMouseListener(this);
        gameUI.addKeyListener(this);
        gameTimer.start();
        programFrame.getContentPane().removeAll();
        programFrame.add(gameUI);
        programFrame.revalidate();
        programFrame.repaint();
    }
    
    
    public void resetValues(){
        //Constant Actions Values
        cardFactory.reset();
        cardMoving = false; 
        enlarge = false;
        leftPressed = false; 
        rightPressed = false; 
        middlePressed = false; 
        stopRotating = false; 
        stopSide = false; 
        shiftingTable = false; 
        changingCount = false; 
        openingMenu = false; 
        gameUI.DrawMenu = false;  
        gameUI.DrawLightDeck = true;
        gameUI.DrawDarkDeck = true;
        
    }
    
    
    //This Method Moves the selected card to the 0 slot in the array
    //This sereves to Bring the card to the front of all other cards and 
    //Keeps the correct card selected/
    private Card[] moveCardToFront(Card[] Array, int Initial){
        Card Temp = Array[Initial];
        
        for(int n = Initial; n>0; n--){
            Array[n] = Array[n-1];
        }
        Array[0] = Temp;
        
        return Array;
    }
    
    //This Method is activated when the Mouse hovers over the right arrow in the GUI;
    //It moves all cards above the "Hand and Deck" to the left
    //THis creates the illusion of looking right
    private void moveTableRight(){
        try{//getMousePosition() Can sometimes return Null
            if(gameUI.getMousePosition().getX() >= gameUI.getWidth()-10){
                //Move Table
                gameUI.tableX -= gameUI.getWidth()/150; 
                //MoveCards
                cardFactory.moveTableX(-gameUI.getWidth()/150);
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    //This method actives when the mouse is over the left arrow
    //It moves all cards above the "Hand and Deck" to the right
    //THis creates the illusion of looking left 
    private void moveTableLeft(){
        try{//getMousePosition() Can sometimes return Null
            if(gameUI.getMousePosition().getX() <= 0){
                //Move Table
                gameUI.tableX += gameUI.getWidth()/150; 
                
                //MoveCards
                cardFactory.moveTableX(gameUI.getWidth()/150);
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    //This activates when the cursor is at the top of the screen
    //It moves moves all cards and table down, 
    //This gives the appearence of looking up
    private void moveTableUp(){
        try{//getMousePosition() Can sometimes return Null
            if(gameUI.getMousePosition().getY() == 0 && gameUI.tableY < 3*gameUI.getHeight() - gameUI.getHeight()/150){
                //Move Table
                gameUI.tableY += gameUI.getHeight()/150; 
                //Move Cards
                cardFactory.moveTableY(gameUI.getHeight()/150);
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        }
    }
    
    //This activates when the cursor is at the bottom of the screen
    //It moves moves all cards and table up, 
    //This gives the appearence of looking down
    private void moveTableDown(){
        try{//getMousePosition() Can sometimes return Null
            if(gameUI.getMousePosition().getY() >= gameUI.getHeight()-5 && gameUI.tableY > -3*gameUI.getHeight() + gameUI.getHeight()/150){
                
                //Move Table
                gameUI.tableY -= gameUI.getHeight()/150;
                //Move Cards
                cardFactory.moveTableY(-gameUI.getHeight()/150);
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        }
    }
    
    //This Method activates with a left mouse click
    //It Switched from Dark to Light side when clicked
    //And VIceVersa
    private void selectSide(){
        try{//getMousePosition() Can sometimes return Null
            if (stopSide == false){
                if(gameUI.sideSelectButton.mouseIntersects(gameUI.getMousePosition())){
            
                    gameUI.isDarkSide = (gameUI.isDarkSide == false);
                    //StopSide is used to keep from constant selection when mouse is held;
                    stopSide = true; 
                    player.clickSound();
                    //This Loop Causes Cards in Hnad to alternate depending on 
                    //Which Side is picked;
                    cardFactory.switchHands();
                } 
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
       }
    }
    
    
    
    
    //Adds to Force Count when + is selected
    private void addForceCount(){
        try{
            if(changingCount == false && gameUI.counterPlusBtn.mouseIntersects(gameUI.getMousePosition())){
                
                changingCount = true; //Keeps from constant scrolling when mouse is held
                if(gameUI.isDarkSide == true){
                    gameUI.darkForceCount++;
                }
                else{
                    gameUI.lightForceCount++;
                }
                player.clickSound();
                
            }
            
        }
         catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    //ForceCount subracted by when when - is selected
    private void subtractForceCount(){
        try{
            if(changingCount == false && gameUI.counterMinusBtn.mouseIntersects(gameUI.getMousePosition())){
                
                changingCount = true; //Keeps from constant scrolling when mouse is held
                if(gameUI.isDarkSide == true){
                    gameUI.darkForceCount--;
                }
                else{
                    gameUI.lightForceCount--;
                }
                player.clickSound();
            }
            
        }
         catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    //Uses card Factory to deal cards then determins if the decks need be displayed
    //Decks are not displayed when they are empty
    private void deal(){
        if(gameUI.deckDark.mouseIntersects(gameUI.getMousePosition())){
            if(gameUI.DrawDarkDeck && gameUI.isDarkSide){
                //If the following condition returns true, a darkside card has been dealt
                //if it returns false, it means no more cards are left in dark deck;
                if(cardFactory.dealCard(gameUI.isDarkSide)){
                    player.dealCardSound();
                    leftPressed = false;
                }
                else{
                    gameUI.DrawDarkDeck = false;
                }
            }
            //If the following condition returns true, a lightside card has been dealt
            //if it returns false, it means no more cards are left in light deck;
            else{if(gameUI.DrawLightDeck && !gameUI.isDarkSide){
                if(cardFactory.dealCard(gameUI.isDarkSide)){
                    player.dealCardSound();
                    leftPressed = false;
                }
                else{
                    gameUI.DrawLightDeck = false;
                }
            }
            }
        }
    }
    
    //This activates when the "arrow" is clicked
    //It hides and shows the random number generator at users request
    private void displayRandomGenerator(){
        try{
            if( gameUI.displayRandBtn.mouseIntersects(gameUI.getMousePosition())){
                
                leftPressed = false;
                gameUI.DisplayRandGen = (gameUI.DisplayRandGen != true);
                gameUI.LightRandNum = 0;
                gameUI.DarkRandNum = 0;
                player.clickSound();
            }
            
        }
         catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        }
    }
    
    //Creates an array with values between 1 and 6
    //Weighted so the higher the value the rarer it is
    //Used in the rand number generator
    private int[] initializeWeightedRandomArray(){
        int[] array = new int[100];
        
        for(int n = 0 ; n <33; n++){ //33% 1's
            array[n] = 1;
        }
        for(int n = 33 ; n <60; n++){//28% 2's
            array[n] = 2;
        }
        for(int n = 60 ; n <75; n++){//15% 3's
            array[n] = 3;
        }
        for(int n = 75 ; n <85; n++){//10% 4's
            array[n] = 4;
        }
        for(int n = 85 ; n <90; n++){//5% 5's
            array[n] = 5;
        }
        for(int n = 90 ; n <92; n++){//3% 6's
            array[n] = 6;
        }
        for(int n = 92 ; n <100; n++){//2% 0's
            array[n] = 0;
        }
        
        return array;
    }
    //Slects a random number between 0 and 6 when Shuffle button is pressed
    private void randomNumberSelect(){
        try{
            if( gameUI.randNumBtn.mouseIntersects(gameUI.getMousePosition())) {
                
                leftPressed = false;
                if(gameUI.isDarkSide == true){
                    gameUI.DarkRandNum = weightedArray[rand.nextInt(weightedArray.length)];
                }
                else{
                    gameUI.LightRandNum = weightedArray[rand.nextInt(weightedArray.length)];
                }
                player.clickSound();
            }
            
        }
         catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    
    //This activates when the left/right"arrow" is clicked
    //It hides and shows the Discard Pile
    private void showDiscardPile(){
        try{
            if( gameUI.discardBtn.mouseIntersects(gameUI.getMousePosition())){
                    
                    leftPressed = false;
                    gameUI.ShowDiscard = (gameUI.ShowDiscard == false);
                    player.clickSound();
            }
        }
         catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
            System.err.println(exc.toString());
        } 
    }
    
    
    //Opens Pause Menu when Button is selected
    private void openMenu(){
        try{
            if(gameUI.menuButton.mouseIntersects(gameUI.getMousePosition())){
                
                //OpeningMenu Stops Action IF button is held
                openingMenu = true;
                //Draw Menu ALlows for the drawing of Menu in Gamefield
                gameUI.DrawMenu = true;
                player.clickSound();
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
        
    }
    
    //HighLights Diffrent Menu Options with Saber
    private void highlightMenu(){
        try{
            if(gameUI.resumeBtn.mouseIntersects(gameUI.getMousePosition())){
                if(!gameUI.resumeBtn.isDisplayed()){
                    gameUI.resumeBtn.display(true);
                    gameUI.optionsBtn.display(false);
                    gameUI.saveBtn.display(false);
                    gameUI.loadBtn.display(false);
                    gameUI.quitBtn.display(false);
                    gameUI.exitBtn.display(false);
                }
                
            }
            else{if(gameUI.optionsBtn.mouseIntersects(gameUI.getMousePosition())){
                if(!gameUI.optionsBtn.isDisplayed()){
                    gameUI.resumeBtn.display(false);
                    gameUI.optionsBtn.display(true);
                    gameUI.saveBtn.display(false);
                    gameUI.loadBtn.display(false);
                    gameUI.quitBtn.display(false);
                    gameUI.exitBtn.display(false);
                }
            }
            else{if(gameUI.saveBtn.mouseIntersects(gameUI.getMousePosition())){
                if(!gameUI.saveBtn.isDisplayed()){
                    gameUI.resumeBtn.display(false);
                    gameUI.optionsBtn.display(false);
                    gameUI.saveBtn.display(true);
                    gameUI.loadBtn.display(false);
                    gameUI.quitBtn.display(false);
                    gameUI.exitBtn.display(false);
                }
            }
            else{if(gameUI.loadBtn.mouseIntersects(gameUI.getMousePosition())){
                if(!gameUI.loadBtn.isDisplayed()){
                    gameUI.resumeBtn.display(false);
                    gameUI.optionsBtn.display(false);
                    gameUI.saveBtn.display(false);
                    gameUI.loadBtn.display(true);
                    gameUI.quitBtn.display(false);
                    gameUI.exitBtn.display(false);
                }
            }
            else{if(gameUI.quitBtn.mouseIntersects(gameUI.getMousePosition())){
                if(!gameUI.quitBtn.isDisplayed()){
                    gameUI.resumeBtn.display(false);
                    gameUI.optionsBtn.display(false);
                    gameUI.saveBtn.display(false);
                    gameUI.loadBtn.display(false);
                    gameUI.quitBtn.display(true);
                    gameUI.exitBtn.display(false);
                }
            }
            else{if(gameUI.exitBtn.mouseIntersects(gameUI.getMousePosition())){
                
                if(!gameUI.exitBtn.isDisplayed()){
                    gameUI.resumeBtn.display(false);
                    gameUI.optionsBtn.display(false);
                    gameUI.saveBtn.display(false);
                    gameUI.loadBtn.display(false);
                    gameUI.quitBtn.display(false);
                    gameUI.exitBtn.display(true);
                }
            }
            }
            }
            }
            }
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    //Selects Items From menu after left click
    private void selectFromMenu(){
        try{
            if(gameUI.resumeBtn.mouseIntersects(gameUI.getMousePosition())){
                player.selectionUpSound();
                gameUI.showGameSaved = false;
                gameUI.DrawMenu = false;
            }
            else{if(gameUI.optionsBtn.mouseIntersects(gameUI.getMousePosition())){
                //Nothing Here (Options)
                
            }
            else{if(gameUI.saveBtn.mouseIntersects(gameUI.getMousePosition())){
                player.selectionUpSound();
                createSaveFile();
                
            }
            else{if(gameUI.loadBtn.mouseIntersects(gameUI.getMousePosition())){
                    player.selectionUpSound();
                    gameUI.DrawMenu = false;
                    gameUI.showGameSaved = false;
                    beginNewGame();
                    loadSaveFile();
            }
            else{if(gameUI.quitBtn.mouseIntersects(gameUI.getMousePosition())){
                player.selectionDownSound();
                player.stopBackgroundMusic();
                gameUI.showGameSaved = false;
                programFrame.getContentPane().removeAll();
                MenuBackend menuActions = new MenuBackend(new MenuUI(gameUI.getWidth(), gameUI.getHeight()), programFrame);
                menuActions.startMainMenu();
                gameTimer.stop();
            }
            else{if(gameUI.exitBtn.mouseIntersects(gameUI.getMousePosition())){
                System.exit(0);
            }
            }
            }
            }
            }
            }
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
        } 
    }
    
    
    
    
    
    //This method saces the current game
    public void createSaveFile(){
        try (PrintWriter save = new PrintWriter(new File("SaveGame.txt"))) {
            System.out.println("Save");

            save.println(gameUI.isDarkSide);
            save.println(gameUI.DrawDarkDeck);
            save.println(gameUI.DrawLightDeck);
            cardFactory.saveDecks(save);
            gameUI.showGameSaved = true;
        }
        catch(Exception ex){
            //return X +"\n"+ Y + "\n"+ RotationAngle +"\n"+ ImageNumber+"\n"+InPlay+"\n"+Rotate +"\n"+ DarkSide+"\n"+ InHand +"\n"+ HideHand;
        }
    }
    
    //This method loads that last sved game.
    public void loadSaveFile(){
        try{
            Scanner load = new Scanner(new File("SaveGame.txt"));
            
            System.out.println("Load");
            gameUI.isDarkSide = Boolean.valueOf(load.next());
            gameUI.DrawDarkDeck = Boolean.valueOf(load.next());
            gameUI.DrawLightDeck = Boolean.valueOf(load.next());
            
            cardFactory.loadDecks(load);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    
    //Begin Mouse Listenrs
    @Override
    public void mouseClicked(MouseEvent evt) {
        //Nothing Here Yet
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if(evt.getButton() == BUTTON1){ //Left Mouse Button Actions
            leftPressed = true;
        }
        else{
            if(evt.getButton() == BUTTON3){ //Right Mouse Button Actions
                rightPressed = true;
            }
            else{
                if(evt.getButton() == BUTTON2){
                    middlePressed = true;
                    
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
       leftPressed = false;
       rightPressed = false;
       middlePressed = false;
       
       stopRotating = false;
       stopSide = false;
       shiftingTable = false;
       changingCount = false;
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
       /// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent evt) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //BeginKeyBoard Listers
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(openingMenu == false){
           if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
               gameUI.DrawMenu = (gameUI.DrawMenu == false);
                openingMenu = true;
            } 
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        openingMenu = false;
    }
    
}
