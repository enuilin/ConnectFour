package cosc405.main;

import cosc405.bo.Board;
import cosc405.bo.Game;
/**
 * Start up your game however you feel like it. Only one block should be uncommented at a time.
 * @author Annie
 *
 */
public class Test {

	public static void main(String[] args) {
		
		Game game;
		
		//SINGLE PLAYER
//		game = new Game(1);
//		game.start();
		
		//AI TESTING
		game = new Game(2);
		game.start();
	}
}
