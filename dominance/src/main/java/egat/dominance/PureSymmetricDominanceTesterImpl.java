/*
 * PureSymmetricDominanceTesterImpl.java
 *
 * Copyright (C) 2006-2009 Patrick R. Jordan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package egat.dominance;

import static egat.dominance.DominanceUtils.*;

import java.io.IOException;
import java.io.Writer;

import egat.game.*;
/**
 * @author Patrick Jordan
 */

public class PureSymmetricDominanceTesterImpl implements SymmetricDominanceTester {
	private Writer writer;
	
	public PureSymmetricDominanceTesterImpl(){
		writer = null;
	}
	
	public PureSymmetricDominanceTesterImpl(Writer inWriter){
		writer = inWriter;
	}
	
    public boolean isDominated(Action action, SymmetricGame game) {
        Player[] players = game.players().toArray(new Player[0]);
        Action[] actions = new Action[players.length];

        int playerIndex = 0;

        SymmetricMultiAgentSystem reduced = createPlayerReducedSymmetricSimulation(players[playerIndex], game);
        Action[] testActions = new Action[game.getActions().size()];
        game.getActions().toArray(testActions);
        for (Action a : testActions) {

            if(a.equals(action)) {
                continue;
            }

            boolean dominated = true;

            for (SymmetricOutcome outcome : Games.symmetricTraversal(reduced)) {
                if(isPayoffUndominated(action, a, game, outcome, players, actions, playerIndex)) {
                    dominated = false;
                    break;
                }
            }

            if (dominated) {
            	if (writer != null)
            		try {
            			writer.append("\n"+action+" is dominated by "+a+"\n");
            			writer.flush();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
                return true;
            }
        }

        return false;
    }

    public boolean isDominated(Strategy strategy, SymmetricGame game) {
        Player[] players = game.players().toArray(new Player[0]);
        Strategy[] strategies = new Strategy[players.length];

        int playerIndex = 0;

        SymmetricMultiAgentSystem reduced = createPlayerReducedSymmetricSimulation(players[playerIndex], game);

        for (Action a : game.getActions()) {
            Strategy s = Games.createPureStrategy(a);

            if(s.equals(strategy)) {
                continue;
            }

            boolean dominated = true;

            for (SymmetricOutcome outcome : Games.symmetricTraversal(reduced)) {
                if(isPayoffUndominated(strategy, s, game, outcome, players, strategies, playerIndex)) {
                    dominated = false;
                    break;
                }
            }

            if (dominated) {
                return true;
            }
        }

        return false;
    }

    protected static boolean isPayoffUndominated(Action baseAction, Action deviantAction, StrategicGame game,
                                               Outcome background, Player[] players, Action[] actions, int index) {
        for(int i = 0; i < players.length; i++) {
            if(i!=index) {
                actions[i] = background.getAction(players[i]);
            }
        }

        actions[index] = baseAction;
        double basePayoff = 0.0;
        try{
        	basePayoff = game.payoff(Games.createOutcome(players,actions)).getPayoff(players[index]).getValue();
        }
        catch (NonexistentPayoffException e){
        	return false;
        }
        actions[index] = deviantAction;
        double deviantPayoff = 0.0;
        try{
        	deviantPayoff = game.payoff(Games.createOutcome(players,actions)).getPayoff(players[index]).getValue();
        }
        catch (NonexistentPayoffException e){
        	return true;
        }
        return basePayoff >= deviantPayoff;
    }

    protected static boolean isPayoffUndominated(Strategy baseStrategy, Strategy deviantStrategy, StrategicGame game,
                                               Outcome background, Player[] players, Strategy[] strategies, int index) {
        for(int i = 0; i < players.length; i++) {
            if(i!=index) {
                strategies[i] = Games.createPureStrategy(background.getAction(players[i]));
            }
        }

        strategies[index] = baseStrategy;

        double basePayoff = game.payoff(Games.createProfile(players,strategies)).getPayoff(players[index]).getValue();

        strategies[index] = deviantStrategy;

        double deviantPayoff = game.payoff(Games.createProfile(players,strategies)).getPayoff(players[index]).getValue();

        return basePayoff >= deviantPayoff;
    }
}
