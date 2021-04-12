package logic;

public class GameState {

	public enum State {
		Menu, Setting, Playing
	}

	private static State state = State.Menu;

	public static State getState() {
		return state;
	}

	public static void setState(State state) {
		GameState.state = state;
	}

}
