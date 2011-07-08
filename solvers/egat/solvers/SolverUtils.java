package egat.solvers;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import egat.game.*;
import egat.game.utils.AllCombinationsGenerator;
import egat.game.utils.CrossProductIterator;
import egat.gamexml.SymmetricGameWriter;
import egat.replicatordynamics.PayoffMatrix;

public class SolverUtils {
	private static RandomData rd = new RandomDataImpl();
	
	public static Strategy getRandomStrategy(Action[] actions){
		Double[] initial_numbers = new Double[actions.length];
		Double sum = 0.0;
		for(int i = 0; i < actions.length; i++){
			initial_numbers[i] = rd.nextExponential(1.0);
			sum += initial_numbers[i];
		}
		for(int i = 0; i < actions.length; i++)
			initial_numbers[i] /= sum;
		return Games.createStrategy(actions, initial_numbers);
	}
	
	public static double minimumPayoffValue(SymmetricGame game){
		double min = Double.MAX_VALUE;
		for (Outcome o : Games.symmetricTraversal(game))
			try{
				for(Map.Entry<Player, PayoffValue> p : (Set<Map.Entry<Player, PayoffValue>>) game.payoff(o).entrySet()){
					min = (Double) (p.getValue().getValue() > min ? min : p.getValue().getValue());
				}
			}
			catch (NonexistentPayoffException e){
				
			}
		return min;
	}
	
	public static SymmetricGame fillInMissingValues(SymmetricGame game) throws IOException{
		double min = SolverUtils.minimumPayoffValue(game);
		DefaultSymmetricGame g = new DefaultSymmetricGame();
		g.addAllActions(game.getActions());
		for(Player p : game.players())
			g.addPlayer(p);
		g.build();
		for (Outcome o : Games.symmetricTraversal(game))
			try{
				g.putPayoff(o, game.payoff(o).valueMap());
			}
			catch (NonexistentPayoffException e){
				Map<Action, PayoffValue> map = new HashMap<Action, PayoffValue>();
				for(Action a : o.actions())
					map.put(a, PayoffFactory.createPayoffValue(min));
				g.putPayoff(o, map);
			}
		return g;
	}
	
	public static List<List<Action>> maxCliques(SymmetricGame game){
		List<List<Action>> maxCliques = getCliques(game);
		List<List<Action>> toRemove = new ArrayList<List<Action>>();
		for(List<Action> clique : maxCliques.subList(0, maxCliques.size()-1))
			for(List<Action> clique2 : maxCliques.subList(maxCliques.indexOf(clique)+1, maxCliques.size()))
				if (clique2.containsAll(clique)){
					toRemove.add(clique);
					break;
				}
		maxCliques.removeAll(toRemove);
		return maxCliques;
	}
	
	public static List<List<Action>> getCliques(SymmetricGame game){
		List<Action> actionList = new ArrayList<Action>();
		actionList.addAll(game.getActions());
		List<List<Action>> combinations = AllCombinationsGenerator.generate(actionList, actionList.size());
		for (SymmetricOutcome o : Games.symmetricTraversal(game)){
			try{
				game.payoff(o);
			}
			catch (NonexistentPayoffException e) {
				List<List<Action>> toRemove = new ArrayList<List<Action>>();
				List<Action> actions = new ArrayList<Action>();
				for(Action a : o.actions())
					if(actions.contains(a) == false)
						actions.add(a);
				for(List<Action> combination : combinations){
					if (combination.containsAll(actions))
						toRemove.add(combination);
				}
				if(toRemove.size() != 0)
					combinations.removeAll(toRemove);
			}
		}
		return combinations;
	}
	
    public static double Linfinity(double[] a, double[] b) {
        double norm = 0;

        for (int i = 0; i < a.length; i++) {
            norm = Math.max(Math.abs(a[i] - b[i]), norm);
        }

        return norm;
    }

	public static boolean unique(Strategy strategy, List<Strategy> strategies, double tolerance) {
		if (strategies.size() == 0)
			return true;
		for (Strategy s : strategies){
			double [] a = new double[strategy.actions().size()];
			double [] b = new double[strategy.actions().size()];
			int i = 0;
			for (Action action: strategy.actions()){
				a[i] = (Double) strategy.getProbability(action);
				b[i] = (Double) s.getProbability(action);
				i++;
			}
			if (Linfinity(a, b) < tolerance)
				return false;
		}
		return true;
	}
}
