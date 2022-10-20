package client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RevieveScreen extends Thread {
	
	private ObjectInputStream objectInputStream = null;
	private JPanel jPanel = null;

	public RevieveScreen(ObjectInputStream objectInputStream, JPanel jPanel) {
		this.objectInputStream = objectInputStream;
		this.jPanel = jPanel;
		start();
	}


	@Override
	public void run(){
        
        try {                
            while(true){           
                ImageIcon imageIcon = (ImageIcon) objectInputStream.readObject();
             
                Image image = imageIcon.getImage();
                image = image.getScaledInstance(jPanel.getWidth(),jPanel.getHeight()
                                                    ,Image.SCALE_FAST);
                
                Graphics graphics = jPanel.getGraphics();
                graphics.drawImage(image, 0, 0, jPanel.getWidth(),jPanel.getHeight(),jPanel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
      } catch(ClassNotFoundException ex){
          ex.printStackTrace();
      }
 }

}
