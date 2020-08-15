import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import utilities.*;

/** 
 * Sprite class, a general purpose class. Represents a sprite and handles rendering its image and updating relevant data.
 * @author Kevin.E
 *
 */
public class Sprite {
	//Image for the sprite.
	private Image spriteImage;
    //x position of the sprite in pixels.
	private float xPos;
	//y position of the sprite in pixels.
    private float yPos;
    //bounding box that surrounds the sprite.
    private BoundingBox boundary; 
    //eventObject for each sprite
    private EventObject event;
    //flag for when the sprite has made contact with another sprite
    private boolean hasHit = false;
   
   
	/**
	 * Construct a sprite based on a sprite image and a location.
	 * @param imageSrc Location of image to be used for this sprite.
	 * @param x The initial x location of the sprite.
	 * @param y The initial y location of the sprite.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @throws SlickException
	 */
	public Sprite(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {		
		//Initializes image of sprite.
		spriteImage = new Image(imageSrc);		
		//Sets current x and y position of sprite to initial positions.
		xPos = x;
		yPos = y;
		//Creates a bounding box for the sprite. Used for sprite collision.
		boundary = new BoundingBox(spriteImage, xPos, yPos);	
		//Creates an eventobject to be used as a timer for any events related to the sprite			
		event = new EventObject(delay,activateOnce);
	}
	
	
	
	/** 
	 * Gets the x position of the sprite.
	 * @return The x position of the sprite.
	 */
	public float getxPos() {		
		return xPos;
	}
	
	/**
	 * Gets the image of the sprite
	 * @return The image of the sprite
	 */
	public Image getImage() {
		return spriteImage;
	}
	
	/**
	 * Gets the y position of the sprite.
	 * @return The y position of the sprite
	 */
	public float getyPos() {
		return yPos;
	}
	
	/** 
	 * Gets the boundary box of the sprite.
	 * @return  The boundary box of the sprite.
	 */
	public BoundingBox getboundary() {
		return boundary;
	}
	
	/**Gets the event object of the sprite
	 * 
	 * @return The event object of the sprite
	 */
	public EventObject geteventObject() {
		return event;
	}
	
	/**
	 * Sets the event object of the sprite
	 * @param delay The trigger interval of the event object
	 * @param activateOnce Whether it should be triggered once or continously 
	 */
	public void seteventObject(int delay, boolean activateOnce) {
		event = new EventObject(delay, activateOnce);
	}
	
	/** 
	 * Sets the x position of the sprite to a new value.
	 * @param xPos New x position of the sprite.
	 */
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Sets the y position of the sprite to a new value.
	 * @param yPos New y position of the sprite.
	 */
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	/**
	 * Gets the value of hasHit
	 * @return the value of hasHit
	 */
	public boolean gethasHit() {
		return hasHit;
	}
	
	/**
	 * Resets hasHit back to false
	 */
	public void resethasHit() {
		hasHit = false;;
	}
	
	
	
	/**
	 * Updates the x and y position of the sprite.
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 * @param xSpeed Speed of the sprite in the x direction in pixels per millisecond.
	 * @param ySpeed Speed of the sprite in the y direction in pixels per millisecond.
	 */
	public void update(Input input, int delta, float xSpeed, float ySpeed) {			
			xPos += delta * xSpeed;
			yPos += delta * ySpeed;
			
			//Updates the boundary box surrounding the sprite to its current x and y positions
			getboundary().setX(getxPos());
			getboundary().setY(getyPos());	
	}
	
	/**
	 * Checks whether the sprite has come into contact with another sprite 
	 * @param other Another spriie
	 * @return true or false about whether it has touched another sprite
	 */
	public boolean contactSprite(Sprite other) {
		if (this.boundary.intersects(other.boundary)) {
			//sets the hasHit flag to true
			hasHit = true;			
			return true;
		}
		return false;				
	}
			
	/**
	 * Draws the image of the sprite at its current x and y positions.
	 */
	public void render() {		
		spriteImage.drawCentered(xPos,yPos);	
	}	
}
