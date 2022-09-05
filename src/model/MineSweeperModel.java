package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class MineSweeperModel
{
	private Cell[][] board;
	
	private final int MAXROWCOUNT;
	private final int MAXCOLCOUNT;
	
	private int numberofBombs;
    private int rowCount;
    private int colCount;

    private List<Cell> listOfBombs = new LinkedList<Cell>();
    private int numRemainingBombsFlagged;
    
    private int numSpacesClicked;
    private Set<Cell> numSpacesclickedSet = new HashSet<Cell>();
    
    private final int numOfRightClickOptions = 2; //2 for Google. 3 for Microsoft.

    private final int[] dr8 = new int[] { -1, +1, 0, 0, -1, -1, +1, +1 };
    private final int[] dc8 = new int[] { 0, 0, -1, +1, -1, +1, -1, +1 };


    public MineSweeperModel()
    {
    	MAXROWCOUNT = 20;
    	MAXCOLCOUNT = 24;

        board = new Cell[MAXROWCOUNT][ MAXCOLCOUNT];

        for (int i = 0; i < MAXROWCOUNT; i++)
            for (int j = 0; j < MAXCOLCOUNT; j++)
                board[i][j] = new Cell(new Point(j, i));
    }


    public void SetupNewBoard(GameLevel gameLevel)
    {


        switch (gameLevel)
        {
            case EASY:
                numberofBombs = 10;
                rowCount = 8;
                colCount = 10;
                break;

            case MEDIUM:
                numberofBombs = 40;
                rowCount = 14;
                colCount = 18;
                break;

            case HARD:
                numberofBombs = 99;
                rowCount = 20;
                colCount = 24;
                break;
        }

        setNumRemainingBombsFlagged(numberofBombs);

        
		AddBombs();
        AddNumbers();
    }

    private void ClearBoard()
    {

        for (int i = 0; i < MAXROWCOUNT; i++)
        {
            for (int j = 0; j < MAXCOLCOUNT; j++)
            {
                Cell cell = board[i][j];

                cell.setCellStatus(CellStatus.BLANK);
                cell.setNumber(0);
                cell.setRightClickStatus(RightClickStatus.BLANK);
            }
        }
                
    }

	private void AddBombs()
    {

        Random random = new Random();

        int numOfPlacedBombs = 0;
        Cell cell;


        while (numOfPlacedBombs < numberofBombs)
        {

            do
            {
                cell = board[random.nextInt(rowCount)][random.nextInt(colCount)];

            } while (cell.getCellStatus().equals(CellStatus.BOMB) );
            
            cell.setCellStatus(CellStatus.BOMB);

            numOfPlacedBombs += 1;
            listOfBombs.add(cell);
        }
        System.err.printf("num of bombs: %d", numOfPlacedBombs);
    }

    private void AddNumbers()
    {


        for (Cell bombCell: listOfBombs)
        {

            int r = bombCell.getLocation().y;
            int c = bombCell.getLocation().x;

            for (int i = 0; i < dr8.length; i++)
            {

                int row = r + dr8[i];
                int col = c + dc8[i];

                if (row < 0 || col < 0 || row >= rowCount || col >= colCount) { continue; }

                Cell cell = board[row][col];
                if (cell.getCellStatus() != CellStatus.BOMB)
                {
                    cell.setCellStatus(CellStatus.NUMBER);
                    
                    cell.setNumber(cell.getNumber() + 1);
                }
            }
        }
    }

    public boolean IsGameWon()
    {
        return numSpacesClicked + numberofBombs == rowCount * colCount;
    }

    public List<Cell> LeftClick(Point location)
    {
        List<Cell> list = new LinkedList<Cell>();


        Cell clickedCell = board[location.y][location.x];
        if (clickedCell.getRightClickStatus() == RightClickStatus.BLANK)
        {
			switch (clickedCell.getCellStatus())
			{
			case BOMB:
				list.add(clickedCell);
				break;
			case NUMBER:
				list.add(clickedCell);
				numSpacesClicked += list.size();
				break;
			case BLANK:
				Set<Cell> coloredSet = new HashSet<Cell>();
				Queue<Cell> queue = new LinkedList<Cell>();

				queue.add(clickedCell);
				coloredSet.add(clickedCell);

				while (queue.size() != 0) {
					Cell cell = queue.poll();

					if (cell.getRightClickStatus() != RightClickStatus.BLANK) {
						continue;
					}
					if (!numSpacesclickedSet.contains(cell)) {
						list.add(cell);
						numSpacesclickedSet.add(cell);
					}

					if (cell.getCellStatus() == CellStatus.BLANK) {
						int r = cell.getLocation().y;
						int c = cell.getLocation().x;

						for (int i = 0; i < dr8.length; i++) {
							int row = r + dr8[i];
							int col = c + dc8[i];

							if (row < 0 || col < 0 || row >= rowCount || col >= colCount) {
								continue;
							}

							cell = board[row][col];

							if (!coloredSet.contains(cell)) {
								coloredSet.add(cell);
								queue.add(cell);
							}
						}
					}
				}
				numSpacesClicked += list.size();
				break;
			}
        }
        return list;
    }
    
    
    public RightClickStatus RightClick(Point location)
    {

        Cell cellRightClicked = board[location.y][location.x];

        RightClickStatus rightClickStatus = cellRightClicked.getRightClickStatus();

        if (rightClickStatus.equals(RightClickStatus.BLANK)) setNumRemainingBombsFlagged(getNumRemainingBombsFlagged() - 1);
        if (rightClickStatus.equals(RightClickStatus.FLAG)) setNumRemainingBombsFlagged(getNumRemainingBombsFlagged() + 1);
        
        rightClickStatus = RightClickStatus.intToEnum( (rightClickStatus.getNum() + 1) % numOfRightClickOptions );
        cellRightClicked.setRightClickStatus(rightClickStatus);

        return rightClickStatus;
    }


	public Cell[][] getBoard() {
		return board;
	}



	public List<Cell> getListOfBombs() {
		return listOfBombs;
	}


	public void setListOfBombs(List<Cell> listOfBombs) {
		this.listOfBombs = listOfBombs;
	}


	public int getNumRemainingBombsFlagged() {
		return numRemainingBombsFlagged;
	}


	public void setNumRemainingBombsFlagged(int numRemainingBombsFlagged) {
		this.numRemainingBombsFlagged = numRemainingBombsFlagged;
	}

}
