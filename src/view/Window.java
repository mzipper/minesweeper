package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.GameLevel;

public class Window extends JFrame {
    
    public FlagCountPanel flagCountPanel;
    public BoardPanel boardPanel;

	private int numberofBombs = 10;

    public Window() {
		initializeComponents();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setSize(500, 325);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeComponents() {
		
		setJMenuBar(new TheMenuBar(new NewgameListener() {
			@Override
			public void newgame() {
				boardPanel.mineSweeperModel.clearBoard();
				boardPanel.mineSweeperModel.setupNewBoard(GameLevel.EASY);
				boardPanel.resetBoard();
			}
		}));
		
		boardPanel = new BoardPanel(new IntListener() {
			@Override
			public void numberEmitted(int number) {
				flagCountPanel.flagCountLabel.setText(Integer.toString(number));
			}
		});
		flagCountPanel = new FlagCountPanel(numberofBombs);
		
		setLayout(new BorderLayout());
		
		add(flagCountPanel, BorderLayout.NORTH);
		add(boardPanel, BorderLayout.CENTER);
	}
   
}