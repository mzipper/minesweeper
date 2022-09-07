package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame {
    

    IntListener intListener;
    
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
		
		
		
		boardPanel = new BoardPanel(new IntListener() {
			
			@Override
			public void numberEmitted(int number) {
				flagCountPanel.flagCountLabel.setText(Integer.toString(number));
			}
		});
		
		setLayout(new BorderLayout());
		
		flagCountPanel = new FlagCountPanel(numberofBombs);
		
		
		add(flagCountPanel, BorderLayout.NORTH);
		add(boardPanel, BorderLayout.CENTER);
		
	}
    
    
    
    
    
}