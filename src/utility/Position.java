package utility;

public class Position {

	public int X;
	public int Y;

	public Position() {
		this(0, 0);
	}

	public Position(Position pos) {
		this(pos.X, pos.Y);
	}

	public Position(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public void move(int deltaX, int deltaY) {
		this.X += deltaX;
		this.Y += deltaY;
	}

}
