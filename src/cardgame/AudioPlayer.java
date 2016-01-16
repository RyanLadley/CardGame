/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Leulad
 */
public class AudioPlayer {
    private boolean musicEnabled = true, soundFXenabled= true;
    
    private Clip theme, starWars,  crash, click, deal, smallBlaster;
    
    private static AudioPlayer instance;
    private AudioPlayer(){
        initiateAudio();
    }
    
    public static AudioPlayer getInstance(){
        if(instance == null){
            instance = new AudioPlayer();
        }
        return instance;
    }
    
    private void initiateAudio(){
        try{
            crash= AudioSystem.getClip(); 
            theme= AudioSystem.getClip(); 
            starWars= AudioSystem.getClip();
            click= AudioSystem.getClip();
            deal = AudioSystem.getClip();
            smallBlaster = AudioSystem.getClip();
            
            crash.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/Crash.au")));
            theme.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/Theme.au")));
            starWars.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/StarWars.au")));
            click.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/Click.wav")));
            deal.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/CardDeal.wav")));
            smallBlaster.open(AudioSystem.getAudioInputStream(new File("Resources/Audio/SmallBlaster.wav")));
            
            //Volume Controls
            FloatControl CrashVolume = (FloatControl) crash.getControl(FloatControl.Type.MASTER_GAIN);
            CrashVolume.setValue(-2.0f);
            
            FloatControl ClickVolume = (FloatControl) click.getControl(FloatControl.Type.MASTER_GAIN);
            ClickVolume.setValue(-8.0f);
            
            FloatControl DealVolume = (FloatControl) deal.getControl(FloatControl.Type.MASTER_GAIN);
            DealVolume.setValue(+2.0f);
            
            FloatControl SmallBlasterVolume = (FloatControl) smallBlaster.getControl(FloatControl.Type.MASTER_GAIN);
            SmallBlasterVolume.setValue(-3.0f);
            
        }
        catch(Exception ex){
            ex.getMessage();
            System.out.println(ex);
        }  
    }
    
    public boolean musicEnabled(){
        return musicEnabled;
    }
    
    public void enableMusic(boolean enabled){
        musicEnabled = enabled;
    }
    
    public boolean soundEnabled(){
        return soundFXenabled;
    }
    
    public void enableSound(boolean enabled){
        soundFXenabled = enabled;
    }
    
    public void playThemeMusic(){
        if(musicEnabled == true){
            theme.setMicrosecondPosition(0);
            theme.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void stopThemeMusic(){
        theme.stop();
    }
    public  void playBackgroundMusic(){
        if(musicEnabled == true){
            starWars.setMicrosecondPosition(0);
            starWars.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public  void stopBackgroundMusic(){
        starWars.stop();
    }
    
    
    //Starts  the click sounds
    public void clickSound(){
        if(soundFXenabled == true){
            click.setMicrosecondPosition(0);
            click.start();
        }
    }
    
    public  void selectionDownSound(){
        if(soundFXenabled == true){
            crash.setMicrosecondPosition(0);
            crash.start();
        }
        
    }
    
    public  void selectionUpSound(){
        if(soundFXenabled == true){
            smallBlaster.setMicrosecondPosition(0);
            smallBlaster.start();
        }
    }
    
    
    //This sound is used when dealing a card
    public void dealCardSound(){
        if(soundFXenabled == true){
            deal.setMicrosecondPosition(0);
            deal.start();
        } 
    }
}
