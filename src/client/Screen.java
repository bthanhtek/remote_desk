package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

public class Screen extends JFrame implements Runnable {

	private Socket socket;
	private JDesktopPane jDesktopPane;
	private JInternalFrame jInternalFrame;
	private JPanel panel;

	public Screen(Socket socket) {
		this.socket = socket;
		jDesktopPane = new JDesktopPane();
		jInternalFrame = new JInternalFrame("Client Screen", true, true, true, true);
		panel = new JPanel();
	}

	public void drawGUI() {
		
		 this.add(jDesktopPane,BorderLayout.CENTER);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
         this.setVisible(true);
         
         jInternalFrame.setLayout(new BorderLayout());
         jInternalFrame.getContentPane().add(panel ,BorderLayout.CENTER);
         jInternalFrame.setSize(100,100);
         jDesktopPane.add(jInternalFrame);
         try {
           
        	 jInternalFrame.setMaximum(true);
         } catch (PropertyVetoException ex) {
             ex.printStackTrace();
         }
         
         panel.setFocusable(true);
         jInternalFrame.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream iObjectInputStream = null;
		Rectangle rectangle = null;
		drawGUI();
		try {

			iObjectInputStream = new ObjectInputStream(socket.getInputStream());
			rectangle = (Rectangle) iObjectInputStream.readObject();
			
			
			new RevieveScreen(iObjectInputStream, panel);
			new SendEvents(socket, panel, rectangle);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
}
