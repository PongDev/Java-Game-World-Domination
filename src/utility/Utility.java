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
		return Math.sqrt(Math.pow(pos1.X - pos2.X, 2) - Math.pow(pos1.Y - pos2.Y, 2));
	}
}
