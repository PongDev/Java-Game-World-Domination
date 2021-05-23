package utility;

/**
 * Position Class: Use For Store Double Precision Position X, Y
 */
public class Position {

	/**
	 * X Position
	 */
	public double X;

	/**
	 * Y Position
	 */
	public double Y;

	/**
	 * Position Alternative Constructor - Set X=0, Y=0
	 */
	public Position() {
		this(0, 0);
	}

	/**
	 * Position Cloning Constructor
	 * 
	 * @param pos Position To Clone
	 */
	public Position(Position pos) {
		this(pos.X, pos.Y);
	}

	/**
	 * Position Alternative Constructor
	 * 
	 * @param X X Position
	 * @param Y Y Position
	 */
	public Position(double X, double Y) {
		this.X = X;
		this.Y = Y;
	}

	/**
	 * Move Position With Delta X, Delta Y
	 * 
	 * @param deltaX Shift X Position Value
	 * @param deltaY Shift Y Position Value
	 */
	public void move(double deltaX, double deltaY) {
		this.X += deltaX;
		this.Y += deltaY;
	}

	/**
	 * Is Position Equals To Each Other
	 */
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
