package utility;

public class Position {

	public double X;
	public double Y;

	public Position() {
		this(0, 0);
	}

	public Position(Position pos) {
		this(pos.X, pos.Y);
	}

	public Position(double X, double Y) {
		this.X = X;
		this.Y = Y;
	}

	public void move(double deltaX, double deltaY) {
		this.X += deltaX;
		this.Y += deltaY;
	}

}
