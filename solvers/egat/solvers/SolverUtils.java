package egat.solvers;
import java.util.*;

import egat.game.*;

public class SolverUtils {
	public static Strategy getRandomStrategy(Action[] actions){
		Random r = new Random(System.currentTimeMillis());
		Double[] initial_numbers = new Double[actions.length];
		Double sum = 0.0;
		for(int i = 0; i < actions.length; i++){
			initial_numbers[i] = r.nextDouble();
			sum += initial_numbers[i];
		}
		for(int i = 0; i < actions.length; i++)
			initial_numbers[i] /= sum;
		return Games.createStrategy(actions, initial_numbers);
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
