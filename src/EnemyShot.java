import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * EnemyShot class, lasers that the boss and basicshooter shoots. Extends the sprite class, handling any unique behaviour of the enemy shot. 
 * @author Kevin.E
 *
 */
public class EnemyShot extends Sprite{
	//Movement speed of the laser in pixels per millisecond.
		private static final float ENEMY_SHOT_SPEED = .7f;
		//Movement speed of the laser when it is not moving in pixels per millisecond.
		private static final float NO_MOVEMENT_SPEED = 0;
		//if enemyshot should be deleted
		private boolean shouldDel = false;
		
		/**
		 * Construct the enemy shot sprite 
		 * @param imageSrc Location of image to be used for this enemy shot.
		 * @param x The initial x location of the enemy shot.
		 * @param y The initial x location of the enemy shot.
		 * @throws SlickException
		 */
		public EnemyShot(String imageSrc, float x, float y, int delay, boolean activateOnce) throws SlickException {
			super(imageSrc,x,y,delay,activateOnce);
		}	
		
		/**
		 * checks whether the the enemy shot should be deleted
		 * @return the boolean of shouldDel
		 */
		public boolean getshouldDel() {
			return shouldDel;
		}
		
		/**
		 * Updates x and y position of the laser sprite.
		 * @param input The slick input object, used for handling all inputs from keyboard.
		 * @param delta Time passed since last frame (milliseconds).
		 */
		public void move(Input input, int delta) {		
			//Sets the flag for the enemyshot to be deleted if it moves offscreen
			if(super.getyPos()<=0) {
				shouldDel=true;
			}
			//Updates y position of the laser sprite based on its constant speed.
			super.update(input, delta, NO_MOVEMENT_SPEED, ENEMY_SHOT_SPEED);								
		   }		
	}




