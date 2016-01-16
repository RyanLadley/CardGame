package cardgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


//This Class is used specificaly to add the game title to the Main Menu
public class MenuUI extends JPanel {
    private int width, height;
    Font MenuFont, MajorFont;
    AudioPlayer player = AudioPlayer.getInstance();
    MenuResources resources = new MenuResources();
    //If true, main menu is displayed, if false,  the options menu is displayed;
    boolean drawMainMenu = true;
    
    //Buttons For Menu are actually the saber.
    //Strings are just for users sake (for show and information)
    Button newBtn, loadBtn, optionsBtn, exitBtn,
           musicEnBtn, musicDisBtn, soundFXEnBtn, soundFXDisBtn, backBtn;
    
    public MenuUI(int screenWidth, int screenHeight){
        width = screenWidth;
        height = screenHeight;
        MenuFont = new Font("Sans Serif", Font.BOLD, width/75);
        MajorFont = new Font("Sans Serif", Font.BOLD, width/60);
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
        //Main Menu Buttons
        newBtn = new Button(resources.getSaber(), 143*width/1000, 7*height/15, width/10, height/17, true);
        loadBtn = new Button(resources.getSaber(), 9*width/50, 27*height/50, width/10, height/17, false);
        optionsBtn = new Button(resources.getSaber(), 22*width/100, 127*height/200, width/10, height/17, false);
        exitBtn = new Button(resources.getSaber(), 27*width/100, 717*height/1000, width/10, height/17, false);
        
        //Options Menu Buttons
        musicEnBtn = new Button(resources.getSaber(), 14*width/100, 517*height/1000, width/10, height/17, false);
        musicDisBtn = new Button(resources.getSaber(), 23*width/100, 517*height/1000, width/10, height/17, false);
        soundFXEnBtn = new Button(resources.getSaber(),14*width/100-1, 618*height/1000-1, width/10, height/17, false);
        soundFXDisBtn = new Button(resources.getSaber(), 23*width/100-1, 618*height/1000-1, width/10, height/17, false);
        backBtn = new Button(resources.getSaber(), 14*width/100, 768 *height/1000, width/10, height/17, false);
        //The -1 in the soundFX buttons Y coordinate is to prevent overlaping with the mucis enable and disable buttons
    }
    
    
    @Override
    public void paint(Graphics g){
        Graphics2D gr = (Graphics2D)g;
        
        gr.drawImage(resources.getBackground(), 0,0,width,height, null);
        gr.drawImage(resources.getLogo(),width/6,height/25, width/2, height/3,null);
        
        if(drawMainMenu == true){
           //Draws New Game, LoadGame, Options and Exit
           //NewGame
           newBtn.draw(gr);
           gr.setFont(MenuFont);
           gr.setColor(Color.black);
           gr.drawString("New Game", width/6+1, height/2+1);
           gr.setColor(Color.WHITE);
           gr.drawString("New Game", width/6, height/2);
           
           //LoadGame
           loadBtn.draw(gr);
           gr.setColor(Color.black);
           gr.drawString("Load Game", width/5+1, 4*height/7+1);
           gr.setColor(Color.WHITE);
           gr.drawString("Load Game", width/5, 4*height/7);
           
           //Options
           optionsBtn.draw(gr);
           gr.setColor(Color.BLACK);
           gr.drawString("Options", width/4+1, 2*height/3+1);
           gr.setColor(Color.white);
           gr.drawString("Options", width/4, 2*height/3);
           
           //Exit
           exitBtn.draw(gr);
           gr.setColor(Color.BLACK);
           gr.drawString("Exit", 3*width/10+1, 3*height/4+1);
           gr.setColor(Color.white);
           gr.drawString("Exit", 3*width/10, 3*height/4);    
           
        }
        else{
            gr.setFont(MajorFont);
            gr.setColor(Color.white);
            gr.drawString("Music", width/6, height/2);

            //Draw Sound Effect Enable/Disable Highlight Effects
            musicEnBtn.draw(gr);
            musicDisBtn.draw(gr);
            gr.setFont(MenuFont);
            if(player.musicEnabled()){ // Highlight Enabled (Music)
               gr.setColor(Color.red);
               gr.drawString("Enabled", width/6+2, 55*height/100+2); 
            }
            else{ // Highlight Disabled (Music)
                gr.setColor(Color.red);
                gr.drawString("Disabled", width/4+2, 55*height/100+2);
            }

            //Draw Enabled in Options (Music)
            gr.setColor(Color.black);
            gr.drawString("Enabled", width/6+1, 55*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Enabled", width/6, 55*height/100);
            //Draw Disabled in Options (Music)
            gr.setColor(Color.black);
            gr.drawString("Disabled", width/4+1, 55*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Disabled", width/4, 55*height/100);

            //Draw SoundEffects
            gr.setFont(MajorFont);
            gr.setColor(Color.white);
            gr.drawString("Sound Effects", width/6, 60*height/100);

            //Draw Sound Effect Enable/Disable Highlight Effects
            gr.setFont(MenuFont);
            soundFXEnBtn.draw(gr);
            soundFXDisBtn.draw(gr);
            if(player.soundEnabled()){ // Highlight Enabled (SoundEffects)
               gr.setColor(Color.red);
               gr.drawString("Enabled", width/6+2, 65*height/100+2); 
            }
            else{ // Highlight Disabled (SoundEffects)
                gr.setColor(Color.red);
                gr.drawString("Disabled", width/4+2, 65*height/100+2);
            }

            //Draw Enabled in Options (SoundEffects)
            gr.setColor(Color.black);
            gr.drawString("Enabled", width/6+1, 65*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Enabled", width/6, 65*height/100);
            //Draw Disabled in Options (SoundEffects)
            gr.setColor(Color.black);
            gr.drawString("Disabled", width/4+1, 65*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Disabled", width/4, 65*height/100);

            //Draw Back in Options
            backBtn.draw(gr);
            gr.setColor(Color.BLACK);
            gr.drawString("Back", width/6+1,80*height/100+1);
            gr.setColor(Color.white);
            gr.drawString("Back", width/6, 80*height/100);
        }
    }
    
}
