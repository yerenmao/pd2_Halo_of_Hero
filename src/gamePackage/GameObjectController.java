package gamePackage;

import java.awt.Graphics;
import java.util.LinkedList;

import assetsPackage.Block;
import assetsPackage.Door;
import assetsPackage.Floor;
import assetsPackage.MovingWall;
import assetsPackage.Shop;
import assetsPackage.Wall;
import bulletPackage.NormalBullet;
import effectPackage.Trail;
import enemyPackage.Enemy;
import pickUpPackage.PickUp;
import playerPackage.Player;

public class GameObjectController {
	
	public LinkedList<Player> playerList = new LinkedList<Player>();
	public LinkedList<Enemy> enemyList = new LinkedList<Enemy>();
	public LinkedList<NormalBullet> playerBulletList = new LinkedList<NormalBullet>();
	public LinkedList<NormalBullet> enemyBulletList = new LinkedList<NormalBullet>();
	public LinkedList<Wall> wallList = new LinkedList<Wall>();
	public LinkedList<Floor> floorList = new LinkedList<Floor>();
	public LinkedList<Door> doorList = new LinkedList<Door>();
	public LinkedList<Trail> trailList = new LinkedList<Trail>();
	public LinkedList<PickUp> pickUpList = new LinkedList<PickUp>();
	public LinkedList<Block> blockList = new LinkedList<Block>();
	public LinkedList<MovingWall> movingWallList = new LinkedList<MovingWall>();
	public LinkedList<Shop> shopList = new LinkedList<Shop>();
	
	public void tick() {
		tickWall();
		tickFloor();
		tickDoor();
		tickEnemy();
		tickBullet();
		tickTrail();
		tickPickUp();
		tickBlock();
		tickMovingWall();
		tickShop();
		tickPlayer();
	}
	
	public void render(Graphics g) {
		try {
			renderWall(g);
			renderFloor(g);
			renderDoor(g);
			renderEnemy(g);
			renderBullet(g);
			renderTrail(g);
			renderPlayer(g);
			renderPickUp(g);
			renderBlock(g);
			renderMovingWall(g);
			renderShop(g);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearAllExceptPlayer() {
		enemyList.clear();
		playerBulletList.clear();
		enemyBulletList.clear();
		wallList.clear();
		floorList.clear();
		doorList.clear();
		trailList.clear();
		pickUpList.clear();
		blockList.clear();
		movingWallList.clear();
		shopList.clear();
	}
	
	public void clearAll() {
		playerList.clear();
		clearAllExceptPlayer();
	}
	
	
	// add & remove
	
	public void addPlayer(Player player) {
		playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
	}
	
	public void addEnemy(Enemy enemy) {
		enemyList.add(enemy);
	}
	
	public void removeEnemy(Enemy enemy) {
		enemyList.remove(enemy);
	}
	
	public void addPlayerBullet(NormalBullet bullet) {
		playerBulletList.add(bullet);
	}
	
	public void removePlayerBullet(NormalBullet bullet) {
		playerBulletList.remove(bullet);
	}
	
	public void addEnemyBullet(NormalBullet bullet) {
		enemyBulletList.add(bullet);
	}
	
	public void removeEnemyBullet(NormalBullet bullet) {
		enemyBulletList.remove(bullet);
	}
	
	public void addWall(Wall wall) {
		wallList.add(wall);
	}
	
	public void removeWall(Wall wall) {
		wallList.remove(wall);
	}
	
	public void addFloor(Floor floor) {
		floorList.add(floor);
	}
	
	public void removeFloor(Floor floor) {
		floorList.remove(floor);
	}
	
	public void addDoor(Door door) {
		doorList.add(door);
	}
	
	public void removeDoor(Door door) {
		doorList.remove(door);
	}
	
	public void addBlock(Block block) {
		blockList.add(block);
	}
	
	public void removeBlock(Block block) {
		blockList.remove(block);
	}
	
	public void addMovingWall(MovingWall movingWall) {
		movingWallList.add(movingWall);
	}
	
	public void removeMovingWall(MovingWall movingWall) {
		movingWallList.remove(movingWall);
	}
	
	public void addTrail(Trail trail) {
		trailList.add(trail);
	}
	
	public void removeTrail(Trail trail) {
		trailList.remove(trail);
	}
	
	public void addPickUp(PickUp pickUp) {
		pickUpList.add(pickUp);
	}
	
	public void removePickUp(PickUp pickUp) {
		pickUpList.remove(pickUp);
	}
	
	public void addShop(Shop shop) {
		shopList.add(shop);
	}
	
	// tick & render
	
	private void tickPlayer() {
		for(int i = 0; i < playerList.size(); i++) {
			playerList.get(i).tick();
		}
	}
	
	private void renderPlayer(Graphics g) {
		for(int i = 0; i < playerList.size(); i++) {
			playerList.get(i).render(g);
		}
	}
	
	private void tickEnemy() {
		for(int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).tick();
		}
	}
	
	private void renderEnemy(Graphics g) {
		for(int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).render(g);
		}
	}
	
