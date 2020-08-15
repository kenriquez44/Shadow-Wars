import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * SineEnemy class, a unique enemy that moves in a sine pattern.. Extends the enemy class, handling and unique behaviour of the sine enemy. 
 * @author Kevin.E
 *
 */
public class SineEnemy extends Enemy{
	//centre position of the sine enemy
	private float centreX = 0;
	//delay value for the sine enemy
	private float delay = 0;
	//x position offset
	private float offset = 0;
	//time in milliseconds
	private int time = 0;
	//amplitude of the offset sine equation
	private static final double AMPLITUDE = 96;
	//period of the offset sine equation
	private static final double PERIOD = 1500;
	//score value of the sine enemy
	private static final int SCORE = 100;
	//Movement speed of the basic enemy in the y direction in pixels per millisecond
	private static final float SINENENEMY_MOVE_SPEED = .15f;
	//Movement speed of sine enemy sprite when not moving in pixels per millisecond. 
	private static final float SINEENEMY_NO_MOVE_SPEED = 0;

	
	/**
	 * Constructs a sine enemy
	 * @param imageSrc Location of image to be used for this sine enemy
	 * @param x The initial x location of the sine enemy.
	 * @param y The initial x location of the sine enemy.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @throws SlickException
	 */
	public SineEnemy(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
		this.delay = delay;
		//sets the centre position of the sine enemy
		centreX = super.getxPos();
	}
	
	/**
	 * gets the score value of the sine enemy
	 * @return the score of the sine enemy
	 */
	public int getScore() {
		return SCORE;
	}
	
	/**
	 * Updates all logic associated with the sine enemy 
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//updates any logic that the sine enemy has that is shared between all enemies
		super.move(input, delta);
		
		//increments the time value by delta
		time+=delta;		
		
		//if allowed to move, updates the sine enemy's movement
		if(super.getcanMove()) {
			//sine equation that determines its offset in the x direction
			offset =  (float) ((AMPLITUDE * Math.sin(((2*Math.PI)/PERIOD)*(time - delay))));	
			//Updates the x position based on the offset added to its centre x position
			super.setxPos(centreX + offset);
			//Updates y position of the based on its constant speed.
			super.update(input, delta, SINEENEMY_NO_MOVE_SPEED, SINENENEMY_MOVE_SPEED);
		}
	}
}






