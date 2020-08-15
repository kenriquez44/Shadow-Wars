import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.Random;

/**
 * BasicShooter Class, a unique enemy that can shoot. Extends the enemy class, handling unique behaviour of the basic shooter. 
 * @author Kevin.E
 *
 */
public class BasicShooter extends Enemy{
	//Minimum y coordinate that the basic shooter can travel to
	private static final int MIN_Y_CORD = 48;
	//Maximum y coodinate that the basic shooter can travel to
	private static final int MAX_Y_CORD = 464;
	//Delay in milliseconds between shots
	private static final int SHOOT_DELAY = 3500;
	//Movement speed in pixels per millisecond
	private static final float BASICSHOOTER_MOVEMENT_SPEED = .2f;
	//No movement speed
	private static final float BASICSHOOTER_NO_SPEED = 0;
	//Score value
	private static final int SCORE = 200;
	//Y coordinate that it will travel to
	private int finalYLoc = 0;
	//Delay object to determine when it can fire another shot
	private EventObject shootDelay;
	
	/**
	 * Constructs the basic shooter enemy
	 * @param imageSrc Location of image to be used for this basic shooter
	 * @param x The initial x location of the basic shooter.
	 * @param y The initial x location of the basic shooter.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @throws SlickException
	 */
	public BasicShooter(String imageSrc, float x, float y,int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
		//Generates the final y coordinate that it will travel to
		genYloc();
		//Creates a delay to determine when it can fire another shot
		shootDelay = new EventObject(SHOOT_DELAY,false);
	}
	
	/**
	 * Gets the score that the basic shooter is worth
	 * @return its score
	 */
	public int getScore() {
		return SCORE;
	}
	
	/**
	 * Gets the shootDelay event object
	 * @return the shootDelay event object
	 */
	public EventObject getshootDelay() {
		return shootDelay;
	}
	
	/**
	 * Calculates a random y coordinate between the min and max y coordinate
	 */
	private void genYloc() {
		Random r = new Random();
		finalYLoc = r.nextInt(MAX_Y_CORD - MIN_Y_CORD) + MIN_Y_CORD;		
	}
	
	/**
	 * Updates all logic associated with the basic shooter
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//updates any logic that the basic shooter has that is shared between all enemies
		super.move(input, delta);
		
		//checks whether the enemy is allowed to move, update its movement	
		if(super.getcanMove()) {
			//Updates y position of the laser sprite based on its constant speed.
			if(super.getyPos()<finalYLoc) {
				super.update(input, delta, BASICSHOOTER_NO_SPEED, BASICSHOOTER_MOVEMENT_SPEED);		
				
			}
			else {
				//once it reaches it final y destination, allow it to start shooting
				super.setcanShoot(true);				
				//stops basic shooter from moving
				super.setcanMove(false);
			}
			
		}
		//checks whether the basic shooter is allowed to shoot and if so, start updating the shootDelay event object
		if(super.getcanShoot()) {
			shootDelay.update(delta);
			//stops basic shooter from moving
			
			
		}		
	}
}






