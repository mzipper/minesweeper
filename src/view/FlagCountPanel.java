package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlagCountPanel extends JPanel {
	
    public JLabel flagLabel;
    public JLabel flagCountLabel;
    
    private static final String FLAG = "\uD83D\uDEA9";
    
	public FlagCountPanel(int numberofBombs) {
		
		flagCountLabel = new JLabel();
		flagCountLabel.setText(Integer.toString(numberofBombs));
		flagLabel = new JLabel(FLAG);
		add(flagLabel);
		add(flagCountLabel);
	}
    
}
