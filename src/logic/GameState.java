package logic;

public class GameState {

	public enum Screen {
		Menu, Setting, Playing
	}

	private static Screen screen = Screen.Menu;

	public static Screen getState() {
		return GameState.screen;
	}

	public static void setState(Screen screen) {
		GameState.screen = screen;
	}

}
