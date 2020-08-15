import java.util.Random;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * The boss class, strongest enemy in the game. Extends the enemy class and handles all unique behaviour of the boss
 * @author Kevin.E
 *
 */
public class Boss extends Enemy{	
	//Inital movement speed of the boss in pixels per millisecond
	private static final float INITIAL_BOSS_SPEED = .05f;	
	//Minimum x coordinate that the boss can travel to	
	private static final int MIN_X_CORD = 128;
	//Maximum x coordinate that the boss can travel to
	private static final int MAX_X_CORD = 896;
	//y coordinate that the boss will travel to
	private static final int INITIAL_Y_CORD_DESTINATION = 72;
	//second delay of the boss in milliseconds
	private static final int SECOND_DELAY = 5000;
	//third delay of the boss in milliseconds
	private static final int THIRD_DELAY = 2000;
	//fourth delay of the boss in milliseconds
	private static final int FOURTH_DELAY = 3000;
	//second movement speed of the boss in pixels per millisecond
	private static final float SECOND_BOSS_SPEED = .2f;
	//third movement speed of teh boss in pixels per millisecond
	private static final float THIRD_BOSS_SPEED = .1f;
	//no movement speed
	private static final float NO_MOVEMENT = 0;
	//time between shots in milliseconds
	private static final int SHOOT_DELAY = 200;
	//score value of the boss
	private static final int SCORE = 5000;
	//int that corresponds to which movement pattern the boss should current use
	private int repeatRun = 0;
	//time in milliseconds
	private int time = 0;
	// final x coordinate that the boss will travel to
	private int finalXLoc = 0;
	//x positions at which each shot the boss shoots will start from 
	private int[] shotPos = new int[] {-97,-74,74,97};
	//delay object of the boss shots
	private EventObject shootDelay;
	//number of lives of the boss
	private int numLives = 60;	
	//flag for whether the boss is dead
	private boolean isDead = false;
	//flag for when the boss movement pattern should be reset
	private boolean restartPattern = false;
	/**
	 * Constructs the boss class
	 * @param imageSrc Location of image to be used for this boss.
	 * @param x The initial x location of the boss.
	 * @param y The initial x location of the boss.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @throws SlickException
	 */
	public Boss(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);		
		shootDelay = new EventObject(SHOOT_DELAY, false);
	}
	
	/**
	 * gets the score of the boss
	 * @return boss's score
	 */
	public int getScore() {
		return SCORE;
	}
	
	/**
	 * gets the array of positions of the enemy shots
	 * @return the array of positions of the enemy shots
	 */
	public int[] getshotPos() {
		return shotPos;
	}
	
	/**
	 * generates a random x location between the minimum and maximum x coordinates that the boss can travel to
	 */
	private void genXloc() {
		Random r = new Random();
		finalXLoc = r.nextInt(MAX_X_CORD - MIN_X_CORD) + MIN_X_CORD;
		
		
	}
	
	/**
	 * gets the shootDelay event object of the boss
	 * @return the shootDelay event object of the boss
	 */
	public EventObject getshootDelay() {
		return shootDelay;
	}
	
	/**
	 * gets the number of lives of the boss
	 * @return the number of lives of the boss
	 */
	public int getLives() {
		return numLives;
	}
	
	/**
	 * sets the flag for whether the boss is dead to true
	 */
	public void setisDead() {
		isDead=true;
	}
	/**
	 * Updates all logic of the boss
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//updates any logic that the boss has that is shared between all enemies
		super.move(input, delta);				
		
		//if the boss can move and it is on its first movement pattern
		if(super.getcanMove() && repeatRun == 0) {
			//updates boss's movement speed in the x direction using the initial movement speed
			super.update(input, delta,0, INITIAL_BOSS_SPEED);
			//checks whether the boss has reached its y direction and stops the boss from moving for 5000 milliseconds
			if(super.getyPos()>=INITIAL_Y_CORD_DESTINATION) {
				super.setcanMove(false);
				super.seteventObject(SECOND_DELAY,true);
				//movement pattern 2
				repeatRun = 1;
				//generates a x coordinate to travel to
				genXloc();
			}
		}
		//if the boss is allowed to move and it is on its second movement pattern
		else if(super.getcanMove() && repeatRun == 1) {			
			//if x destination is higher that its current position
			if(finalXLoc >= super.getxPos()) {
				//updates boss's movement speed in the x direction using the second movement speed in the positive direction
				super.update(input, delta, SECOND_BOSS_SPEED, NO_MOVEMENT);
				//stops boss from moving when it reaches destination
				if(super.getxPos()>= finalXLoc) {
					super.setcanMove(false);
				}
			}
			//if x destination is lower than its current position			
			else {
				//updates boss's movement speed in the x direction using the second movement speed in the negative direction
				super.update(input, delta, -SECOND_BOSS_SPEED, NO_MOVEMENT);
				//stops boss from moving when it reaches destination
				if(super.getxPos()<= finalXLoc) {
					super.setcanMove(false);
				}
			}
			//if the boss has stopped moving, make it wait for 2000 milliseconds
			if(!super.getcanMove()) {
				super.seteventObject(THIRD_DELAY, true);
				//generate another x coordinate to travel to
				genXloc();
				//movement pattern 3
				repeatRun = 2;
			}
		}
		//if boss can move and it is on its third movement pattern
		else if(super.getcanMove() && repeatRun ==2) {			
			//allows boss to starting shooting
			super.setcanShoot(true);
			//if x destination is higher that its current position
			if(finalXLoc >= super.getxPos()) {
				//updates boss's movement speed in the x direction using the third movement speed in the positive direction
				super.update(input, delta, THIRD_BOSS_SPEED, NO_MOVEMENT);
				//stops boss from moving when it reaches destination
				if(super.getxPos()>= finalXLoc) {
					super.setcanMove(false);
					//reset movement pattern
					restartPattern = true;
				}
			}
			//if x destination is lower than its current position			
			else {
				//updates boss's movement speed in the x direction using the third movement speed in the negative direction
				super.update(input, delta, -THIRD_BOSS_SPEED, NO_MOVEMENT);
				//stops boss from moving when it reaches destination
				if(super.getxPos()<= finalXLoc) {
					super.setcanMove(false);
					//reset movement pattern
					restartPattern = true;
				}
			}						
		}
		
		//checks if boss can shoot and is not dead
		if(super.getcanShoot() && !isDead) {		
			//time to determine when it should stop shooting
			time+=delta;
			//updates shootDelay object
			shootDelay.update(delta);
			//if 3000 milliseconds has passed, stop shooting
			if(time >= FOURTH_DELAY) {								
				time=0;
				//stop shooting
				super.setcanShoot(false);			
			}			
		}
		
		//checks if movement pattern should be reset and boss has finished shooting and moving
		if(restartPattern && !super.getcanShoot() && !super.getcanMove()) {
			//generate another x coordinate to travel to
			genXloc();
			// wait 5000 milliseconds 
			super.seteventObject(SECOND_DELAY,true);
			//go back to movement pattern 2
			repeatRun = 1;			
			restartPattern = false;
		}
		
		//if boss has been hit, lose a life
		if(super.gethasHit()) {
			numLives-=1;
			super.resethasHit();
		}		
		
		//if number of lives is 0, set the flag for whether it is dead to be true
		if(numLives<=0) {
			isDead = true;
		}
		
	}
	/**
	 * renders the image of the boss 
	 */
	public void render() {	
		// if boss is not dead, draw its image centred on its x and y position
		if(!isDead) {
			super.getImage().drawCentered(super.getxPos(), super.getyPos());
		}
	}
}






