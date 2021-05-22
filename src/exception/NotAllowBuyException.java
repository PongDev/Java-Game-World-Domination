package exception;

import item.Buyable;
import utility.Logger;

/**
 * Exception For Not Allow Buy Item
 */
public class NotAllowBuyException extends Exception {

	/**
	 * Exception Serial Version UID
	 */
	private static final long serialVersionUID = 4211324474592954998L;

	/**
	 * Exception Constructor
	 * 
	 * @param item Not Allow Buy Item
	 */
	public NotAllowBuyException(Buyable item) {
		Logger.exception(String.format("Item %s Is Not Allow Buy", item.getName()));
	}

}
