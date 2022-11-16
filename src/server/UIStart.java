package server;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

import Widgets.CButton;

public class UIStart extends JFrame {


	private CButton submit;
	private int WIDTH = 500;
	private int HEIGHT = 500;
	private String FONT = "Roboto";
	private int PORT = 1234;
	private String myIp ;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	UIStart() {
		
		ListenConnection(PORT);	
	}	
	
	public void ListenConnection(int PORT) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);			
			Socket socket;	
			System.out.println("Server running");
			while (true) {				
				socket = serverSocket.accept();
				drawGUI();
				new InitScreenServer(socket);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void drawGUI() {
		setResizable(false);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle("Remote Desk Server");
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int CoordX = (objDimension.width - getWidth()) / 2;
		int CoordY = (objDimension.height - getHeight()) / 2;
		setLocation(CoordX, CoordY);

					
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, panel, 50, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -50, SpringLayout.EAST, getContentPane());
		panel.setBackground(Color.white);
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(400, 300));
		panel.setMaximumSize(new Dimension(300, 300));
		panel.setPreferredSize(new Dimension(400, 300));
		

		JLabel ipJLabel = new JLabel("IP : ");
		ipJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		ipJLabel.setFont(new Font(FONT, 0, 13));
		ipJLabel.setMaximumSize(new Dimension(300, 26));
		ipJLabel.setMinimumSize(new Dimension(300, 26));
		ipJLabel.setPreferredSize(new Dimension(300, 26));
		panel.add(ipJLabel);
		
		
		
		JTextField ip = new JTextField();
		panel.add(ip);
		ip.setBackground(new Color(245, 245, 245));
		ip.setColumns(1);
		ip.setFont(new Font(FONT, 0, 14));
		ip.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12));
		ip.setMaximumSize(new Dimension(300, 45));
		ip.setMinimumSize(new Dimension(300, 45));
		ip.setPreferredSize(new Dimension(300, 45));

		
		JLabel lPassword = new JLabel("Port : ");
		panel.add(lPassword);
		lPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		lPassword.setFont(new Font(FONT, 0, 13));
		lPassword.setMaximumSize(new Dimension(300, 26));
		lPassword.setMinimumSize(new Dimension(300, 26));
		lPassword.setPreferredSize(new Dimension(300, 26));
		lPassword.setAlignmentX(0.5f);
		
		
		panel.add(Box.createVerticalStrut(12));
		
		JTextField password = new JTextField();
		panel.add(password);
		password.setText(String.valueOf(PORT));
		password.setBackground(new Color(245, 245, 245));
		password.setColumns(1);
		password.setFont(new Font(FONT, 0, 14));
		password.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12));
		password.setMaximumSize(new Dimension(300, 45));
		password.setMinimumSize(new Dimension(300, 45));
		password.setPreferredSize(new Dimension(300, 45));
		panel.add(Box.createVerticalStrut(12));
		
		

		

		CButton button = new CButton();
		button.setBackground(new java.awt.Color(33, 150, 243));
		button.setText("Chat");
		button.setAlignmentX(0.5F);
		button.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
		button.setMaximumSize(new Dimension(300, 45));
		button.setMinimumSize(new Dimension(300, 45));
		button.setMouseHover(new Color(66, 165, 245));
		button.setMousePress(new Color(33, 150, 243));
		button.setPreferredSize(new Dimension(300, 45));
		button.setWarnaBackground(new Color(33, 150, 243));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Thread thread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new MsgServer();
						}
					});
		               thread.start();

			}
		});
		Component verticalStrut = Box.createVerticalStrut(12);
		panel.add(verticalStrut);

		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon("assets/logo.png").getImage().getScaledInstance(300, 150, Image.SCALE_DEFAULT));
		JLabel picLabel = new JLabel(imageIcon);
		getContentPane().add(picLabel);
		springLayout.putConstraint(SpringLayout.NORTH, picLabel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, picLabel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, picLabel, -10, SpringLayout.EAST, getContentPane());

		
		try (final DatagramSocket socket = new DatagramSocket()) {
			try {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				myIp = socket.getLocalAddress().getHostAddress();
			
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(true);
		ip.setText(myIp);
	}
	



}
