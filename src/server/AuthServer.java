package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

import client.MsgClient;

public class AuthServer {

	private int PORT;
	private String password;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	AuthServer(String password, int PORT) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			Socket socket;	
			while (true) {				
				socket = serverSocket.accept();
				
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				String checkPass = dataInputStream.readUTF();
				
				if (checkPass.equals(password)) {				
					dataOutputStream.writeUTF("valid");				
					new InitScreenServer(socket);					
					dataOutputStream.flush();
				} else {
					dataOutputStream.writeUTF("Invalid");
					dataOutputStream.flush();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

   
}
