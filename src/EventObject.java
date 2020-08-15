/**
 * EventObject class, used to deal with any timers associated with sprites. 
 * @author Kevin.E
 *
 */
public class EventObject {
	//amount of time passed since the last activation in millisecconds
	private int timeSinceTrigger = 0;
	//the intended time duration between each activation in milliseconds
	private int triggerInterval = 0;
	//flag for whether it should be activated only once or continously
	private boolean activateOnce;
	//flag for whether it has already been activated
	private boolean activated;
	
	/**
	 * Constructs the eventobject class. 
	 * @param triggerInterval the intended time duration between each activation in milliseconds
	 * @param activateOnce /flag for whether it should be activated only once or continously
	 */
	public EventObject(int triggerInterval, boolean activateOnce) {
		this.triggerInterval = triggerInterval;
		this.activateOnce = activateOnce;
	}
	
	/**
	 * Updates the amount of time that has passed since the last activation in milliseconds
	 * @param delta
	 */
	public void update(float delta) {
		timeSinceTrigger+=delta;
	}
	
	/**
	 * gets the amount time that has passed since the last activation in milliseconds
	 * @return the amount time that has passed since the last activation in milliseconds
	 */
	public float getTime() {
		return timeSinceTrigger;
	}
	
	/**
	 * checks whether the eventobject is ready to be activated
	 * @return
	 */
	public boolean isReady() {
		//checks whether it is meant to only be activated once and has already done so previously
		if(activateOnce && activated ) {
			return false;
		}
		
		//checks whether enough time has elapsed and therefore should be ready to be activated
		if(timeSinceTrigger >= triggerInterval) {
			//checks if its meant to activate once and sets that flag to true
			if(activateOnce) {
				activated = true;
			}
			//resets the time since activation
			else {
				timeSinceTrigger = 0;
				
			}
			return true;
		}
		//returns false if enough time has passed
		return false;
	}
	
}
