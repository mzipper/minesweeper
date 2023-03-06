package model;

public class HardLevel implements IGameLevel {
	private final int NUMBEROFBOMBS = 99;
	private final int ROWCOUNT = 20;
	private final int COLCOUNT = 24;
	
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
