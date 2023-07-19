/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		
		/*this is not necessary
		case 'f':
		        /*this is not necessary
		        Main.sfx.playWAV();
		        break;
		*/
			
		case 'p':
		        if(Main.song.isPlaying()) Main.song.pauseWAV();
		        else Main.song.setLoop();
		        break;
		/*case 't':
		        //resets animation text
		        Main.atext.resetAnimation();
		        break;*/
		case 'u':
                        //Main.trigger = "u is triggered";
                        break;
		case '$':
		        //Main.trigger = "space is triggered";
		        break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}
}