	private void tickBullet() {
		for(int i = 0; i < playerBulletList.size(); i++) {
			playerBulletList.get(i).tick();
		}
		for(int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).tick();
		}
	}
	
	private void renderBullet(Graphics g) {
		for(int i = 0; i < playerBulletList.size(); i++) {
			playerBulletList.get(i).render(g);
		}
		for(int i = 0; i < enemyBulletList.size(); i++) {
			enemyBulletList.get(i).render(g);
		}
	}
	
	private void tickWall() {
		for(int i = 0; i < wallList.size(); i++) {
			wallList.get(i).tick();
		}
	}
	
	private void renderWall(Graphics g) {
		for(int i = 0; i < wallList.size(); i++) {
			wallList.get(i).render(g);
		}
	}
	
	private void tickFloor() {
		for(int i = 0; i < floorList.size(); i++) {
			floorList.get(i).tick();
		}
	}
	
	private void renderFloor(Graphics g) {
		for(int i = 0; i < floorList.size(); i++) {
			floorList.get(i).render(g);
		}
	}
	
	private void tickDoor() {
		for(int i = 0; i < doorList.size(); i++) {
			doorList.get(i).tick();
		}
	}
	
	private void renderDoor(Graphics g) {
		for(int i = 0; i < doorList.size(); i++) {
			doorList.get(i).render(g);
		}
	}
	
	private void tickBlock() {
		for(int i = 0; i < blockList.size(); i++) {
			blockList.get(i).tick();
		}
	}
	
	private void renderBlock(Graphics g) {
		for(int i = 0; i < blockList.size(); i++) {
			blockList.get(i).render(g);
		}
	}
	
	private void tickMovingWall() {
		for(int i = 0; i < movingWallList.size(); i++) {
			movingWallList.get(i).tick();
		}
	}
	
	private void renderMovingWall(Graphics g) {
		for(int i = 0; i < movingWallList.size(); i++) {
			movingWallList.get(i).render(g);
		}
	}
	
	private void tickTrail() {
		for(int i = 0; i < trailList.size(); i++) {
			trailList.get(i).tick();
		}
	}
	
	private void renderTrail(Graphics g) {
		for(int i = 0; i < trailList.size(); i++) {
			trailList.get(i).render(g);
		}
	}
	
	private void tickPickUp() {
		for(int i = 0; i < pickUpList.size(); i++) {
			pickUpList.get(i).tick();
		}
	}
	
	private void renderPickUp(Graphics g) {
		for(int i = 0; i < pickUpList.size(); i++) {
			pickUpList.get(i).render(g);
		}
	}
	
	private void tickShop() {
		for(int i = 0; i < shopList.size(); i++) {
			shopList.get(i).tick();
		}
	}
	
	private void renderShop(Graphics g) {
		for(int i = 0; i < shopList.size(); i++) {
			shopList.get(i).render(g);
		}
	}
}
