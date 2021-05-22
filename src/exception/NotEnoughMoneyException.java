package exception;

import utility.Logger;

/**
 * Exception For Not Enough Money To Buy
 */
public class NotEnoughMoneyException extends Exception {

	/**
	 * Exception Serial Version UID
	 */
	private static final long serialVersionUID = 4447249380993205690L;

	/**
	 * Exception Constructor
	 * 
	 * @param money Current Money
	 * @param cost  Item Cost
	 */
	public NotEnoughMoneyException(int money, int cost) {
		Logger.exception(String.format("Money Not Enough: Current Money: %d Item Cost: %d Need More: %d", money, cost,
				cost - money));
	}

}
