package input;

import java.util.Date;

public class KeyPressData {

	private boolean isPress = false;
	private long pressTimestamp = 0;

	public void keyDown() {
		isPress = true;
		pressTimestamp = (new Date()).getTime();
	}

	public void keyUp() {
		isPress = false;
		pressTimestamp = 0;
	}

	public boolean isPress() {
		return isPress;
	}

	public long keyPressDuration() {
		return isPress ? (new Date()).getTime() - pressTimestamp : 0;
	}

}
