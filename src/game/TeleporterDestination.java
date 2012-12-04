package game;

public class TeleporterDestination extends TeleporterSource {
	private Lander lander;
	private Planet planet;

	public TeleporterDestination(Lander lander, Planet planet, Vector position, MapManager mapManager) {
		super(position, mapManager);
		this.lander = lander;
		this.planet = planet;
	}

	@Override
	protected void reachedMaxSize() {
		float angleOnPlanet = getPosition().subtract(planet.getPosition()).angle();
		lander.placeOnPlanet(angleOnPlanet, planet);
	}
}
