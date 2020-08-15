import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Laser class, for the player the shoot. Extends the Sprite class. Handles unique movement of the laser.
 * @author Kevin.E
 *
 */
public class Laser extends Sprite{
	//Movement speed of the laser in pixels per millisecond.
	private static final float LASER_SPEED = 3f;
	//Movement speed of the laser when it is not moving in pixels per millisecond.
	private static final float NO_MOVEMENT_SPEED = 0;
	//if laser should be deleted
	private boolean shouldDel = false;
	
	
	
	
	/**
	 * Construct the laser sprite based on a sprite image and location.
	 * @param imageSrc Location of image to be used for this laser.
	 * @param x The initial x location of the laser.
	 * @param y The initial x location of the laser.
	 * @throws SlickException
	 */
	public Laser(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);
	}	
	
	public boolean getshouldDel() {
		return shouldDel;
	}
	/**
	 * Updates all logic associated with the laser sprite.
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//Sets the flag for the laser to be deleted if it moves offscreen
		if(super.getyPos()<20) {
			shouldDel=true;
		}
		//Updates y position of the laser sprite based on its constant speed.
		super.update(input, delta, NO_MOVEMENT_SPEED, -LASER_SPEED);		
			
	   }		
}



