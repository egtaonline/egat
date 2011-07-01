/*
 * StrategicTauGreedyFormationSearch.java
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
package egat.minform.search;
import egat.game.*;
import egat.minform.StrategicRationalizableFinder;

import java.util.*;

/**
 * @author Patrick R. Jordan
 */
public class StrategicTauGreedyFormationSearch extends TauGreedyFormationSearch<StrategicGame, StrategicMultiAgentSystem, Map<Player, Set<Action>>> {
    private Player[] players;
    private Action[][] actions;
    private StrategicRationalizableFinder rationalizableFinder;
    private double tolerance;

    public StrategicTauGreedyFormationSearch(StrategicGame base, StrategicRationalizableFinder rationalizableFinder) {
        this(base, rationalizableFinder, 1e-8);
    }

    public StrategicTauGreedyFormationSearch(StrategicGame base, StrategicRationalizableFinder rationalizableFinder, double tolerance) {
        super(base);

        this.rationalizableFinder = rationalizableFinder;
        this.tolerance = tolerance;

        players = base.players().toArray(new Player[0]);

        actions = new Action[players.length][];

        for (int i = 0; i < players.length; i++) {

            actions[i] = base.getActions(players[i]).toArray(new Action[0]);

        }
    }

    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> initialNode(StrategicGame base, int bound) {
        PriorityQueue<FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>> nodes = new PriorityQueue<FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>>();

        int total = 1;
        for (Action[] a : actions) {
            total *= a.length;
        }

        for (int i = 0; i < total; i++) {
            int remainder = i;

            Map<Player, Set<Action>> strategySpace = new HashMap<Player, Set<Action>>();

            StrategicGame game = base;

            for (int j = actions.length - 1; j >= 0; j--) {
                int offset = remainder % (actions[j].length);
                remainder /= actions[j].length;

                Player p = players[j];
                Action a = actions[j][offset];
                Set<Action> s = new HashSet<Action>();
                s.add(a);

                strategySpace.put(p, s);

                game = new ActionReducedStrategicGame(game, p, s);
            }

            if (1 <= bound) {
                double epsilon = rationalizableFinder.rationalizableEpsilon(game, base);
                epsilon = Math.round(epsilon / tolerance) * tolerance;

                FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node = new FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>(game, strategySpace, epsilon, 1);

                nodes.offer(node);
            }
        }

        return nodes.poll();
    }

    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> expandNode(FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node, int bound) {

        FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> maxNode = null;
        double maxTau = Double.NEGATIVE_INFINITY;


        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            for (int actionIndex = 0; actionIndex < actions[playerIndex].length; actionIndex++) {
                if (!node.getGame().getActions(players[playerIndex]).contains(actions[playerIndex][actionIndex])) {

                    double tau = rationalizableFinder.rationalizableTau(players[playerIndex], actions[playerIndex][actionIndex], node.getGame(), getBase());

                    if (tau > maxTau) {
                        Set<Action> newActions = new HashSet<Action>(node.getGame().getActions(players[playerIndex]));

                        newActions.add(actions[playerIndex][actionIndex]);

                        Map<Player, Set<Action>> key = new HashMap<Player, Set<Action>>();

                        for (int k = 0; k < players.length; k++) {
                            key.put(players[k], k == playerIndex ? newActions : node.getGame().getActions(players[k]));
                        }

                        FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> child = createNode(key, bound);

                        if (child != null) {
                            maxNode = child;
                            maxTau = tau;
                        }

                    }
                }
            }
        }


        return maxNode;
    }

    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> createNode(Map<Player, Set<Action>> strategySpace, int bound) {
        FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node = null;
        StrategicGame game = getBase();

        int total = 1;
        for (Player p : strategySpace.keySet()) {
            Set<Action> playerActions = strategySpace.get(p);
            game = new ActionReducedStrategicGame(game, p, playerActions);
            total *= playerActions.size();
        }


        if (total <= bound) {
            double epsilon = rationalizableFinder.rationalizableEpsilon(game, getBase());
            epsilon = Math.round(epsilon / tolerance) * tolerance;

            node = new FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>(game, strategySpace, epsilon, total);

        }

        return node;
    }

    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> createNode(Map<Player, Set<Action>> strategySpace) {
        FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node = null;
        StrategicGame game = getBase();

        int total = 1;
        for (Player p : strategySpace.keySet()) {
            Set<Action> playerActions = strategySpace.get(p);
            game = new ActionReducedStrategicGame(game, p, playerActions);
            total *= playerActions.size();
        }


        double epsilon = rationalizableFinder.rationalizableEpsilon(game, getBase());
        epsilon = Math.round(epsilon / tolerance) * tolerance;

        node = new FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>(game, strategySpace, epsilon, total);


        return node;
    }
    
    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> initialNode(StrategicGame base, StrategicMultiAgentSystem bound) {
        PriorityQueue<FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>> nodes = new PriorityQueue<FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>>();

        int total = 1;
        for (Action[] a : actions) {
            total *= a.length;
        }

        for (int i = 0; i < total; i++) {
            int remainder = i;

            Map<Player, Set<Action>> strategySpace = new HashMap<Player, Set<Action>>();

            StrategicGame game = base;

            boolean flag = true;

            for (int j = actions.length - 1; j >= 0; j--) {
                int offset = remainder % (actions[j].length);
                remainder /= actions[j].length;

                Player p = players[j];
                Action a = actions[j][offset];
                Set<Action> s = new HashSet<Action>();
                s.add(a);

                strategySpace.put(p, s);

                game = new ActionReducedStrategicGame(game, p, s);

                flag &= bound.getActions(players[j]).contains(actions[j]);
            }

            if (flag) {
                double epsilon = rationalizableFinder.rationalizableEpsilon(game, base);
                epsilon = Math.round(epsilon / tolerance) * tolerance;

                FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node = new FormationSearchNode<StrategicGame, Map<Player, Set<Action>>>(game, strategySpace, epsilon, 1);

                nodes.offer(node);
            }
        }

        return nodes.poll();
    }

    protected FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> expandNode(FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> node, StrategicMultiAgentSystem bound) {
        FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> maxNode = null;
        double maxTau = Double.NEGATIVE_INFINITY;        

        for (int i = 0; i < players.length; i++) {

            Set<Action> currentPlayerActions = node.getGame().getActions(players[i]);

            if (currentPlayerActions.size() != bound.getActions(players[i]).size()) {

                for (int j = 0; j < actions[i].length; j++) {

                    if (bound.getActions(players[i]).contains(actions[i][j]) && !currentPlayerActions.contains(actions[i][j])) {

                        double tau = rationalizableFinder.rationalizableTau(players[i], actions[i][j], node.getGame(), getBase());

                        if (tau > maxTau) {
                            Set<Action> newActions = new HashSet<Action>(currentPlayerActions);

                            newActions.add(actions[i][j]);

                            Map<Player, Set<Action>> key = new HashMap<Player, Set<Action>>();

                            for (int k = 0; k < players.length; k++) {
                                key.put(players[k], k == i ? newActions : node.getGame().getActions(players[k]));
                            }

                            FormationSearchNode<StrategicGame, Map<Player, Set<Action>>> child = createNode(key);

                            if (child != null) {
                                maxNode = child;
                                maxTau = tau;
                            }
                        }
                    }
                }
            }
        }

        return maxNode;
    }
}
