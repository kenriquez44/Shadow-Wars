import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * Powerup class, grants player a special ability. Extends the sprite class and handles all unique behaviour of the powerup that is spawned in the game world when an enemy is killed. 
 * @author Kevin.E
 *
 */
public class Powerup extends Sprite{	
	//Movement speed of the powerup in pixels per millisecond
	private static final float POWER_SPEED = .1f;
	//No movement speed
	private static final float NO_MOVEMENT_SPEED = 0;
	//flag for whether the powerup should grant a shield
	private boolean grantShield = false;
	//flag for whether powerup should increase player shot speed
	private boolean shotSpeed = false;
	
	/**
	 * Constructs the powerup. 
	 * param imageSrc Location of image to be used for this powerup
	 * @param x The initial x location of the powerup.
	 * @param y The initial x location of the powerup.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @param grantShield whether the powerup should grant a shield
	 * @param shotSpeed whether powerup should increase player shot speed
	 * @throws SlickException
	 */
	public Powerup(String imageSrc, float x, float y, int delay, boolean activateOnce, boolean grantShield, boolean shotSpeed) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
		this.grantShield =grantShield;
		this.shotSpeed = shotSpeed;
	}	
	
	/**
	 * gets the flag for whether powerup should grant a shield
	 * @return the boolean of grantShield
	 */
	public boolean getgrantShield() {
		return grantShield;
	}
	
	/**
	 * sets the delay of the event object of the powerup
	 * @param x time in milliseconds for the duration of the delay
	 * @param activateOnce whether the eventobject should activate once or continously
	 */
	public void setDelay(int x, boolean activateOnce) {
		this.seteventObject(x, activateOnce);
	}
	
	/**
	 * gets the flag for whether the powerup should increase player shot speed
	 * @return the boolean of shotSpeed
	 */
	public boolean getshotSpeed() {
		return shotSpeed;
	}
	
	/**
	 * Updates all logic associated with the powerup
	 *  @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {	
		//updates the timer of the powerup
		super.geteventObject().update(delta);
		//updates y position of the powerup based on its constant speed
		super.update(input, delta, NO_MOVEMENT_SPEED, POWER_SPEED);	
		}
}
