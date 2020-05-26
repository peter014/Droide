package app.dto;

public class EnemiesDTO {

	private String type;
	private int number;

	public EnemiesDTO() {
		super();
	}

	public EnemiesDTO(String type, int number) {
		super();
		this.type = type;
		this.number = number;
	}

	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}

	public int getnumber() {
		return number;
	}

	public void setnumber(int number) {
		this.number = number;
	}



}
