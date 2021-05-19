package utility;

import object.GameObject;

public class Utility {
	public static boolean isObjectCollide(GameObject gameObject, double posX, double posY) {
		return posX >= gameObject.getPos().X && posY >= gameObject.getPos().Y
				&& posX <= (gameObject.getPos().X + gameObject.getWidth() - 1)
				&& posY <= (gameObject.getPos().Y + gameObject.getHeight() - 1);
	}

	public static boolean isObjectCollide(GameObject target, GameObject bullet) {
		return isObjectCollide(bullet, target.getPos().X, target.getPos().Y)
				|| isObjectCollide(bullet, target.getPos().X + target.getWidth(), target.getPos().Y)
				|| isObjectCollide(bullet, target.getPos().X, target.getPos().Y + target.getHeight())
				|| isObjectCollide(bullet, target.getPos().X + target.getWidth(),
						target.getPos().Y + target.getHeight());
	}

	public static double euclideanDistance(Position pos1, Position pos2) {
		return Math.sqrt(Math.pow(pos1.X - pos2.X, 2) + Math.pow(pos1.Y - pos2.Y, 2));
	}

	public static double euclideanDistance(GameObject obj1, GameObject obj2) {
		return euclideanDistance(obj1.getCenterPos(), obj2.getCenterPos());
	}

	public static boolean isZeroSumVector(Position vector1, Position vector2) {
		return (vector1.X + vector2.X == 0 && vector1.Y + vector2.Y == 0);
	}
}
