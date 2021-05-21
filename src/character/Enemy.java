package character;

import java.util.ArrayList;
import java.util.Date;

import config.Config;
import logic.GameState;
import object.ObjectManager;
import render.RenderManager;
import tower.Tower;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager;
import utility.Utility;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Weapon;

/**
 * Subclass Of Character: In Game Enemy
 */
public class Enemy extends Character {

	/**
	 * Enemy Moving Vector
	 */
	private Position movingVector = new Position(0, 0);

	/**
	 * Time Stamp Count For movingVector Is Zero Sum
	 */
	private long zeroSumMovingVectorTimestamp = 0;

	/**
	 * Time Stamp For Suspend movingVector Auto Update
	 */
	private long suspendUpdateMovingVectorTimestamp = 0;

	/**
	 * Allow Update For Moving Vector
	 */
	private boolean allowUpdateMovingVector = true;

	/**
	 * Enemy Money Drop Amount On Destroyed
	 */
	private int moneyDrop = 0;

	/**
	 * Enemy Alternative Constructor
	 * 
	 * @param imageResource
	 * @param width
	 * @param height
	 * @param name
	 * @param maxHealth
	 * @param defense
	 * @param speed
	 * @param weapon
	 * @param team
	 * @param moneyDrop
	 * @param centerPosX
	 * @param centerPosY
	 */
	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, int team, int moneyDrop, int centerPosX, int centerPosY) {
		this(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, moneyDrop,
				new Position(centerPosX, centerPosY));
	}

	/**
	 * Enemy Main Constructor
	 * 
	 * @param imageResource
	 * @param width
	 * @param height
	 * @param name
	 * @param maxHealth
	 * @param defense
	 * @param speed
	 * @param weapon
	 * @param team
	 * @param moneyDrop
	 * @param centerPos
	 */
	public Enemy(ImageResource imageResource, int width, int height, String name, int maxHealth, int defense, int speed,
			Weapon weapon, int team, int moneyDrop, Position centerPos) {
		super(imageResource, width, height, name, maxHealth, defense, speed, weapon, team, centerPos);
		this.moneyDrop = moneyDrop;
		UpdateManager.add(this);
		RenderManager.add(this);
		ObjectManager.addCharacter(this);
	}

	/**
	 * Get Walkable Moving Vector
	 * 
	 * @return
	 */
	private Position getMovingVector() {
		ArrayList<Position> movingVectorList = ((MainCharacter) ResourceManager
				.getGameObject(GameObjectResource.MAIN_CHARACTER)).getTowardMovingVector(this.getCenterPos());

		for (Position movingVector : movingVectorList) {
			if (GameState.getGameMap().isWalkable(this, (int) movingVector.X * this.getSpeed(),
					(int) movingVector.Y * this.getSpeed())) {
				return movingVector;
			}
		}
		return new Position(0, 0);
	}

	/**
	 * Move Action For Enemy
	 */
	private void move() {
		Position requestMovingVector = this.getMovingVector();

		if (!allowUpdateMovingVector && (new Date()).getTime()
				- suspendUpdateMovingVectorTimestamp >= Config.ENEMY_SUSPEND_MOVING_VECTOR_UPDATE_TIME) {
			zeroSumMovingVectorTimestamp = 0;
			allowUpdateMovingVector = true;
			suspendUpdateMovingVectorTimestamp = 0;
		}

		if (Utility.euclideanDistance(this, ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER)) > 1.5
				* Math.max(Config.TILE_W, Config.TILE_H)
				&& Utility.isZeroSumVector(requestMovingVector, movingVector)) {
			if (zeroSumMovingVectorTimestamp == 0) {
				zeroSumMovingVectorTimestamp = (new Date()).getTime();
			} else if ((new Date()).getTime()
					- zeroSumMovingVectorTimestamp >= Config.ENEMY_ZERO_SUM_MOVING_VECTOR_TIME_ALLOW) {
				allowUpdateMovingVector = false;
				suspendUpdateMovingVectorTimestamp = (new Date()).getTime();
			}
		} else {
			zeroSumMovingVectorTimestamp = 0;
			allowUpdateMovingVector = true;
			suspendUpdateMovingVectorTimestamp = 0;
		}

		if (allowUpdateMovingVector) {
			movingVector = requestMovingVector;
		}

		if (GameState.getGameMap().isWalkableAndNotCollideTower(this, (int) movingVector.X * this.getSpeed(),
				(int) movingVector.Y * this.getSpeed())) {
			this.pos.X += movingVector.X * this.getSpeed();
			this.pos.Y += movingVector.Y * this.getSpeed();
		} else {
			movingVector = new Position(0, 0);
		}
	}

	/**
	 * Enemy ZIndex
	 */
	public int getZ() {
		return Config.ZINDEX_ENEMY;
	}

	/**
	 * Is Enemy Destroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Action On Enemy Destroyed
	 */
	public void onDestroyed() {
		MainCharacter mainCharacter = (MainCharacter) ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER);

		mainCharacter.setMoney(mainCharacter.getMoney() + this.moneyDrop);
	}

	/**
	 * Is Enemy Should Remove From Update
	 */
	public boolean isRemoveFromUpdate() {
		return isDestroyed;
	}

	/**
	 * Update Enemy
	 */
	public void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				calculateDistanceFromCharacter();
				ObjectManager.collideWithBullet(this);

				double degree;
				double noDispersionDegree;
				Tower targetTower = ObjectManager.findNearestOpponentTower(this, team);
				int row = (int) (this.getCenterPos().Y / Config.TILE_H);
				int col = (int) (this.getCenterPos().X / Config.TILE_W);

				if (targetTower != null && targetTower.getManhattanDistanceFromTower(row,
						col) < ((MainCharacter) ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER))
								.getManhattanDistanceFromCharacter(row, col)) {
					degree = Math.toDegrees(Math.atan2(
							(this.getCenterPos().Y) - (targetTower.getCenterPos().Y)
									+ (Math.random() * Config.ENEMY_DISPERSION * (Math.random() <= 0.5 ? 1 : -1)),
							(targetTower.getCenterPos().X) - (this.getCenterPos().X)
									+ (Math.random() * Config.ENEMY_DISPERSION * (Math.random() <= 0.5 ? 1 : -1))));
					noDispersionDegree = Math
							.toDegrees(Math.atan2((this.getCenterPos().Y) - (targetTower.getCenterPos().Y),
									(targetTower.getCenterPos().X) - (this.getCenterPos().X)));
				} else {
					degree = Math.toDegrees(Math.atan2(
							(-GameState.getGameMap().getMapPos().Y + pos.Y + (height / 2)) - (Config.SCREEN_H / 2)
									+ (Math.random() * Config.ENEMY_DISPERSION * (Math.random() <= 0.5 ? 1 : -1)),
							(Config.SCREEN_W / 2) - (-GameState.getGameMap().getMapPos().X + pos.X + (width / 2))
									+ (Math.random() * Config.ENEMY_DISPERSION * (Math.random() <= 0.5 ? 1 : -1))));
					noDispersionDegree = Math.toDegrees(Math.atan2(
							(-GameState.getGameMap().getMapPos().Y + pos.Y + (height / 2)) - (Config.SCREEN_H / 2),
							(Config.SCREEN_W / 2) - (-GameState.getGameMap().getMapPos().X + pos.X + (width / 2))));
				}
				weapon.attack(getCenterPos(), degree);
				isTurnLeft = (noDispersionDegree > 90 || noDispersionDegree < -90) ? true : false;
				if (isTurnLeft) {
					this.weaponTurningDegree = (180 * (noDispersionDegree > 0 ? 1 : -1)) - noDispersionDegree;
				} else {
					this.weaponTurningDegree = noDispersionDegree;
				}
				this.move();
			}
		}
	}
}
