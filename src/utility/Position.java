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
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(X) != Double.doubleToLongBits(other.X))
			return false;
		if (Double.doubleToLongBits(Y) != Double.doubleToLongBits(other.Y))
			return false;
		return true;
	}

}
