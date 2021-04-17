package input;

import java.util.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import utility.Position;

public class InputManager {

	private static Map<KeyCode, KeyPressData> keyPress = new HashMap<KeyCode, KeyPressData>();
	private static boolean isLeftDown = false;
	private static boolean isRightDown = false;
	private static boolean isMouseOnScreen = false;
	private static long leftMousePressTime;
	private static long rightMousePressTime;
	private static Position mousePos = new Position();

	public static void addEventListener(Scene scene) {
		scene.setOnKeyPressed((e) -> {
			if (!keyPress.containsKey(e.getCode())) {
				keyPress.put(e.getCode(), new KeyPressData());
			}
			keyPress.get(e.getCode()).keyDown();
		});
		scene.setOnKeyReleased((e) -> {
			if (!keyPress.containsKey(e.getCode())) {
				keyPress.put(e.getCode(), new KeyPressData());
			}
			keyPress.get(e.getCode()).keyUp();
		});
		scene.setOnMousePressed((e) -> {
			if (e.getButton() == MouseButton.PRIMARY)
			{
				isLeftDown = true;
				leftMousePressTime = (new Date()).getTime();
			}
			if (e.getButton() == MouseButton.SECONDARY)
			{
				isRightDown = true;
				rightMousePressTime = (new Date()).getTime();
			}
		});
		scene.setOnMouseReleased((e) -> {
			if (e.getButton() == MouseButton.PRIMARY)
			{
				isLeftDown = false;
				leftMousePressTime = 0;
			}
			if (e.getButton() == MouseButton.SECONDARY)
			{
				isRightDown = false;
				rightMousePressTime = 0;
			}
		});
		scene.setOnMouseEntered((e) -> {
			isMouseOnScreen = true;
		});
		scene.setOnMouseExited((e) -> {
			isMouseOnScreen = false;
		});
		scene.setOnMouseMoved((e) -> {
			if (isMouseOnScreen) {
				mousePos.X = e.getX();
				mousePos.Y = e.getY();
			}
		});
	}
	
	public boolean isLeftMousePress() {
		return isLeftDown;
	}
	
	public boolean isRightMousePress() {
		return isRightDown;
	}

	public long leftMousePressDuration() {
		return isLeftMousePress() ? (new Date()).getTime() - leftMousePressTime : 0;
	}
	
	public long rightMousePressDuration() {
		return isLeftMousePress() ? (new Date()).getTime() - rightMousePressTime : 0;
	}
}
