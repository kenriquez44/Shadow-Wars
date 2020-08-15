import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Player class, the class that we can control.  Extends the sprite class, handling unique movement of the player sprite. 
 * @author Kevin.E
 *
 */
public class Player extends Sprite {
	//Movement speed of the player sprite in pixels per millisecond.
	private static final float PLAYER_MOVESPEED = .5f;
	//Minimum x position that player sprite is allowed to move to. 
	private static final int PLAYER_LEFT_BORDER = 32;
	//Maximum x position that player sprite is allowed to move to. 
    private static final int PLAYER_RIGHT_BORDER = 992;
    //Minimum y position that player sprite is allowed to move to. 
	private static final int PLAYER_TOP_BORDER = 32;
	//Maximum y position that player sprite is allowed to move to. 
	private static final int PLAYER_BOTTOM_BORDER = 736;
	//Movement speed of player sprite when not moving in pixels per millisecond. 
	private static final float NO_MOVEMENT_SPEED = 0;
	//Time duration of the effects of a powerup in milliseconds
	private static final int POWER_DURATION = 5000;
	//Time duration of a player's ability to shoot in milliseconds
	private static final int SHOOT_DELAY = 350;
	//The shield that surrounds the player
	private Shield shield;
	//The number of lives of the player
	private int numLives =3;
	//Flag for whether the player has lost a life
	private boolean lifeLost = false;	
	//eventobject corresponding to the player's delay between shots
	private EventObject shootDelay;
	
	
	

	/**
	 * Constructs the player sprite
	 * @param imageSrc Location of image to be used for this player.
	 * @param x The initial x location of the player.
	 * @param y The initial x location of the player.
	 * @param delay The delay of the event object in milliseconds
	 * @param activateOnce Whether the eventobject should activate once or continously
	 * @param shield The shield that surrounds the player
	 * @throws SlickException
	 */
	public Player(String imageSrc, float x, float y, int delay, boolean activateOnce, Shield shield) throws SlickException {
		super(imageSrc,x,y,delay,activateOnce);	
		this.shield = shield;
		//Creates an eventobject to be used as a timer for the delay between shots
		shootDelay = new EventObject(SHOOT_DELAY, false);
	}
	
	/**
	 * sets the shield of the player
	 * @param shield The shield of the player
	 */
	public void setShield(Shield shield) {
		this.shield = shield;
	}
	/**
	 * gets the shootdelay event object
	 * @return the shootdelay event object
	 */
	public EventObject getshootDelay() {
		return shootDelay;
	}
	/**
	 * sets the lifeLost flag
	 */
	public void setlifeLost() {
		lifeLost = !lifeLost;
	}
	
	/**
	 * gets the lifeLost flag
	 * @return the lifeLost flag
	 */
	public boolean getlifeLost() {
		return lifeLost;
	}
	
	/**
	 * sets the shootDelay event object
	 * @param delay The time interval of the event object in milliseconds 
	 * @param activateOnce Whether it should be activated once or continously
	 */
	public void setshootDelay(int delay, boolean activateOnce) {
		shootDelay = new EventObject(delay, activateOnce);
	}
	
	/**
	 * sets the delay of the player's event object
	 * @param delay The time interval of the event object in milliseconds 
	 * @param activateOnce  Whether it should be activated once or continously
	 */
	public void setDelay(int delay, boolean activateOnce) {
		this.seteventObject(delay, activateOnce);
	}
	
	/**
	 * checks whether the player has contacted a powerup
	 * @param other a powerup
	 * @return true or false 
	 */
	public boolean contactPower(Powerup other) {
		if (this.getboundary().intersects(other.getboundary())) {							
			return true;			
		}		
		return false;
	}
	
	/**
	 * sets a new shield for the a player and sets the flag for it to be drawn to be true
	 */
	public void newShield() {
		shield.setdrawImage(true);
		shield.seteventObject(POWER_DURATION, true);	
	}
	
	/**
	 * Updates all logic associated with  the player sprite.
	 * @param input The slick input object, used for handling all inputs from keyboard.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void move(Input input, int delta) {		
		//Updates timer of players's event object (related to time duration of a powerup)
		super.geteventObject().update(delta);
		
		//Checks whether the timer is ready and if so, sets the shootDelay back to 350 milliseconds
		if(super.geteventObject().isReady()) {
			setshootDelay(SHOOT_DELAY, false);
		}
	
		//Updates the shootdelay event object
		shootDelay.update(delta);
		
		//If up key is pressed, player moves up in the y direction.
		if (input.isKeyDown(Input.KEY_UP)) {			
			super.update(input, delta, NO_MOVEMENT_SPEED,-PLAYER_MOVESPEED);			
		}
		
		//If down key is pressed, player moves down the in the y direction.
		if (input.isKeyDown(Input.KEY_DOWN)) {			
			super.update(input, delta, NO_MOVEMENT_SPEED, PLAYER_MOVESPEED);		
		}
		
		//If left key is pressed, player moves to the left in the x direction.
		if (input.isKeyDown(Input.KEY_LEFT)) {			
			super.update(input, delta, -PLAYER_MOVESPEED, NO_MOVEMENT_SPEED);		
		}
		
		//If right key is pressed, player moves right in the x direction.
		if (input.isKeyDown(Input.KEY_RIGHT)) {			
			super.update(input, delta, PLAYER_MOVESPEED, NO_MOVEMENT_SPEED);		
		}	
		
		//If player x position reaches the left border, continuously updates its x position to the left border location, preventing movement.
		if(getxPos()<PLAYER_LEFT_BORDER) {
			setxPos(PLAYER_LEFT_BORDER);
		}
		
		//If player x position reaches the right border, continuously updates its x position to the right border location, preventing movement.
		if(getxPos()>PLAYER_RIGHT_BORDER) {
			setxPos(PLAYER_RIGHT_BORDER);
		}
		
		//If player y position reaches the top border, continuously updates its y position to the top border location, preventing movement.
		if(getyPos()<PLAYER_TOP_BORDER) {
			setyPos(PLAYER_TOP_BORDER);
		}
		
		//If player y position reaches the bottom border, continuously updates its y position to the bottom border location, preventing movement.
		if(getyPos()>PLAYER_BOTTOM_BORDER) { 
			setyPos(PLAYER_BOTTOM_BORDER);
		}
		
		//Updates the bounding box surrounding the player to its current location as it moves around the screen.
		
		
		//checks if the player has been hit by an enemy, and if so, player loses a life and gains a shield for 3000 milliseconds
		if(super.gethasHit()){
			if(!shield.getdrawImage()) {
				shield.setdrawImage(true);
				shield.seteventObject(3000, true);								
				super.resethasHit();					
				numLives-=1;
				lifeLost = true;
			}
			else {
				super.resethasHit();
			}
		}
		//if player has no more lives, game exits
		if(numLives==0) {
			System.exit(0);
		}
		
		
		
	}
}
		
	

