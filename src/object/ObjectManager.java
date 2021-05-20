package object;

import java.util.ArrayList;

import config.Config;
import tower.Tower;
import utility.Utility;
import weapon.Bullet;

public class ObjectManager {

	private static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private static ArrayList<Tower> towerList = new ArrayList<Tower>();

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

	public static void collideWithBullet(GameObject obj) {
		for (int i = bulletList.size() - 1; i >= 0; i--) {
			if (bulletList.get(i).isDestroyed()) {
				bulletList.remove(i);
			} else if (isObjectCollide(obj, bulletList.get(i))) {
				if (obj.getTeam() != bulletList.get(i).getTeam()) {
					obj.dealDamage(bulletList.get(i).getDamage());
					bulletList.get(i).destroy();
				}
			}
		}
	}

	public static void addTower(Tower tower) {
		towerList.add(tower);
	}

	public static Tower findNearestOpponentTower(GameObject obj, int team) {
		Tower targetTower = null;
		int row = (int) (obj.getCenterPos().Y / Config.TILE_H);
		int col = (int) (obj.getCenterPos().X / Config.TILE_W);

		for (int i = towerList.size() - 1; i >= 0; i--) {
			if (towerList.get(i).isDestroyed()) {
				towerList.remove(i);
			}
		}
		for (Tower tower : towerList) {
			if (tower.getTeam() != team) {
				if (targetTower == null) {
					if (tower.getManhattanDistanceFromTower(row, col) != -1) {
						targetTower = tower;
					}
				} else {
					int targetTowerDistance = targetTower.getManhattanDistanceFromTower(row, col);
					int loopTowerDistance = tower.getManhattanDistanceFromTower(row, col);

					if (loopTowerDistance != -1) {
						if (loopTowerDistance < targetTowerDistance) {
							targetTower = tower;
						} else if (loopTowerDistance == targetTowerDistance
								&& Utility.euclideanDistance(tower.getCenterPos(), obj.getCenterPos()) < Utility
										.euclideanDistance(targetTower.getCenterPos(), obj.getCenterPos())) {
							targetTower = tower;
						}
					}
				}
			}
		}
		if (targetTower != null) {
			targetTower.reportDetected(obj);
		}
		return targetTower;
	}

}
