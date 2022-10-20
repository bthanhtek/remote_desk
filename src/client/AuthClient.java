package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.MsgServer;

public class AuthClient implements Runnable {

	private Socket socket;
	private String password;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private String ip;

	AuthClient(Socket socket, String password, String ip) {
		this.socket = socket;
		this.password = password;
		this.ip = ip;
		this.run();
	}
	
	
	@Override
	public void run() {

		// TODO Auto-generated method stub
		
		Screen screen = new Screen(socket);
		screen.run();
		screen.setVisible(true);
//		try {

//			dataInputStream = new DataInputStream(socket.getInputStream());
//			dataOutputStream = new DataOutputStream(socket.getOutputStream());
//
//			dataOutputStream.writeUTF(password);
//
//			String checkPass = dataInputStream.readUTF();			
//			
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
