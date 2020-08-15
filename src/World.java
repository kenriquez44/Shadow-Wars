import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.*;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Random;



/**
 * World Class. Represents everything in the game.
 * Handles backgrounds and sprites.
 * @author Kevin.E
 */

public class World {
	//Division of background tiles in pixels.
	private static final int TILE_DIVISION = 512;
	//Initial position of background in pixels.
	private static final int INTIAL_BACKGROUND_POSITION = 0;
	//Initial x position of player sprite in pixels.
	private static final int INITIAL_PLAYERX_POSITION = 480;
	//Initial y position of player sprite in pixels.
	private static final int INITIAL_PLAYERY_POSITION = 688;
	//Movement speed of background tiles pixels per millisecond.
	private static final float BACKGROUND_MOVESPEED  = .2f;		
	//Sets y position of background tile to initial position. 
   	private float backgroundYPos = INTIAL_BACKGROUND_POSITION;   
   	//No delay
   	private static final int NO_DELAY = 0;
   	// Initial shield delay
   	private static final int INITIAL_SHIELD_DELAY = 3000;
   	//y position of player life sprites
   	private static final float LIFE_YPOS = 696;
   	//initial y position of all enemy sprites
   	private static final float INITIAL_EMEMY_YPOS = -64;
   	//Duration of the powerups
   	private static final int POWERUP_DURATION = 5000;
   	//Shot speed of player under effect of powerup
	private static final int SHOT_SPEED_POWER = 150;
	//The image that represent the background.
	private Image background;		
	//The sprite that represents the player.
    private Player player; 
    //The sprite that represents the player's shield
    private Shield shield;
    //The sprite that represents the boss
    private Boss boss;
    //Represents the player's current score
 	private int score; 
    //Array of x positions for the player lives
    private int[] livePos = new int[] {100,60,20};
    //List of all the sprites representing the players lives
    private ArrayList<Sprite> playerLives = new ArrayList<Sprite>();
   	//List of all basic enemy sprites that exist in the game.
   	private ArrayList<BasicEnemy> basicEnemies  = new ArrayList<BasicEnemy>();
   	//List of all sine enemy sprites that exist in the game.
   	private ArrayList<SineEnemy> sineEnemies  = new ArrayList<SineEnemy>();
   	//List of all the powerups that exist in the game
   	private ArrayList<Powerup> powerups = new ArrayList<Powerup>();
   	//List of all basic shooter enemy sprites that exist in the game
   	private ArrayList<BasicShooter> basicShooters = new ArrayList<BasicShooter>();	   
   	//List of all laser sprites that exist in the game.
   	private ArrayList<Laser> lasers = new ArrayList<Laser>();
   	//List of all enemyshots that exist in the game.
   	private ArrayList<EnemyShot> enemyshots = new ArrayList<EnemyShot>();
   	//List of all sprites that need to be removed from the game.
   	private ArrayList<Object> removeList = new ArrayList<Object>();   
  
   
   	
   
   	

       
	/** 
	 * Constructs a new world
	 * @throws SlickException
	 */
	public World() throws SlickException {		
		// Initializes all sprites appearing in the world. 	   
		background = new Image("res/space.png");				 
		player = new Player("res/spaceship.png", INITIAL_PLAYERX_POSITION, INITIAL_PLAYERY_POSITION,NO_DELAY,true,shield);
		shield = new Shield("res/shield.png",INITIAL_PLAYERX_POSITION,INITIAL_PLAYERY_POSITION,INITIAL_SHIELD_DELAY,true, player);
		player.setShield(shield);
		for( int i: livePos) {
			Sprite playerlife = new Sprite("res/lives.png",i,LIFE_YPOS,NO_DELAY,false);
			playerLives.add(playerlife);
		}
			
		//Reads and parses enemy waves from waves.txt
		try (Scanner file = new Scanner(new FileReader("C:\\Users\\Kevin.E\\eclipse-workspace\\Project 1\\src\\res\\\\waves.txt"))) {
			int xPos = 0;
			int delay = 0;
			String spriteName = "";
			while(file.hasNext()) {
				spriteName=file.nextLine();
				if(spriteName.contains("#")) {
					spriteName=file.nextLine();
				}
				String[] parts = spriteName.split(",");
				xPos = Integer.parseInt(parts[1]);	
				delay = Integer.parseInt(parts[2]);				
				if(parts[0].equals("BasicEnemy")) {
					BasicEnemy basicEnemy = new BasicEnemy("res/basic-enemy.png",xPos,INITIAL_EMEMY_YPOS,delay,true);					
					basicEnemies.add(basicEnemy);				
				}
				
				else if(parts[0].equals("SineEnemy")) {
					SineEnemy sineEnemy = new SineEnemy("res/sine-enemy.png",xPos, INITIAL_EMEMY_YPOS, delay,true);				
					sineEnemies.add(sineEnemy);									
				}
				
				else if(parts[0].equals("BasicShooter")) {
					BasicShooter basicShooter = new BasicShooter("res/basic-shooter.png",xPos, INITIAL_EMEMY_YPOS,delay,true);					
					basicShooters.add(basicShooter);				
				}
				else if(parts[0].equals("Boss")){
					 boss = new Boss("res/boss.png",xPos, INITIAL_EMEMY_YPOS, delay,true);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}								   		      			
	}
	
	
	/**
	 * Creates a powerup when an enemy is killed at its location.
	 * @param xPos x position of the killed enemy.
	 * @param yPos y position of the killed enemy.
	 * @throws SlickException
	 */
	public void spawnPower(float xPos, float yPos) throws SlickException {
		int x;
		Random r = new Random();
		x= r.nextInt(100);
		//5% chance of spawning a powerup
		if(x==5) {
			r = new Random();
			x = r.nextInt(2);
			//50% chance of spawning either a shield or shotspeed powerup
			if (x==1) {
				Powerup powerup = new Powerup("res/shield-powerup.png", xPos, yPos, NO_DELAY,true, true, false);
				powerups.add(powerup);
			}
			
			else {
				Powerup powerup = new Powerup("res/shotspeed-powerup.png", xPos, yPos,NO_DELAY,true, false, true);
				powerups.add(powerup);
			}
			
		}
	}
	/** 
	 * Remove an entity from the game. Will no longer move or be drawn.
	 * @param enemy The enemy that should be removed.
	 */
	public void removeSprite(Object enemy) {
		removeList.add(enemy);
	}
	
	/** 
	 * Returns the current score of the player
	 * @return current score of the player
	 */
	public int getScore() {
		return  score;
	}
	
	/**
	 *Handles logic of all sprites existing in the game world.
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException
	 */
	public void update(Input input, int delta) throws SlickException {
		//Updates movement of background tiles based of constant background moving speed.  
		backgroundYPos += delta *BACKGROUND_MOVESPEED;			
		//Resets background position to initial position after reaching tile division.
		if (backgroundYPos >= TILE_DIVISION) {
			backgroundYPos = INTIAL_BACKGROUND_POSITION;
		}	
								
		//Updates movement of player sprite.		
		player.move(input,delta);	
		
		//Updates the movement of the shield surrounding the player
		shield.move(input, delta);
					
		//Updates the movement of boss sprite
		boss.move(input, delta);
		
		//If the boss is allowed to shoot, creates 4 enemy shots at set locations after every delay interval, and adds it to the list of enemy shots.
		if(boss.getcanShoot() ) {
			if(boss.getshootDelay().isReady()) {
				for(int i : boss.getshotPos()) {
				EnemyShot enemyshot = new EnemyShot("res/enemy-shot.png",boss.getxPos()+i,boss.getyPos(),NO_DELAY,false);			
				enemyshots.add(enemyshot);
				}
			}
		}
		
		//Updates the movement of all basic enemy sprites
		for(BasicEnemy i: basicEnemies) {			
			i.move(input, delta);
		}
		//Updates the movement of all sine enemy sprites
		for(SineEnemy i: sineEnemies) {	
			i.move(input,delta);
		}
		
				
		//Updates the movement and logic of all basic shooter enemy sprites
		for(BasicShooter i: basicShooters) {			
			//if the basic shooter is allowed to shoot, creates an enemy shot after every delay interval, and adds it to the list of enemy shots.
			if(i.getcanShoot()) {
				if(i.getshootDelay().isReady()) {
					EnemyShot enemyshot = new EnemyShot("res/enemy-shot.png",i.getxPos(),i.getyPos(),0,false);			
					enemyshots.add(enemyshot);
				}				
			}
			i.move(input, delta);
		}
		
		
		
		
		//If space is pressed, creates a new laser at current player position and adds it to the list of lasers.
		if(input.isKeyDown(Input.KEY_SPACE)) {
			if(player.getshootDelay().isReady()) {
				Laser laser = new Laser("res/shot.png",player.getxPos(),player.getyPos(),0,false);						
				lasers.add(laser);
			}
		}
		
		//Updates movement for all lasers currently on screen (in the laser list).
		for(Laser i: lasers) {
			i.move(input, delta);
			//checks whether the laser has contacted the boss, deletes the laser if so.
			if(boss.contactSprite(i)) {
				removeSprite(i);
			}
			
		}
		//Updates movement for all enemy shots 	current only screen ( in the enemyshot list).			
		for(EnemyShot i: enemyshots) {
			i.move(input, delta);
			//checks whether the enemyshot has hit the player, player loses a life if so.
			player.contactSprite(i) ;	
			if (i.getshouldDel()) {
				removeSprite(i);
			}
		}
																	
		//Checks collision between basic enemy sprites using the bounding box surrounding each sprite.
		for(BasicEnemy i: basicEnemies) {
			//If the players bounding box intersects with an enemy bounding box, player loses a life.
			player.contactSprite(i) ;			
			//If the laser bounding box intersects with an enemy bounding box, removes both laser and basic enemy from the game.
			for(Laser j: lasers) {
				if(j.contactSprite(i)) {					
					removeSprite(i);
					removeSprite(j);
					//determines whether a powerup should be spawned after the enemy is killed
					spawnPower(i.getxPos(),i.getyPos());	
					//enemy's score value is added to the player's score
					score+=i.getScore();
				}
				//checks whether the laser is out of bounds and should be deleted
				if(j.getshouldDel()) {
					removeSprite(j);
				}
			}
		}
	

		
		for(SineEnemy i: sineEnemies) {
			//If the players bounding box intersects with an enemy bounding box, player loses a life. 
			if(player.getboundary().intersects(i.getboundary())) {
				player.contactSprite(i) ;	
			}
			//If the laser bounding box intersects with an enemy bounding box, removes both laser and sine enemy from the game.
			for(Laser j: lasers) {
				if(j.contactSprite(i)) {			
					removeSprite(i);					
					removeSprite(j);
					//determines whether a powerup should be spawned after the enemy is killed
					spawnPower(i.getxPos(),i.getyPos());
					//enemy's score value is added to the player's score
					score+=i.getScore();
				}
			}
		}
		
		for(BasicShooter i: basicShooters) {
			//If the players bounding box intersects with an enemy bounding box, player loses a life.
			if(player.getboundary().intersects(i.getboundary())) {
				player.contactSprite(i) ;	
			}
			//If the laser bounding box intersects with an enemy bounding box, removes both laser and basic shooter from the game.
			for(Laser j: lasers) {
				if(j.contactSprite(i)) {				
					removeSprite(i);				
					removeSprite(j);
					//determines whether a powerup should be spawned after the enemy is killed
					spawnPower(i.getxPos(),i.getyPos());
					//enemy's score value is added to the player's score
					score+=i.getScore();
				}
			}
		}
		
		
		
			
		//if the player has lost a life, removes the sprite that represents that life.		
		for(Sprite i: playerLives) {
			if(player.getlifeLost()) {
				removeSprite(i);
				player.setlifeLost();
			}
		}
		
		//Updates movement of all powerup sprites in the game and 
		for(Powerup i: powerups) {
			i.move(input, delta);
			if(player.contactPower(i)) {
				if(i.getgrantShield()) {					
					player.newShield();				
				}
				
				else {
					player.setDelay(POWERUP_DURATION,true);
					player.setshootDelay(SHOT_SPEED_POWER, false);
				}
				removeSprite(i);
				}		
		}
		
		
		
		
		//Removes all sprites that has been marked to be removed from the remove list.
		basicEnemies.removeAll(removeList);	
		sineEnemies.removeAll(removeList);	
		basicShooters.removeAll(removeList);	
		playerLives.removeAll(removeList);
		lasers.removeAll(removeList);	
		enemyshots.removeAll(removeList);
		powerups.removeAll(removeList);
		//Clears the remove enemy list 
		removeList.clear();						
	}

	
	/** 
	 * Draws all of the sprites in the game.
	 */
	public void render() {		
		//Draws first background tile off-screen at the initial x position and -512 pixels in the y direction.   
		background.draw(INTIAL_BACKGROUND_POSITION, backgroundYPos-TILE_DIVISION);
		
		//Draws second background tile off-screen. 512 pixels to the right of first tile . 
		background.draw(TILE_DIVISION, backgroundYPos-TILE_DIVISION);
		
		//Draws third background tile at top left corner of screen.
		background.draw(INTIAL_BACKGROUND_POSITION, backgroundYPos);
		
		//Draws fourth background tile in the middle of the screen.
		background.draw(TILE_DIVISION,backgroundYPos);
		
		//Draws fifth background tile below the tile at the top left corner of screen.
		background.draw(INTIAL_BACKGROUND_POSITION,TILE_DIVISION+backgroundYPos);
		
		//Draws sixth background tile below the tile at the middle of the screen./
		background.draw(TILE_DIVISION,TILE_DIVISION+backgroundYPos);
		
		//Draws player sprite.
		player.render();		
		
		//Draws the shield sprite surrounding the player
		shield.render();
		
		//Draws the boss sprite.
		boss.render();
		
		//Draws all laser sprites currently existing in the world.
		for(Laser i: lasers) {
			i.render();
		}
		//Draws all enemyshot sprites currently existing in the world.
		for(EnemyShot i: enemyshots) {
			i.render();
		}
		//Draws all basic enemy sprites currently existing in the world.
		for(BasicEnemy i: basicEnemies) {
			i.render();
		}
		//Draws all sine enemy sprites currently existing in the world.
		for(SineEnemy i: sineEnemies) {
			i.render();
		}
		//Draws all basic shooter sprites currently existing in the world.
		for(BasicShooter i: basicShooters) {
			i.render();
		}
		//Draws all player life sprites currently existing in the world.
		for(Sprite i: playerLives) {
			i.render();
		}
		//Draws all draws all the powerup sprites currently existing in the world.
		for(Powerup i: powerups) {
			i.render();
		}
	}
}
