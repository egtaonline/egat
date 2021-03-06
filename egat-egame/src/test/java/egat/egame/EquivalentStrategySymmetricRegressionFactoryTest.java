/*
 * EquivalentStrategySymmetricRegressionFactoryTest.java
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
package egat.egame;

import org.junit.Test;
import org.junit.Assert;
import egat.game.*;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrick Jordan
 */
public class EquivalentStrategySymmetricRegressionFactoryTest {
    @Test
    public void testRegression() {
        Player[] players = new Player[]{Games.createPlayer("Alice"),
                                        Games.createPlayer("Bob")};
        Action[] actions = new Action[] {Games.createAction("a"),
                                         Games.createAction("b"),
                                         Games.createAction("c")};

        Map<Action,Action> actionConverter = new HashMap<Action,Action>();

        actionConverter.put(Games.createAction("a"), Games.createAction("{a,b}"));
        actionConverter.put(Games.createAction("b"), Games.createAction("{a,b}"));
        actionConverter.put(Games.createAction("c"), Games.createAction("c"));

        EquivalentStrategySymmetricRegressionFactory factory = new EquivalentStrategySymmetricRegressionFactory(actionConverter);

        SymmetricMultiAgentSystem sim = createTestSimulation(players,actions);

        SymmetricSimulationObserver observer = new AbstractSymmetricSimulationObserver(sim);

        addObservations(observer, players);

        SymmetricRegression regression = factory.regress(observer);

        Action[] reducedAction = new Action[] {Games.createAction("{a,b}"), Games.createAction("{a,b}")};
        SymmetricOutcome outcome = Games.createSymmetricOutcome(players, reducedAction);
        SymmetricPayoff p = regression.getSymmetricGame().payoff(outcome);
        Map<Action,PayoffValue> values = new HashMap<Action,PayoffValue>();
        values.put(Games.createAction("{a,b}"),PayoffFactory.createPayoffValue(14.5/3.0));

        SymmetricPayoff p1 = PayoffFactory.createSymmetricPayoff(values,outcome);

        Assert.assertEquals(p,p1);
    }


    protected SymmetricMultiAgentSystem createTestSimulation(Player[] players, Action[] actions) {
        MutableSymmetricMultiAgentSystem sim = new DefaultSymmetricMultiAgentSystem("test","testRegression");

        for(Player p : players) {
            sim.addPlayer(p);
        }

        for(Action a : actions) {
            sim.addAction(a);
        }

        return sim;
    }

    protected void addObservations(SymmetricSimulationObserver observer, Player[] players) {
        double count = 0.0;
        for (SymmetricOutcome outcome : Games.symmetricTraversal(observer.getSymmetricSimulation())) {
            Map<Action,PayoffValue> values = new HashMap<Action,PayoffValue>();

            for(Action a: observer.getSymmetricSimulation().getActions()) {
                if(outcome.getCount(a)>0) {
                    values.put(a,PayoffFactory.createPayoffValue(count++));
                }
            }
            SymmetricPayoff payoff = PayoffFactory.createSymmetricPayoff( values, outcome );
            observer.observe(outcome, payoff);
        }
    }
}
