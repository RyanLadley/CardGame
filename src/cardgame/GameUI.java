
package cardgame;

//This class will act as the display for all gameplay. Its primary role it its "Paint" Function

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;

public class GameUI extends JLabel{
    private  int width, height;
    
    boolean initialize = false;
   
    //All int varibales are initilized inside of constructor due to
    //the need of "width" and "height" variables which are provided their
    int tableX = 0, tableY = 0, tableWidth = width, tableHeight = height, 
        //bottomHeight is the dividing line between the table and hand;
        bottomHeight,
        //count location is used for the String that display the force counts
        countLocation,  lightForceCount, darkForceCount,
        //countVariables are used to the force counter located on the top of the UI
        countX, countY, countWidth, countHeight,
        //RandNum Variables are used to draw 
        RandNumX, RandNumY, RandNumWidth, RandNumHeight,
        //Discard Variables are used to draw the Discard pile (the trash can on the right) when it is deployed
        discardX, discardY, discardWidth, discardHeight,
        
        LightRandNum , DarkRandNum;
               
               
    boolean DrawDarkDeck = true, DrawLightDeck = true, isDarkSide = false, DrawMenu = false, DisplayRandGen = false, ShowDiscard = false, showGameSaved = false;
    
    Color bottomColor = new Color(0,0,0,200),
          darkOutLineColor = new Color(146,26,26,250), lightOutLineColor = new Color(62,22,192, 250),
          menuFilter = new Color(0,0,0,150),pausedColor = new Color(230,230,230,250);
    
    Font SideFont, PausedFont, RandFont, MenuFont;
    
    Button menuButton, sideSelectButton, counterMinusBtn, counterPlusBtn,displayRandBtn, discardBtn, ressumeBtn, randNumBtn, deckLight, deckDark,
           resumeBtn, saveBtn, loadBtn, optionsBtn, quitBtn, exitBtn;
    
    AffineTransform Default;
    CardFactory cardFactory;
    GameResources resources;
    
    public GameUI(int screenWidth, int screenHeight){
        width = screenWidth;
        height = screenHeight;
        
        SideFont = new Font("Britanic Bold", Font.BOLD, height/60);
        RandFont = new Font("Britanic Bold", Font.BOLD, height/50);
        PausedFont = new Font("Britanic Bold", Font.BOLD, height/17);
        MenuFont = new Font("Sans Serif", Font.BOLD, height/25);
        
        //table variables are used to draw the main background image;
        tableX = 0; 
        tableY = 0; 
        tableWidth = width; 
        tableHeight = height;
        //bottomHeight is the dividing line between the table and hand;
        bottomHeight = 751*height/1000;
        //count location is used for the String that display the force counts
        countLocation= 498*width/1000;  
        lightForceCount = 0; 
        darkForceCount = 0;
        //countVariables are used to the force counter located on the top of the UI
        countX= 45*width/100; 
        countY = 0; 
        countWidth = width/10; 
        countHeight=height/40;
        //RandNum Variables are used to draw 
        RandNumX = 58*width/100; 
        RandNumY = 4*height/100-1; 
        RandNumWidth = 3*width/100; 
        RandNumHeight = 3*height/100;
        //Discard Variables are used to draw the Discard pile (the trash can on the right) when it is deployed
        discardX = 90*width/100; 
        discardY = 40*height/100; 
        discardWidth =10*width/100; 
        discardHeight = 25*height/100;
    
        resources = new GameResources();
        cardFactory = CardFactory.getInstance();
        createButtons();
    }
    
    @Override
    public int getWidth(){
        return width;
    }
    
    @Override
    public int getHeight(){
        return height;
    }
    
