package server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.net.Socket;

public class InitScreenServer extends Thread {
	
	private Socket socket;
	private Robot robot;
	private Rectangle rectangle;
	
	
	public InitScreenServer(Socket socket) {
		this.socket = socket;

		start();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			
			GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice dfDevice = gEnvironment.getDefaultScreenDevice();
			
			Dimension dmDimension = Toolkit.getDefaultToolkit().getScreenSize();
			rectangle = new Rectangle(dmDimension);
			robot = new Robot(dfDevice);
			
			new SendScreen(robot, socket, rectangle);
			new ReceiveEvents(socket, robot);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
