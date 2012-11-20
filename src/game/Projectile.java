package game;

public abstract class Projectile extends MovableObject implements Renderable {
	protected GameController gameController;
	protected Lander firedBy;
	
	public Projectile(Lander firedBy, Vector position, Vector velocity, GameController gameController) {
		super(position, velocity);
		this.firedBy = firedBy;
		this.gameController = gameController;
	}
}
