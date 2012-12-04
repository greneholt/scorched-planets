package game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;


/*
 * Explosions are created at the point of impact and added to the scene. They render the animation of the explosion, and cause damage.
 */
public class Explosion extends Shockwave {
	static final Color COLOR = new Color(0xFC410D);
	static final int CONTRACT_STEPS = 30;
	static final int EXPAND_STEPS = 10;
	private Player causedBy;
	private float blastRadius;
	private float yield;

	public Explosion(Player causedBy, Vector position, float blastRadius, float yield, MapManager mapManager) {
		super(position, mapManager);
		this.causedBy = causedBy;
		this.blastRadius = blastRadius;
		this.yield = yield;
	}

	@Override
	protected float getBlastRadius() {
		return blastRadius;
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
		List<Lander> landersTemp = new LinkedList<Lander>(getMapManager().getLanders());
		for (Lander lander : landersTemp) {
			float distance = Vector.distanceBetween(lander.getPosition(), getPosition());

			if (distance < blastRadius) {
				int damage = (int) ((1 - (distance / blastRadius) * (distance / blastRadius)) * yield);

				lander.damage(damage, causedBy);
			}
		}
	}
}
