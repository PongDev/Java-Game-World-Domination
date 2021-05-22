package input;

import java.util.*;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import utility.Position;

/**
 * Input Manager: Management Input System
 */
public class InputManager {

	/**
	 * Map To Store Key Press
	 */
	private static Map<KeyCode, KeyPressData> keyPress = new HashMap<KeyCode, KeyPressData>();

	/**
	 * Map To Store Key Click
	 */
	private static Map<KeyCode, Boolean> keyClick = new HashMap<KeyCode, Boolean>();

	/**
	 * ArrayList Contain Registered Inputable Object
	 */
	private static ArrayList<Inputable> inputableObject = new ArrayList<Inputable>();

	/**
	 * Is Left Mouse Down
	 */
	private static boolean isLeftDown = false;

	/**
	 * Is Left Mouse Click
	 */
	private static boolean isLeftClick = false;

	/**
	 * Is Right Mouse Down
	 */
	private static boolean isRightDown = false;

	/**
	 * Is Right Mouse Click
	 */
	private static boolean isRightClick = false;

	/**
	 * Is Mouse On Screen
	 */
	private static boolean isMouseOnScreen = false;

	/**
	 * Left Mouse Press Time Stamp
	 */
	private static long leftMousePressTime;

	/**
	 * Right Mouse Press Time Stamp
	 */
	private static long rightMousePressTime;

	/**
	 * Mouse Position
	 */
	private static Position mousePos = new Position();

	/**
	 * Add Event Listener To Scene
	 * 
	 * @param scene Scene To Add Event Listener
	 */
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
			keyClick.put(e.getCode(), true);
		});
		scene.setOnMousePressed((e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				isLeftDown = true;
				leftMousePressTime = (new Date()).getTime();
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				isRightDown = true;
				rightMousePressTime = (new Date()).getTime();
			}
		});
		scene.setOnMouseReleased((e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				isLeftDown = false;
				leftMousePressTime = 0;
				isLeftClick = true;
			}
			if (e.getButton() == MouseButton.SECONDARY) {
				isRightDown = false;
				rightMousePressTime = 0;
				isRightClick = true;
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
		scene.setOnMouseDragged((e) -> {
			if (isMouseOnScreen) {
				mousePos.X = e.getX();
				mousePos.Y = e.getY();
			}
		});
	}

	/**
	 * Is Key Press
	 * 
	 * @param keyCode Key To Check
	 */
	public static boolean isKeyPress(KeyCode keyCode) {
		return keyPress.containsKey(keyCode) ? keyPress.get(keyCode).isPress() : false;
	}

	/**
	 * Is Key Click
	 * 
	 * @param keyCode Key To Check
	 */
	public static boolean isKeyClick(KeyCode keyCode) {
		return keyClick.containsKey(keyCode) ? true : false;
	}

	/**
	 * Is Left Mouse Press
	 */
	public static boolean isLeftMousePress() {
		return isLeftDown;
	}

	/**
	 * Is Left Mouse Click
	 */
	public static boolean isLeftMouseClick() {
		return isLeftClick;
	}

	/**
	 * Is Right Mouse Press
	 */
	public static boolean isRightMousePress() {
		return isRightDown;
	}

	/**
	 * Is Right Mouse Click
	 */
	public static boolean isRightMouseClick() {
		return isRightClick;
	}

	/**
	 * Left Mouse Press Duration
	 */
	public static long leftMousePressDuration() {
		return isLeftMousePress() ? (new Date()).getTime() - leftMousePressTime : 0;
	}

	/**
	 * Right Mouse Press Duration
	 */
	public static long rightMousePressDuration() {
		return isLeftMousePress() ? (new Date()).getTime() - rightMousePressTime : 0;
	}

	/**
	 * Add Inputable Object To inputableObject ArrayList
	 * 
	 * @param obj Object To Add
	 */
	public static void addInputableObject(Inputable obj) {
		inputableObject.add(obj);
	}

	/**
	 * Process Inputable Object In inputableObject Array List And Cleanup Click Data
	 */
	public static void processInputableObject() {
		for (Inputable object : inputableObject) {
			object.processInput();
		}
		isLeftClick = false;
		isRightClick = false;
		keyClick.clear();
	}

	/**
	 * Get Current Mouse Position
	 * 
	 * @return Current Mouse Position
	 */
	public static Position getMousePos() {
		return mousePos;
	}

}
