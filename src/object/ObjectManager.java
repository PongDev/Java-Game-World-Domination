package object;

import java.util.ArrayList;

import character.Character;
import config.Config;
import logic.GameState;
import tower.Tower;
import utility.Utility;
import weapon.Bullet;

/**
 * Object Manager: Management Game Object System
 */
public class ObjectManager {

	/**
	 * Array List Contain Current In-Game Bullet
	 */
	private static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

	/**
	 * Array List Contain Current In-Game Tower
	 */
	private static ArrayList<Tower> towerList = new ArrayList<Tower>();

	/**
	 * Array List Contain Current In-Game Character
	 */
	private static ArrayList<Character> characterList = new ArrayList<Character>();

	/**
	 * Is Object Collide With Position X, Y
	 * 
	 * @param gameObject Object To Check
	 * @param posX       Position X
	 * @param posY       Position Y
	 */
	public static boolean isObjectCollide(GameObject gameObject, double posX, double posY) {
		return posX >= gameObject.getPos().X && posY >= gameObject.getPos().Y
				&& posX <= (gameObject.getPos().X + gameObject.getWidth() - 1)
				&& posY <= (gameObject.getPos().Y + gameObject.getHeight() - 1);
	}

	/**
	 * Is Object Collide With Each Other
	 * 
	 * @param obj1 First Object
	 * @param obj2 Second Object
	 */
	public static boolean isObjectCollide(GameObject obj1, GameObject obj2) {
		return isObjectCollide(obj1, obj2.getPos().X, obj2.getPos().Y)
				|| isObjectCollide(obj1, obj2.getPos().X + obj2.getWidth() - 1, obj2.getPos().Y)
				|| isObjectCollide(obj1, obj2.getPos().X, obj2.getPos().Y + obj2.getHeight() - 1)
				|| isObjectCollide(obj1, obj2.getPos().X + obj2.getWidth() - 1, obj2.getPos().Y + obj2.getHeight() - 1);
	}

	/**
	 * Is Tower Deployable Without Collide Other Object And In Placable Tile
	 * 
	 * @param tower Tower To Check
	 */
	public static boolean isTowerDeployable(Tower tower) {
		if (GameState.getGameMap().isPlacable(tower.getTowerRow(), tower.getTowerCol())) {
			for (Tower e : towerList) {
				if (isObjectCollide(e, tower)) {
					return false;
				}
			}
			for (Character e : characterList) {
				if (isObjectCollide(e, tower)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Add Bullet To bulletList
	 */
	public static void addBullet(Bullet bullet) {
		bulletList.add(bullet);
	}

	/**
	 * Process Game Object Is Collide With Opponent Bullet If It Is, Deal Damage
	 * Also Remove Destroyed Bullet From bulletList Between Process
	 * 
	 * @param obj Game Object To Process
	 */
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

	/**
	 * Add Tower To towerList
	 */
	public static void addTower(Tower tower) {
		towerList.add(tower);
	}

	/**
	 * Find Nearest Opponent Tower Priority By Manhattan Distance Follow By
	 * Euclidean Distance
	 * 
	 * @param obj Object To Find Nearest Opponent Tower
	 * @return Nearest Opponent Tower, Null If Not Found
	 */
	public static Tower findNearestOpponentTower(GameObject obj) {
		Tower targetTower = null;
		int team = obj.getTeam();
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
		return targetTower;
	}

	/**
	 * Add Character To characterList
	 */
	public static void addCharacter(Character character) {
		characterList.add(character);
	}

	/**
	 * Find Nearest Opponent Character Priority By Manhattan Distance Follow By
	 * Euclidean Distance
	 * 
	 * @param obj Object To Find Nearest Opponent Character
	 * @return Nearest Opponent Character, Null If Not Found
	 */
	public static Character findNearestOpponentCharacter(GameObject obj) {
		Character targetCharacter = null;
		int team = obj.getTeam();
		int row = (int) (obj.getCenterPos().Y / Config.TILE_H);
		int col = (int) (obj.getCenterPos().X / Config.TILE_W);

		for (int i = characterList.size() - 1; i >= 0; i--) {
			if (characterList.get(i).isDestroyed()) {
				characterList.remove(i);
			}
		}
		for (Character character : characterList) {
			if (character.getTeam() != team) {
				if (targetCharacter == null) {
					if (character.getManhattanDistanceFromCharacter(row, col) != -1) {
						targetCharacter = character;
					}
				} else {
					int targetTowerDistance = targetCharacter.getManhattanDistanceFromCharacter(row, col);
					int loopTowerDistance = character.getManhattanDistanceFromCharacter(row, col);

					if (loopTowerDistance != -1) {
						if (loopTowerDistance < targetTowerDistance) {
							targetCharacter = character;
						} else if (loopTowerDistance == targetTowerDistance
								&& Utility.euclideanDistance(character.getCenterPos(), obj.getCenterPos()) < Utility
										.euclideanDistance(targetCharacter.getCenterPos(), obj.getCenterPos())) {
							targetCharacter = character;
						}
					}
				}
			}
		}
		return targetCharacter;
	}

}
