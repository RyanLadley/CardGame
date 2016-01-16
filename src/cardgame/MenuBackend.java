/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Leulad
 */
public class MenuBackend extends AbstractAction  implements MouseListener{
    boolean leftPressed = false;
    AudioPlayer player = AudioPlayer.getInstance();
    
    MenuUI menuUI;
    JFrame programFrame;
    
    Timer menuTimer;
    
    
    public MenuBackend(MenuUI field, JFrame frame){
        menuUI = field;
        programFrame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(menuUI.drawMainMenu  == true){ //Actions for when Main Menu is diaplyed
            highlightMenu();

            if(leftPressed == true){
                selectMenuButtons();
            }
        }
        else{ // Actions For Options Menu
            highlightOptions();
            if(leftPressed == true){
                selectOptionsButtons();
            }
        }
        
        menuUI.revalidate();
        menuUI.repaint();
    }
    
    public void startMainMenu(){
        menuTimer = new Timer(20, this);
        menuUI.addMouseListener(this);
        player.playThemeMusic();
        programFrame.getContentPane().removeAll();
        programFrame.add(menuUI);
        programFrame.revalidate();
        programFrame.repaint();
        menuTimer.start();
    }
    //THis Method Activates when cursor roles over Menu Buttons
    //When this happens, the Menu Button will have a lightsaber underneath
    public void highlightMenu(){
        //Check Highlight NewGame
        try{
           if(menuUI.newBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.newBtn.isDisplayed()){
                    menuUI.newBtn.display(true);
                    menuUI.loadBtn.display(false);
                    menuUI.optionsBtn.display(false);
                    menuUI.exitBtn.display(false);
                } 
                
            }
            //Check Highlight LoadGame
            else{if(menuUI.loadBtn.mouseIntersects(menuUI.getMousePosition())){
                    if (!menuUI.loadBtn.isDisplayed()){
                        menuUI.newBtn.display(false);
                        menuUI.loadBtn.display(true);
                        menuUI.optionsBtn.display(false);
                        menuUI.exitBtn.display(false);
                    } 
            }
        
            //Check Highlight Options
            else{if(menuUI.optionsBtn.mouseIntersects(menuUI.getMousePosition())){
                    if (!menuUI.optionsBtn.isDisplayed()){
                        menuUI.newBtn.display(false);
                        menuUI.loadBtn.display(false);
                        menuUI.optionsBtn.display(true);
                        menuUI.exitBtn.display(false);
                    } 
                    
                }
                //Check Highlight Exit
                else{if(menuUI.exitBtn.mouseIntersects(menuUI.getMousePosition())){
                    if (!menuUI.exitBtn.isDisplayed()){
                        menuUI.newBtn.display(false);
                        menuUI.loadBtn.display(false);
                        menuUI.optionsBtn.display(false);
                        menuUI.exitBtn.display(true);
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
    
    //THis Method Activates when cursor roles over Menu Buttons
    //When this happens, the Menu Button will have a lightsaber underneath
    //Only one button will be highlighted at a time.
    public void selectMenuButtons(){
        try{
            //New Game Selected
           if(  menuUI.newBtn.mouseIntersects(menuUI.getMousePosition())){ 
                leftPressed = false;
                menuTimer.stop();
                player.selectionUpSound();
                player.stopThemeMusic();
                programFrame.getContentPane().removeAll(); 
                programFrame.add(new LoadingScreen(menuUI.getWidth(), menuUI.getHeight()));
                programFrame.revalidate();
                programFrame.repaint();
                new GameBackend(new GameUI(menuUI.getWidth(), menuUI.getHeight()), programFrame).beginNewGame();
            }
            //LoadGame Selected
            else{if( menuUI.loadBtn.mouseIntersects(menuUI.getMousePosition())){
                    leftPressed = false;
                    player.selectionUpSound();
                    programFrame.getContentPane().removeAll();
                    player.stopThemeMusic();
                    menuTimer.stop();
                    GameBackend gameBack = new GameBackend(new GameUI(menuUI.getWidth(), menuUI.getHeight()), programFrame);
                    gameBack.beginNewGame();
                    gameBack.loadSaveFile();
                    programFrame.revalidate();
                    programFrame.repaint();
                }
        
            //OptionsSelected
            else{if( menuUI.optionsBtn.mouseIntersects(menuUI.getMousePosition())){
                    menuUI.drawMainMenu = false;
                    leftPressed = false;
                    player.selectionUpSound();
                    
                    //Highlight Music Enable to start Options Menu
                    menuUI.musicEnBtn.display(true);
                    menuUI.musicDisBtn.display(false);
                    menuUI.soundFXEnBtn.display(false);
                    menuUI.soundFXDisBtn.display(false);
                    menuUI.backBtn.display(false);
                }
            // Exit Selected
            else{if( menuUI.exitBtn.mouseIntersects(menuUI.getMousePosition())){
                    System.exit(0);
                }
                }
                }
                } 
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
       }
    }
    
    //THis Method Activates when cursor roles over Menu Buttons
    //When this happens, the Menu Button will have a lightsaber underneath
    //Only one button will be highlighted at a time.
    public void highlightOptions(){
        try{
            //Check Highlight Music Enable
           if(menuUI.musicEnBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.musicEnBtn.isDisplayed()){
                    menuUI.musicEnBtn.display(true);
                    menuUI.musicDisBtn.display(false);
                    menuUI.soundFXEnBtn.display(false);
                    menuUI.soundFXDisBtn.display(false);
                    menuUI.backBtn.display(false);
                }
                
            }
            //Check Highlight Musc Disable
            else{if( menuUI.musicDisBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.musicDisBtn.isDisplayed()){
                    menuUI.musicEnBtn.display(false);
                    menuUI.musicDisBtn.display(true);
                    menuUI.soundFXEnBtn.display(false);
                    menuUI.soundFXDisBtn.display(false);
                    menuUI.backBtn.display(false);
                }
            }

            //Check Highlight Soud Effects Enable
            else{if( menuUI.soundFXEnBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.soundFXEnBtn.isDisplayed()){
                    menuUI.musicEnBtn.display(false);
                    menuUI.musicDisBtn.display(false);
                    menuUI.soundFXEnBtn.display(true);
                    menuUI.soundFXDisBtn.display(false);
                    menuUI.backBtn.display(false);
                }

                }
            //Check Highlight Sound Effects Disable
            else{if(  menuUI.soundFXDisBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.soundFXDisBtn.isDisplayed()){
                    menuUI.musicEnBtn.display(false);
                    menuUI.musicDisBtn.display(false);
                    menuUI.soundFXEnBtn.display(false);
                    menuUI.soundFXDisBtn.display(true);
                    menuUI.backBtn.display(false);
                }
            }
            //Check Highlight Back
            else{if(menuUI.backBtn.mouseIntersects(menuUI.getMousePosition())){
                if (!menuUI.backBtn.isDisplayed()){
                    menuUI.musicEnBtn.display(false);
                    menuUI.musicDisBtn.display(false);
                    menuUI.soundFXEnBtn.display(false);
                    menuUI.soundFXDisBtn.display(false);
                    menuUI.backBtn.display(true);
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
    
    public void selectOptionsButtons(){
        
        try{
            //Select Enable Music
            if(menuUI.musicEnBtn.mouseIntersects(menuUI.getMousePosition())){
                    if(!player.musicEnabled()){
                        player.enableMusic(true);
                        player.playThemeMusic();
                        player.selectionUpSound();
                    }
                    
            }
            //Select Disable Music
            else{if(menuUI.musicDisBtn.mouseIntersects(menuUI.getMousePosition())){
                        if(player.musicEnabled()){
                            player.enableMusic(false);
                            player.selectionUpSound();
                            player.stopThemeMusic();
                        }
               
            }
        
            //Check Highlight Options
            else{if( menuUI.soundFXEnBtn.mouseIntersects(menuUI.getMousePosition())){
                        if(!player.soundEnabled()){
                            player.enableSound(true);
                            player.selectionUpSound();
                        }
                }
            //Check Highlight Exit
            else{if(menuUI.soundFXDisBtn.mouseIntersects(menuUI.getMousePosition())){
                        if(player.soundEnabled()){
                            player.enableSound(false);
                        }
                }
            //Back Button Pressed
            else{if(menuUI.backBtn.mouseIntersects(menuUI.getMousePosition())){
                    menuUI.drawMainMenu = true;
                    player.selectionDownSound();
                    
                    //Highlight New to start on Main Menu
                    menuUI.newBtn.display(true);
                    menuUI.loadBtn.display(false);
                    menuUI.optionsBtn.display(false);
                    menuUI.exitBtn.display(false);
                }
            }
            }
            }
            }
            leftPressed = false;
        }
        catch(NullPointerException exc){ //getMousePosition() Can sometimes return Null
        //do Nothing
       }
    }
   //Mouse Listeners
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        leftPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        leftPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
