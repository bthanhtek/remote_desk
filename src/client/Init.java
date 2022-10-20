package client;

public class Init implements Runnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Init().run();		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UIStart uiStart = new UIStart();
		uiStart.setVisible(true);
	}

}
