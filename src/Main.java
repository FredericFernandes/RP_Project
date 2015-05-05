

import javax.swing.JFrame;

import graphics.MainWindow;
public class Main {


	public static void main(String[] args) {
		//Create and set up the window.
		MainWindow frame = new MainWindow("Projet RP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set up the content pane.
		frame.addComponentsToPane(frame.getContentPane());
		//Display the window.
		frame.pack();
		frame.setSize(510, 200);
		frame.setResizable(false);
		frame.setVisible(true);
	}



}
