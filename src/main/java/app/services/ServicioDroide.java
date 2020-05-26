package app.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.configurations.Constantes;
import app.dto.CoordinatesDTO;
import app.dto.RadarBodyDTO;
import app.dto.ScanDTO;
import app.interfaces.ServicioDroideInterface;


@Service("ServicioCliente")
public class ServicioDroide implements ServicioDroideInterface{
	
	private List<ScanDTO> possibleCoordinates;
	
	@Override
	public CoordinatesDTO getNextPointToDestroy(RadarBodyDTO body){
		possibleCoordinates = new ArrayList<>();
		boolean closeEnemies=true;
		boolean hasToCheckDistance=false;
		for( String p : body.getProtocols()){
			switch(p) {
			  case Constantes.CLOSEST_ENEMIES:
				  closeEnemies = true;
				  hasToCheckDistance = true;
				  break;
			  case Constantes.FURTHEST_ENEMIES:
				  closeEnemies = false;
				  hasToCheckDistance = true;
				  break;
			  case Constantes.ASSIST_ALLIES:
				  assistAllies(body.getScan());
				  break;
			  case Constantes.AVOID_CROSSFIRE:
				  avoidCrossFire(body.getScan());
				  break;
			  case Constantes.PRIORITIZE_MECH:
				  prioritizeMech(body.getScan());
				  break;
			  case Constantes.AVOID_MECH:
				  avoidMech(body.getScan());
				  break;
			  default:
			    return null;
			}
		}
		if(possibleCoordinates.isEmpty()) {
			possibleCoordinates.addAll(body.getScan());
		}
		if(hasToCheckDistance) {
			if(closeEnemies) {
				return closestEnemies();
			}
			else {
				return furthestEnemies();
			}			
		}
		else {
			return possibleCoordinates.get(0).getCoordinates();
		}
	}
	
	/**
	 * Check the lowest distance to the possible coordinates, return the closest coordinate
	 * @return CoordinatesDTO
	 */
	private CoordinatesDTO closestEnemies() {
		double minDist = 10000d;
		CoordinatesDTO closestCoord = null;
		for (ScanDTO s : possibleCoordinates) {
			CoordinatesDTO coord = s.getCoordinates();
			double x = coord.getX() * coord.getX();
			double y = coord.getY() * coord.getY();
			double sqrt = Math.sqrt(x+y);
			if(sqrt < minDist) {
				minDist = sqrt;
				closestCoord = coord;
			}
		}
		return closestCoord;		
	}
	
	/**
	 * Check the highest distance to the possible coordinates, return the furthest coordinate
	 * @return CoordinatesDTO
	 */
	private CoordinatesDTO furthestEnemies() {
		double maxDist = 0d;
		CoordinatesDTO furthestCoord = null;
		for (ScanDTO s : possibleCoordinates) {
			CoordinatesDTO coord = s.getCoordinates();
			double x = coord.getX() * coord.getX();
			double y = coord.getY() * coord.getY();
			double sqrt = Math.sqrt(x+y);
			if(sqrt < 100 && sqrt > maxDist) {
				maxDist = sqrt;
				furthestCoord = coord;
			}
		}
		return furthestCoord;	
	}
	
	/**
	 * @param scan
	 * full list of scan object
	 * 
	 * check possible coordinates with allies.
	 * if another protocol was executed only checks the possible coordinates that the other one marked
	 * 
	 */
	private void assistAllies(List<ScanDTO> scan) {
		List<ScanDTO> newScan = cloneList(scan);
		for(ScanDTO s : newScan){
			if(possibleCoordinates.contains(s)) {
				if(s.getAllies() <= 0) {
					possibleCoordinates.remove(s);					
				}
			}else if(s.getAllies() > 0) {
				possibleCoordinates.add(s);
			}				
		}
	}
	
	
	/**
	 * @param scan
	 * full list of scan object
	 * 
	 * check possible coordinates without allies.
	 * if another protocol was executed only checks the possible coordinates that the other one marked
	 * 
	 */
	private void avoidCrossFire(List<ScanDTO> scan) {
		List<ScanDTO> newScan = cloneList(scan);
		for(ScanDTO s : newScan){
			if(possibleCoordinates.contains(s)) {
				if(s.getAllies() > 0) {
					possibleCoordinates.remove(s);
				}
			}
			else {
				if(s.getAllies() <= 0) {
					possibleCoordinates.add(s);
				}
			}
		}
	}
	
	/**
	 * @param scan
	 * full list of scan object
	 * 
	 * check possible coordinates with mech enemies.
	 * if another protocol was executed only checks the possible coordinates that the other one marked
	 * 
	 */
	private void prioritizeMech(List<ScanDTO> scan) {
		List<ScanDTO> newScan = cloneList(scan);
		for(ScanDTO s : newScan){
			if(possibleCoordinates.contains(s)) {
				if(!s.getEnemies().gettype().equals(Constantes.MECH)) {
					possibleCoordinates.remove(s);					
				}
			}
			else if(s.getEnemies().gettype().equals(Constantes.MECH)) {
				possibleCoordinates.add(s);
			}
		}
	}
	
	/**
	 * @param scan
	 * full list of scan object
	 * 
	 * check possible coordinates without mech enemies.
	 * if another protocol was executed only checks the possible coordinates that the other one marked
	 * 
	 */
	private void avoidMech(List<ScanDTO> scan) {
		List<ScanDTO> newScan = cloneList(scan);
		for(ScanDTO s : newScan){
			if(possibleCoordinates.contains(s) ){
				if(s.getEnemies().gettype().equals(Constantes.MECH)) {
					possibleCoordinates.remove(s);					
				}
			}
			else if(!s.getEnemies().gettype().equals(Constantes.MECH)) {
				possibleCoordinates.add(s);
			}
		}
	}
	
	
	/**
	 * @param scan
	 * full list of scan object
	 * 
	 * if another protocol was executed set the list to only check the coordinates that the other one marked
	 * 
	 */
	private List<ScanDTO> cloneList(List<ScanDTO> scan) {
		List<ScanDTO> listoTocheck = new ArrayList<>();
		if(!possibleCoordinates.isEmpty()) {
			listoTocheck.addAll(possibleCoordinates);
		}
		else {
			listoTocheck.addAll(scan);
		}
		return listoTocheck;
	}
}

