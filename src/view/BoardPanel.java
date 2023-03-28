package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.*;

public class BoardPanel extends JPanel {
	
	public MineSweeperModel mineSweeperModel;
	


    public IGameLevel boardIGameLevel;

    public boolean isGameStarted = false;
    
    
    public TagJButton[][] buttons;
    
    public IntListener intListener;
    
    
    MouseListener mouseListener;
	
	public BoardPanel(IntListener intListener, IGameLevel iGameLevel) {
		this.intListener = intListener;

        boardIGameLevel = iGameLevel;
		
		
		buttons = new TagJButton[boardIGameLevel.getRowCount()][boardIGameLevel.getColCount()];
		
		mouseListener = new MouseClicked();
		
		setLayout(new GridLayout(boardIGameLevel.getRowCount(), boardIGameLevel.getColCount()));
		
		for (int i = 0; i < boardIGameLevel.getRowCount(); i++)
			for (int j = 0; j < boardIGameLevel.getColCount(); j++) {
				buttons[i][j] = new TagJButton(new Point(j, i));
				(buttons[i][j]).addMouseListener(mouseListener);
				
				buttons[i][j].setBackground(Color.LIGHT_GRAY);
				
				add(buttons[i][j]);
				
			}
		
		mineSweeperModel = new MineSweeperModel();
		
		
		mineSweeperModel.setupNewBoard(boardIGameLevel);
		
		//Test_ViewBoard();
	}
	
	

	public int getRowCount() {
		return boardIGameLevel.getRowCount();
	}


	public int getColCount() {
		return boardIGameLevel.getColCount();
	}


	public int getNumberofBombs() {
		return boardIGameLevel.getNumberOfBombs();
	}



	public IGameLevel getBoardGameLevel() {
		return boardIGameLevel;
	}

	public Boolean getIsGameStarted() {
		return isGameStarted;
	}

	public void setIsGameStarted(Boolean isGameStarted) {
		this.isGameStarted = isGameStarted;
	}
//}

@SuppressWarnings("serial")
class TagJButton<T> extends JButton {
	private T tag;

	public TagJButton(T tag) {
		super();
		this.tag = tag;
	}

	public T getTag() {
		return tag;
	}
}

private void test_ViewBoard()
{

    Cell cell;
    CellStatus cStatus;

    for (int i = 0; i < boardIGameLevel.getRowCount(); i++)
        for (int j = 0; j < boardIGameLevel.getColCount(); j++)
        {
            Cell board[][] = mineSweeperModel.getBoard();
        	cell = board[i][j];
            cStatus = cell.getCellStatus();

            TagJButton b = buttons[i][j];

            switch (cStatus)
            {
                case BLANK:
                    b.setText("");

                    break;
                case BOMB:
                    b.setText(Icon.BOMB);

                    break;

                case NUMBER:
                    b.setText(Integer.toString(cell.getNumber()));

                    break;
            }

        }
}

public void resetBoard() {
	for (int i = 0; i < boardIGameLevel.getRowCount(); i++)
		for (int j = 0; j < boardIGameLevel.getColCount(); j++) {
			buttons[i][j].setEnabled(true);
			buttons[i][j].addMouseListener(mouseListener);
			buttons[i][j].setText("");
			buttons[i][j].setForeground(Color.BLACK);
			buttons[i][j].setBackground(Color.LIGHT_GRAY);
		}
}

class MouseClicked extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			TagJButton button = (TagJButton) e.getSource();
			Point p = (Point) button.getTag();
			
			System.err.println(p);
			System.err.println("Left-click on button");
			
			List<Cell> cells = mineSweeperModel.leftClick(p);
			
			for (Cell cell : cells)
            {
				TagJButton cellButton = buttons[cell.getLocation().y][cell.getLocation().x];

                if (cell.getRightClickStatus() == RightClickStatus.BLANK)
                {
                    if (cell.getCellStatus() == CellStatus.BOMB)
                    {
                        
                        cellButton.setText(Icon.BOMB);
                        System.err.println("GAME OVER. Lost...");

                        for (Cell bombCell : mineSweeperModel.getListOfBombs())
                        {
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setBackground(Color.RED);
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setText(Icon.BOMB);
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setEnabled(false);
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].removeMouseListener(this);
                        }

                        for (int i = 0; i < boardIGameLevel.getRowCount(); i++)
                            for (int j = 0; j < boardIGameLevel.getColCount(); j++) {
                            	buttons[i][j].setEnabled(false);
                            	buttons[i][j].removeMouseListener(this);
                            }
                        

                    }
                    else if (cell.getCellStatus() == CellStatus.NUMBER)
                    {

                        switch (cell.getNumber())
                        {

                            case 1:
                                cellButton.setForeground(Color.BLUE);
                                break;
                            case 2:
                                cellButton.setForeground(Color.GREEN);
                                break;
                            case 3:
                                cellButton.setForeground(Color.RED);
                                break;
                            case 4:
                                cellButton.setForeground(Color.MAGENTA.darker());//Should be Purple
                                break;
                        }
                        cellButton.setText(Integer.toString(cell.getNumber() ));
                    }
                    cellButton.setBackground(Color.WHITE);
                    
                    cellButton.setEnabled(false);
                    cellButton.removeMouseListener(this);
                    if (mineSweeperModel.isGameWon())
                    {
                        
                        System.err.println("YOU WON!");

                        for (Cell bombCell : mineSweeperModel.getListOfBombs())
                        {
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setBackground(Color.YELLOW);
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setText(Icon.BOMB);
                            
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].setEnabled(false);
                            buttons[bombCell.getLocation().y][bombCell.getLocation().x].removeMouseListener(this);
                        }
                    }
                }

            }
			
			
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			
			TagJButton button = (TagJButton) e.getSource();
			Point p = (Point) button.getTag(); 
            
			System.err.println(p);
			System.err.println("Right-click on button");
			RightClickStatus rightClickStatus = mineSweeperModel.rightClick(p);

                switch (rightClickStatus)
                {
                    case BLANK:
                        button.setText("");
                        button.setBackground(Color.LIGHT_GRAY);
                        button.setForeground(Color.BLACK);

                        intListener.numberEmitted(mineSweeperModel.getNumRemainingBombsFlagged());

                        break;
                    case FLAG:
                        button.setText(Icon.FLAG);
                        button.setBackground(Color.ORANGE.brighter());
                        button.setForeground(Color.RED);

                        intListener.numberEmitted(mineSweeperModel.getNumRemainingBombsFlagged());

                        break;
                    case QMARK:
                        button.setText(Icon.QMARK);
                        button.setBackground(Color.GREEN.brighter());
                        button.setForeground(Color.GREEN.darker());
                        break;
                }
			
		}
	}
}
	
	
}
