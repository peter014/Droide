package app.interfaces;

import app.dto.CoordinatesDTO;
import app.dto.RadarBodyDTO;

public interface ServicioDroideInterface {
	
	public CoordinatesDTO getNextPointToDestroy(RadarBodyDTO body);

}
