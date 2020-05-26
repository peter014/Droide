package app.dto;

import java.util.List;

public class RadarBodyDTO {

	private List<String> protocols;
	private List<ScanDTO> scan;

	public RadarBodyDTO() {
		super();
	}

	public RadarBodyDTO(List<String> protocols, List<ScanDTO> scan) {
		super();
		this.protocols = protocols;
		this.scan = scan;
	}

	public List<String> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<String> protocols) {
		this.protocols = protocols;
	}

	public List<ScanDTO> getScan() {
		return scan;
	}

	public void setScan(List<ScanDTO> scan) {
		this.scan = scan;
	}


}
