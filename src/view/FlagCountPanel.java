package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlagCountPanel extends JPanel {
	
    public JLabel flagLabel;
    public JLabel flagCountLabel;
    
    
	public FlagCountPanel(int numberofBombs) {
		
		flagCountLabel = new JLabel();
		flagCountLabel.setText(Integer.toString(numberofBombs));
		flagLabel = new JLabel(Icon.FLAG);
		add(flagLabel);
		add(flagCountLabel);
	}
    
}
