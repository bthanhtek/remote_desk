package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IllegalFormatWidthException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Widgets.CButton;
import utils.InfoFile;

public class MsgClient extends JFrame {
	private JTextArea jTextArea;
	private CButton sendButton, sendFile;
	private JTextField inputMess;
	private static DataInputStream reviceMess;
	private static DataOutputStream sendMess;
	private static Socket socket;
	private final int WIDTH = 500;
	private final int HEIGHT = 400;
	private Box vertical = Box.createVerticalBox();
	private JPanel screen;
	private JScrollPane scrollPane;
	private JFileChooser fc;
	private File file;
	public ArrayList<InfoFile> listFile = new ArrayList<InfoFile>();
	public int index;

//	public SendMess (Socket socket) {
//		this.socket = socket;
//		start();
//		
//	}
//	
	public MsgClient(String ip) {
		// TODO Auto-generated constructor stub
		screen = new JPanel();
		scrollPane = new JScrollPane(screen);
		drawUI();
		try {
			socket = new Socket(ip, 6001);
			reviceMess = new DataInputStream(socket.getInputStream());
			sendMess = new DataOutputStream(socket.getOutputStream());
			screen.setLayout(new BorderLayout());

			while (true) {
				reviceMsg(reviceMess);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reviceMsg(DataInputStream dis) {
		System.out.println("Waiting for command");
		int command;
		try {
			command = dis.readInt();
			System.out.println("client New command: " + command);
			switch (command) {
			case 0: {
				String msg = reviceMess.readUTF();
				JPanel panel = formatLabel(msg);

				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);
				panel.addMouseListener(getEvent(msg));
				vertical.add(Box.createVerticalStrut(15));
				screen.add(vertical, BorderLayout.PAGE_START);

				validate();
				break;
			}
			case 1: {
				reciveFile();
				System.out.println("sendfile");
				break;
			}
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawUI() {

		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle("Mess Client");
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		screen.setAutoscrolls(true);
		getContentPane().setBackground(new Color(242, 242, 242));

		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -80, SpringLayout.SOUTH, getContentPane());
		scrollPane.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		scrollPane.setMinimumSize(new Dimension(WIDTH, HEIGHT));

		sendButton = new CButton();
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendMessger(inputMess.getText());
				inputMess.setText("");
			}
		});
		sendButton.setText("Send");
		sendButton.setWarnaBackground(new Color(33, 150, 243));
		sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sendButton.setMaximumSize(new Dimension(80, 40));
		sendButton.setMinimumSize(new Dimension(80, 40));
		sendButton.setPreferredSize(new Dimension(80, 40));
		springLayout.putConstraint(SpringLayout.EAST, sendButton, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, sendButton, -25, SpringLayout.SOUTH, getContentPane());

		sendFile = new CButton();
		sendFile.setText("file");
		sendFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendFile();
			}
		});
		sendFile.setWarnaBackground(new Color(33, 150, 243));
		sendFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sendFile.setMaximumSize(new Dimension(80, 40));
		sendFile.setMinimumSize(new Dimension(80, 40));
		sendFile.setPreferredSize(new Dimension(80, 40));
		springLayout.putConstraint(SpringLayout.EAST, sendFile, -100, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, sendFile, -25, SpringLayout.SOUTH, getContentPane());

		inputMess = new JTextField();
		inputMess.setBackground(Color.white);
		inputMess.setColumns(1);
		inputMess.setFont(new Font("Robot", 0, 14));
		inputMess.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12));
		inputMess.setMaximumSize(new Dimension(300, 40));
		inputMess.setMinimumSize(new Dimension(300, 40));
		inputMess.setPreferredSize(new Dimension(300, 40));

		springLayout.putConstraint(SpringLayout.WEST, inputMess, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, inputMess, -25, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, inputMess, -200, SpringLayout.EAST, getContentPane());

		getContentPane().add(sendFile);
		getContentPane().add(sendButton);
		getContentPane().add(inputMess);
		getContentPane().add(scrollPane);
		setVisible(true);
	}

	public static JPanel formatLabel(String out) {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel output = new JLabel(
				"<html><p style='width: auto;border-radius: 100px; padding: 7px 12px'>" + out + "</p></html>");
		LineBorder line = new LineBorder(new Color(33, 150, 243), 2, true);
		output.setFont(new Font("Tahoma", Font.PLAIN, 13));
		output.setBackground(new Color(33, 150, 243));
		output.setOpaque(true);
		output.setBorder(line);
		panel.add(output);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));

		panel.add(time);

		return panel;

	}

	public void sendMessger(String ms) {

		try {

			JPanel p2 = formatLabel(ms);

			screen.setLayout(new BorderLayout());

			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			screen.add(vertical, BorderLayout.PAGE_START);

			sendMess.writeInt(EnumCommands.SEND_MSG.getAbbrev());
			sendMess.writeUTF(ms);

			this.repaint();
			this.invalidate();
			this.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean chooseFile() {
		fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			return true;

		} else {
			return false;
		}

	}

	public void sendFile() {
		if (chooseFile() && file.isAbsolute()) {
			sendMessger(file.getName());
			try {
				FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
				byte[] fileName = file.getName().getBytes();
				byte[] fileContentBytes = new byte[(int) file.length()];

				fileInputStream.read(fileContentBytes);

				sendMess.writeInt(EnumCommands.SEND_FILE.getAbbrev());

				sendMess.writeInt(fileName.length);
				sendMess.write(fileName);

				sendMess.writeInt(fileContentBytes.length);
				sendMess.write(fileContentBytes);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void dowloadFile(String nameString, byte[] fileContent, File save) {
		try {

			File fileDowloadFile = new File(save.getAbsolutePath() + "/" + nameString);
			FileOutputStream fileOutputStream = new FileOutputStream(fileDowloadFile);

			fileOutputStream.write(fileContent);

			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reciveFile() {

		int fileContentLength;
		int fileNameLength;
		try {
			fileNameLength = reviceMess.readInt();

			if (fileNameLength > 0) {
				byte[] fileName = new byte[fileNameLength];
				reviceMess.readFully(fileName, 0, fileName.length);
				String nameString = new String(fileName);

				fileContentLength = reviceMess.readInt();

				if (fileContentLength > 0) {

					byte[] fileContent = new byte[fileContentLength];

					listFile.add(new InfoFile(index++, nameString, fileContent));

					reviceMess.readFully(fileContent, 0, fileContentLength);

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MouseListener getEvent(String sms) {
		return new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				for (InfoFile infoFile : listFile) {
					if (sms.equals(infoFile.nameFileString)) {
						int reply = JOptionPane.showConfirmDialog(null,
								"Bạn có muốn tải file : " + infoFile.nameFileString + " không?", "Download",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							JFileChooser chooser = new JFileChooser();
							chooser.setCurrentDirectory(new java.io.File("."));
							chooser.setDialogTitle("Select folder");
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							chooser.setAcceptAllFileFilterUsed(false);

							if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
								System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
								System.out
										.println("InfoFile : " + infoFile.idFileString + "-" + infoFile.nameFileString);
								System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
								dowloadFile(infoFile.nameFileString, infoFile.data, chooser.getSelectedFile());
							}
						} else {
							System.out.println("Out");

						}

					}
				}
			}
		};

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new MsgClient("localhost");

	}

}
