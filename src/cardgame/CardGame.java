
package cardgame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

public class CardGame {
    
    
    public static void main(String[] args) throws IOException{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width, 
            height = screenSize.height;
        
        JFrame programFrame = new JFrame();
        programFrame.add(new LoadingScreen(width, height));
        
        programFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        programFrame.setUndecorated(true);
        programFrame.setResizable(false);
        programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        programFrame.setVisible(true);
        
        /*MenuBackend back =*/new MenuBackend(new MenuUI(width, height), programFrame).startMainMenu();
    }
    
    
    
    
    
    
    
}
