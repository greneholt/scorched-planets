package game;

import java.awt.Color;

public class TeleporterSource extends Shockwave {
	private static final float BLAST_RADIUS = 30;
	private static final int CONTRACT_STEPS = 20;
	private static final int EXPAND_STEPS = 10;
	private static final Color COLOR = new Color(0x42E0FF);
	
	public TeleporterSource(Vector position, MapManager mapManager) {
		super(position, mapManager);
	}

	@Override
	protected float getBlastRadius() {
		return BLAST_RADIUS;
	}

	@Override
	protected Color getColor() {
		return COLOR;
	}

	@Override
	protected int getContractSteps() {
		return CONTRACT_STEPS;
	}

	@Override
	protected int getExpandSteps() {
		return EXPAND_STEPS;
	}

	@Override
	protected void reachedMaxSize() {
		// do nothing
	}
}
