import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * The Shield class, prevents the player from losing a life. Extends the sprite class, handling the unique behaviour of the shield that surrounds the player. 
 * @author Kevin.E
 *
 */
public class Shield extends Sprite{
	//flag for when the shield sprite should be drawn
	private boolean drawImage = true;
	//the player that the shield surrounds
	private Player player;
	
	/**
	 * Constructs the shield sprite
	 *@param imageSrc Location of image to be used for this shield
	 * @param x The initial x location of the shield.
	 * @param y The initial x location of the shield.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @param player The player that the shield surrounds
	 * @throws SlickException
	 */
	public Shield(String imageSrc, float x, float y, int delay, boolean activateOnce, Player player) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);		
		this.player = player;
	}
	
	/**
	 * gets the flag for whether the shield should be drawn
	 * @return the boolean of drawImage
	 */
	public boolean getdrawImage() {
		return drawImage;
	}
	
	/**
	 * sets the flag for whether the shield should be drawn
	 * @param x the boolean for which the flag should be set to
	 */
	public void setdrawImage(boolean x) {
		drawImage  = x;
	}
	
	
	/**
	 * Updates all logic associated with the shield
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {	
		///updates the timer of the shield
		super.geteventObject().update(delta);
		
		//checks whether enough time has elapsed and sets the flag for whether it should be drawn to false
		if(super.geteventObject().isReady()) {
			setdrawImage(false);			
			
		}
		//sets the x and y position of the shield to the same as the player that it surrounds
		super.setxPos(player.getxPos());
		super.setyPos(player.getyPos());
	}
	
	/**
	 * Draws the image of the sheild 
	 */
	public void render() {	
		//checks whether the shield should be drawn
		if(drawImage) {
			//draws the image based on its x and y position
			super.getImage().drawCentered(super.getxPos(), super.getyPos());
		}
	}
}
