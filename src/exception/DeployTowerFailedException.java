package exception;

import tower.Tower;
import utility.Logger;

/**
 * Exception For Deploy Tower Failed
 */
public class DeployTowerFailedException extends Exception {

	/**
	 * Exception Serial Version UID
	 */
	private static final long serialVersionUID = 4151845649830332489L;

	/**
	 * Exception Constructor
	 * 
	 * @param tower Deploy Failed Tower
	 */
	public DeployTowerFailedException(Tower tower) {
		Logger.exception(String.format("Failed To Deploy Tower %s On Row: %d Col: %d", tower.getName(),
				tower.getTowerRow(), tower.getTowerCol()));
	}

}
