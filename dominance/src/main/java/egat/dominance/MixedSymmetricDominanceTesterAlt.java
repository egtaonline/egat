package egat.dominance;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import egat.game.Action;
import egat.game.Games;
import egat.game.Outcome;
import egat.game.Player;
import egat.game.Strategy;
import egat.game.SymmetricGame;

public class MixedSymmetricDominanceTesterAlt extends
		MixedSymmetricDominanceTesterImpl {

	@Override
	public boolean isDominated(Action action, SymmetricGame game) {
		Player p = game.players().iterator().next();
		for(Outcome o: Games.symmetricTraversal(DominanceUtils.createPlayerReducedSymmetricSimulation(p, game))){
			Set<Player> players = o.players();
			players.add(p);
			Collection<Action> actions = o.actions();
			actions.add(action);
			Player [] pl = new Player[game.players().size()];
			players.toArray(pl);
			Action [] act = new Action[game.players().size()];
			actions.toArray(act);
			if(Games.isAWeakBestResponse(p, Games.createSymmetricOutcome(pl, act), action, game))
				return false;
		}
		return true;
	}
}
