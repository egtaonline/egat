/**
 * 
 */
package simplesearch;

import egat.game.*;

import java.util.*;

/**
 * @author Viknesh
 *
 */


/* This will only initialize supports when specific supports are chosen
 * in the outer loop of the Recursive Backtracking call
 *
 * **Check first to make sure that playerActions.size() geq supportSize!**
 */
public class PlayerDomain {

	Player thePlayer;
	StrategicGame theGame;
	int supportSize;
	
	Set<Action> playerActions;
	Set<Set<Action>> playerSupports;
	
	int numActions;

	
	PlayerDomain(StrategicGame game, Player player, int size){
		theGame = game;
		thePlayer = player;
		playerActions = game.getActions(player);
		supportSize = size;
		playerSupports = null;
		numActions = playerActions.size();
	}
	
	public void removeAction(Action rm){
		...
		numActions = playerActions.size();
		
	}
	
	public Set<Set<Action>> getSupports(){
		if (playerSupports == null){
			generateSupports();
		}
		
		return playerSupports;
	}
	
	private void generateSupports(){
		numActions = playerActions.size();
		generateSupportsHelper(-1, 0);
	}
	
	private void generateSupportsHelper(int i, int numIndiciesAssigned){
		int indiciesLeftToAssign = numActions - (numIndiciesAssigned+1);
		for (int j = i+1; j < indiciesLeftToAssign; j++){
			generateSupportsHelper
		}
	}
	
}
