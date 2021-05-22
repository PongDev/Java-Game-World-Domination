package input;

import java.util.Date;

/**
 * Key Press Data
 */
public class KeyPressData {

	/**
	 * Is Key Press
	 */
	private boolean isPress = false;

	/**
	 * Key Press Time Stamp
	 */
	private long pressTimestamp = 0;

	/**
	 * Set Key Down
	 */
	public void keyDown() {
		isPress = true;
		pressTimestamp = (new Date()).getTime();
	}

	/**
	 * Set Key Up
	 */
	public void keyUp() {
		isPress = false;
		pressTimestamp = 0;
	}

	/**
	 * Is Key Press
	 */
	public boolean isPress() {
		return isPress;
	}

	/**
	 * Key Press Duration
	 */
	public long keyPressDuration() {
		return isPress ? (new Date()).getTime() - pressTimestamp : 0;
	}

}
