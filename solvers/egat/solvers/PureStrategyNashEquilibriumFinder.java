package egat.solvers;

import egat.game.*;

import java.util.*;

public class PureStrategyNashEquilibriumFinder {
	public static List<Outcome> findAllPSNE(StrategicGame game){
		List<Outcome> outcomes = new ArrayList<Outcome>();
			for(Outcome outcome : (SymmetricGame.class.isInstance(game) ? Games.symmetricTraversal((SymmetricMultiAgentSystem) game) : Games.traversal(game)))
				if (isPSNE(outcome, game))
					outcomes.add(outcome);
		return outcomes;
	}

	public static boolean isPSNE(Outcome outcome, StrategicGame game) {
		return Games.conditionalRegret(outcome, game) == 0;
	}
}
