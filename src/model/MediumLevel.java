package model;

public class MediumLevel implements IGameLevel {
	private final int NUMBEROFBOMBS = 40;
	private final int ROWCOUNT = 14;
	private final int COLCOUNT = 18;
	
	@Override
	public int getNumberOfBombs() {
		return NUMBEROFBOMBS;
	}

	@Override
	public int getRowCount() {
		return ROWCOUNT;
	}

	@Override
	public int getColCount() {
		return COLCOUNT;
	}
}
