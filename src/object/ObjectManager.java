package object;

import java.util.ArrayList;

import character.Character;
import weapon.Bullet;

public class ObjectManager {

	private static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

	public static boolean isObjectCollide(GameObject gameObject, double posX, double posY) {
		return posX >= gameObject.getPos().X && posY >= gameObject.getPos().Y
				&& posX <= (gameObject.getPos().X + gameObject.getWidth() - 1)
				&& posY <= (gameObject.getPos().Y + gameObject.getHeight() - 1);
	}

	public static boolean isObjectCollide(GameObject obj1, GameObject obj2) {
		return isObjectCollide(obj1, obj2.getPos().X, obj2.getPos().Y)
				|| isObjectCollide(obj1, obj2.getPos().X + obj2.getWidth() - 1, obj2.getPos().Y)
				|| isObjectCollide(obj1, obj2.getPos().X, obj2.getPos().Y + obj2.getHeight() - 1)
				|| isObjectCollide(obj1, obj2.getPos().X + obj2.getWidth() - 1, obj2.getPos().Y + obj2.getHeight() - 1);
	}

	public static void addBullet(Bullet bullet) {
		bulletList.add(bullet);
	}

	public static void collideWithBullet(Character character) {
		for (int i = bulletList.size() - 1; i >= 0; i--) {
			if (bulletList.get(i).isDestroyed()) {
				bulletList.remove(i);
			} else if (isObjectCollide((GameObject) character, bulletList.get(i))) {
				character.dealDamage(bulletList.get(i).getDamage());
				bulletList.get(i).destroy();
			}
		}
	}

}
