package main;

import javax.swing.JFrame;

public class MainGame {

	
	public static void main(String [] args){
		
		JFrame JF = new JFrame();
		JF.setTitle("FlappyBox");
		JF.setContentPane(new GamePanel());
		JF.setResizable(false);
		JF.pack();
		JF.setVisible(true);
		JF.setLocationRelativeTo(null);
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
