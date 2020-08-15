import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Enemy Class, represents the bad guys for the player to destory. Extends the sprite class and handles unique behaviour of all enemies
 */
public class Enemy extends Sprite{
	//flag for when the enemy is allowed to shoot
	private boolean canShoot = false;
	 //flag for when the enemy is allowed to move
    private boolean canMove = false;

	
	/**
	 * Constructs the enemy class. 
	 * @param imageSrc Location of image to be used for this enemy
	 * @param x The initial x location of the enemy.
	 * @param y The initial x location of the enemy.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 */
	public Enemy(String imageSrc, float x, float y,int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
	}
	
	
	/**
	 * Gets the flag of whether the enemy should be allowed to move
	 * @return boolean of  canMove
	 */
	public boolean getcanMove() {
		return canMove;
	}
	
	/**
	 * Sets the flag of canMove to either true or false
	 * @param x boolean that canMove will bet set to. 
	 */
	public void setcanMove(boolean x) {
		canMove = x;
	}
	
	/**
	 * gets the flag of whether enemy should be able to shoot
	 * @return the boolean of canShoot
	 */
	public boolean getcanShoot() {
		return canShoot;
	}
	
	/**
	 * sets the flag of whether the enemy should be able to shoot
	 * @param x the boolean that canShoot will be set to
	 */
	public void setcanShoot(boolean x) {
		canShoot = x;
	}
	
	/**
	 * Updates all logic of the enemy
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//updates the timer of the enemy
		super.geteventObject().update(delta);						
	    //sets the flag for whether the enemy can move if enough time has elapsed
	    if(super.geteventObject().isReady()) {
	    	canMove = true;
		}
	}
}
