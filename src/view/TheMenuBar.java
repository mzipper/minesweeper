package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TheMenuBar extends JMenuBar {
	
	private JMenu mainMenu;
	private JMenuItem newGameItem;
	
	private NewgameListener newgameListener;

	public TheMenuBar(NewgameListener newgameListener) {
		this.newgameListener = newgameListener;
		
		mainMenu = new JMenu("Main Menu");
		newGameItem = new JMenuItem("New Game");
		
		mainMenu.add(newGameItem);
		
		add(mainMenu);
		
		newGameItem.addActionListener(new MainMenuListener());
	}
	
	class MainMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newgameListener.newgame();
		}
	}
	
}