    private void createButtons(){
        sideSelectButton = new Button(resources.getLightThumb(),resources.getDarkThumb(), 816*width/1000, bottomHeight-height/50, width/13, height/32);
        counterMinusBtn = new Button(resources.getMinus(), 45*width/100, height/60, width/60,height/40);
        counterPlusBtn = new Button(resources.getPlus(), 5339*width/10000, height/60, width/60,height/40);
        displayRandBtn = new Button(resources.getDownArrow(), resources.getUpArrow(),  567*width/1000, -2,17*width/1000, 2*height/100);
        randNumBtn = new Button(resources.getShuffle(), 586*width/1000, 4*height/100, 2*width/100, 3*height/100);
        discardBtn = new Button(resources.getLeftArrow(), resources.getRightArrow(), 985*width/1000, 383*height/1000, 17*width/1000, 2*height/100);
        menuButton = new Button(resources.getMenuIcon(), 898*width/1000, bottomHeight-height/50,width/40,height/32);
        deckLight = new Button(resources.getLightDeck(), 9*width/10, 780*height/1000, width /12, height/5);
        deckDark = new Button(resources.getDarkDeck(), 9*width/10, 780*height/1000, width /12, height/5);
        
        //Menu Buttons
        resumeBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 41*width/100,34*height/100, width/6, height/10, true);
        saveBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 39*width/100, 41*height/100, 19*width/100, height/10, false);
        loadBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 39*width/100, 48*height/100, 19*width/100, height/10, false);
        optionsBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 41*width/100, 55*height/100, width/6, height/10, false);
        quitBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 447*width/1000, 62*height/100, width/10, height/10,false);
        exitBtn = new Button(resources.getLightThumb(), resources.getDarkThumb(), 34*width/100, 69*height/100,  30*width/100, height/10, false); 
    }
    
    //This methods creates the GUI for the game.
    //Wverything is drwn here
    @Override
    public void paint(Graphics g){
        Graphics2D gr = (Graphics2D)g;
        gr.fillRect(0,0,width, height);
        if(initialize == false){
            this.setPreferredSize(new Dimension(width, height));
            Default = gr.getTransform(); // Allows restest of Graphics
            initialize = true;
        }
        //Draw Background
        for(int x = -50; x <=50; x++){
            for(int y = -3; y <=3; y++){
                if(x%2 == 0){ //Two Images create a more seamless feel
                    gr.drawImage(resources.getFlippedTable(),tableX + (width*x)-width/100*x,tableY +(height*y),tableWidth, tableHeight, null);
                }
                else{
                    gr.drawImage(resources.getTable(),tableX + (width*x)-width/100*x,tableY +(height*y),tableWidth, tableHeight, null);
                }
        }
        }
        gr.setTransform(Default);
        gr.drawString("RYAN IS THE BEST!!", 15, 15);
        
        //Draw Cards that are on table;
        cardFactory.drawTabledCards(gr, Default);
        
        //Setup Bottom (Hand and deck)
        gr.setColor(bottomColor);
        gr.fillRect(0, bottomHeight, 78*width/100, height/4);
        gr.fillRect(78*width/100, bottomHeight, width/4, height/4);
        if(isDarkSide == false){
            gr.setColor(lightOutLineColor);
        }
        else{
           gr.setColor(darkOutLineColor);
            
        }
        gr.drawRect(-1, bottomHeight, 78*width/100, height/4);
        gr.drawRect(78*width/100-1, bottomHeight, width/4, height/4);
        
        //Draw Side Icon
        sideSelectButton.draw(gr, isDarkSide);
        if(isDarkSide == false){
            gr.setColor(lightOutLineColor);
        }
        else{//LightSide Icon
            gr.setColor(darkOutLineColor);
        }
        gr.drawRect(sideSelectButton.getX(), sideSelectButton.getY(), sideSelectButton.getWidth(), sideSelectButton.getHeight());
        
        //Draw Menu Icon
        gr.setColor(Color.GRAY);
        gr.fillRect(menuButton.getX(), menuButton.getY(), menuButton.getWidth()-1, menuButton.getHeight()-1);
        menuButton.draw(gr);
        if(isDarkSide == false){
            gr.setColor(lightOutLineColor);
        }
        else{
            gr.setColor(darkOutLineColor);
        }
        gr.drawRect(menuButton.getX(), menuButton.getY(), menuButton.getWidth(), menuButton.getHeight());
        
        //Draw Deck
        if(isDarkSide == true){
            if(DrawDarkDeck == true){//Condition Statement is used to only draw deck if cards are left to be drawn. It is modified in the ConstatnActions.dealCards() method
                deckDark.draw(gr);
            } 
        }
        else{
           if(DrawLightDeck == true){
               deckLight.draw(gr);
           } 
        }
        
        //Draw Cards that are in the hand (Out of Hand Cards are drawn above(code wise) "SetUp Bottom"
        cardFactory.drawCardsinHand(gr, Default);
        
        //Draw Force Counter
        gr.setColor(bottomColor);
        gr.fillRect(countX, countY, countWidth, height/25);
        gr.fillRect(countX, height/60, countWidth, countHeight);
        counterMinusBtn.draw(gr); //Subtraction
        counterPlusBtn.draw(gr); //Addition
        gr.setColor(Color.white);
        gr.setFont(SideFont);
        gr.drawString("Force Count", 473*width/1000, height/75);
        if(isDarkSide == true){
            gr.drawString(String.valueOf(darkForceCount),countLocation,height/29);
            gr.setColor(darkOutLineColor);
        }
        else{
            gr.drawString(String.valueOf(lightForceCount),countLocation,height/29);
            gr.setColor(lightOutLineColor);
        }
        gr.drawRect(countX, countY-1, countWidth, height/60);
        
        //Draw Random Number Generator
        displayRandBtn.draw(gr, DisplayRandGen);
        if(DisplayRandGen == true){
            //Draw Rand Number
            gr.setColor(bottomColor);
            gr.fillRect(58*width/100, -1, 3*width/100, 7*height/100);
            randNumBtn.draw(gr);
            gr.setColor(Color.white);
            gr.setFont(RandFont);
            if(isDarkSide == true){
                gr.drawString(String.valueOf(DarkRandNum), 5925*width/10000, 25*height/1000);
                gr.setColor(darkOutLineColor);
            }
            else{
                gr.drawString(String.valueOf(LightRandNum), 5925*width/10000, 25*height/1000);
                gr.setColor(lightOutLineColor);
            }
            gr.drawRect(58*width/100, -1, 3*width/100, 7*height/100);
            gr.drawRect(RandNumX, RandNumY, RandNumWidth, RandNumHeight); 
            
        }
        
        //Draw Discard Pile
        discardBtn.draw(gr, ShowDiscard);
        if(ShowDiscard == true){
            gr.setColor(bottomColor);
            gr.drawImage(resources.getBin(), 903*width/1000, 43*height/100, 10*width/100, 20*height/100, null);
            gr.fillRect(discardX, discardY, discardWidth, discardHeight);
            if(isDarkSide == true){
                gr.setColor(darkOutLineColor);
            }
            else{
                gr.setColor(lightOutLineColor);
            }
            gr.drawRect(discardX, discardY, discardWidth, discardHeight);
            
            //Draw Cards that are in discard pile
            cardFactory.drawDiscardedCards(gr, Default);
        }
           
        //Draw Pause Menu
        if(DrawMenu == true){
            gr.setColor(menuFilter);
            gr.fillRect(0,0, width, height);
            gr.setColor(Color.BLACK);
            gr.fillRect(width/3, height/8, width/3, 2*height/3);
            //Draw Top of Menu (Lightning)
            gr.drawImage(resources.getLightning(),width/3, height/8, width/3, height/5, null);
            gr.setFont(PausedFont);
            gr.setColor(Color.BLACK);
            gr.drawString("Game", 4*width/10-2, 22*height/100-1);
            gr.drawString("Game", 4*width/10+1, 22*height/100+1);
            gr.setColor(Color.WHITE);
            gr.drawString("Game", 4*width/10, 22*height/100);
            gr.setColor(Color.BLACK);
            gr.drawString("Paused", 48*width/100-1, 27*height/100-1);
            gr.drawString("Paused", 48*width/100+1, 27*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Paused", 48*width/100, 27*height/100);
            //Draw Selection Choices
            //Draws specific Saber depending on which side is selected. Saber acts as selection
            resumeBtn.draw(gr, isDarkSide);
            saveBtn.draw(gr, isDarkSide);
            loadBtn.draw(gr, isDarkSide);
            optionsBtn.draw(gr, isDarkSide);
            quitBtn.draw(gr, isDarkSide);
            exitBtn.draw(gr, isDarkSide);
            gr.setFont(MenuFont);
            gr.setColor(Color.BLACK);
            gr.drawString("Resume", 46*width/100+1, 40*height/100+1);
            gr.drawString("Save Game", 445*width/1000+1, 47*height/100+1);
            gr.drawString("Load Game", 445*width/1000+1, 54*height/100+1);
            gr.drawString("Options", 464*width/1000+1, 61*height/100+1);
            gr.drawString("Quit", 482*width/1000+1, 68*height/100+1);
            gr.drawString("Exit to Windows", 423*width/1000+1, 75*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Resume", 46*width/100, 40*height/100);
            gr.drawString("Save Game", 445*width/1000, 47*height/100);
            gr.drawString("Load Game", 445*width/1000, 54*height/100);
            gr.drawString("Options", 464*width/1000, 61*height/100);
            gr.drawString("Quit", 482*width/1000, 68*height/100);
            gr.drawString("Exit to Windows", 423*width/1000, 75*height/100);
            if(isDarkSide == true){
                gr.setColor(darkOutLineColor);
            }
            else{
                gr.setColor(lightOutLineColor);
            }
            gr.drawRect(width/3, height/8, width/3, height/5);
            gr.drawRect(width/3, height/8, width/3, 2*height/3);
            if(showGameSaved == true){
                gr.setFont(SideFont);
                gr.setColor(Color.white);
                gr.drawString("Game Saved", 93*width/100, 2*height/100);
            }
        }
    }
    
}
