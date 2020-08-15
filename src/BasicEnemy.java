import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * Basic Enemy class, a special type of enemy. Extends the enemy class, handling any unique behaviour of the basic enemy
 * @author Kevin.E
 *
 */
public class BasicEnemy extends Enemy{
	//The score value that the basic enemy is worth
	private static final int SCORE = 50;	
	//Movement speed of the basic enemy in the y direction in pixels per millisecond
	private static final float BASICENEMY_MOVE_SPEED = .2f;
	//Movement speed of basic enemy sprite when not moving in pixels per millisecond. 
	private static final float BASICENEMY_NO_MOVE_SPEED = 0;
	
	/**
	 * Constructs the basic enemy 
	 * @param imageSrc Location of image to be used for this basic enemy
	 * @param x The initial x location of the basic enemy.
	 * @param y The initial x location of the basic enemy.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @throws SlickException
	 */
	public BasicEnemy(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
	}
	
	
	/**
	 * gets the score value of the basic enemy
	 * @return the score value of the basic enemy
	 */
	public int getScore() {
		return SCORE;
	}
	/**
	 * Updates all logic associated with the basic shooter
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//updates any logic that the basic enemy has that is shared between all enemies
		super.move(input, delta);
		
		//if the enemy is allowed to move, update its movement
		if(super.getcanMove()) {
			//Updates y position of the laser sprite based on its constant speed.
			super.update(input, delta, BASICENEMY_NO_MOVE_SPEED, BASICENEMY_MOVE_SPEED);		
			
			//Updates the bounding box surround the laser sprite to its current location as it moves on the screen.	
			getboundary().setX(getxPos());
			getboundary().setY(getyPos());		
		}
	}
}




