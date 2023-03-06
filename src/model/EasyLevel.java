package model;

public class EasyLevel implements IGameLevel {
	private final int NUMBEROFBOMBS = 10;
	private final int ROWCOUNT = 8;
	private final int COLCOUNT = 10;
	
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
