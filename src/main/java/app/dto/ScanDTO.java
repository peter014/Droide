package app.dto;

public class ScanDTO {

	private CoordinatesDTO coordinates;
	private EnemiesDTO enemies;
	private int allies;

	public ScanDTO() {
		super();
	}

	public ScanDTO(CoordinatesDTO coordinates, EnemiesDTO enemies, int allies) {
		super();
		this.coordinates = coordinates;
		this.enemies = enemies;
		this.allies = allies;
	}

	public ScanDTO(CoordinatesDTO coordinates, EnemiesDTO enemies) {
		super();
		this.coordinates = coordinates;
		this.enemies = enemies;
		this.allies = 0;
	}

	public CoordinatesDTO getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(CoordinatesDTO coordinates) {
		this.coordinates = coordinates;
	}

	public EnemiesDTO getEnemies() {
		return enemies;
	}

	public void setEnemies(EnemiesDTO enemies) {
		this.enemies = enemies;
	}

	public int getAllies() {
		return allies;
	}

	public void setAllies(int allies) {
		this.allies = allies;
	}

}
