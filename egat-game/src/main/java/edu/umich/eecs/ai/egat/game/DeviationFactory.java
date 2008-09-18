/**
 * $Id$
 * $Date$
 * $Author$
 * $Revision$
 */
package edu.umich.eecs.ai.egat.game;

import java.util.*;

/**
 * @author Patrick Jordan
 */
public class DeviationFactory {
    private DeviationFactory() {
    }

    public static OutcomeTraversal deviationTraversal(Outcome outcome, StrategicGame game, Player player) {
        return new DeviationTraversal(outcome,game,player);
    }

    public static SymmetricOutcomeTraversal deviationTraversal(SymmetricOutcome outcome, SymmetricGame game) {
        return new SymmetricDeviationTraversal(outcome,game);
    }
    
    static class DeviationTraversal implements OutcomeTraversal {
        Outcome outcome;
        StrategicGame game;
        Player player;
        public DeviationTraversal(Outcome outcome, StrategicGame game, Player player) {
            this.outcome = outcome;
            this.game = game;
            this.player = player;
        }

        public Iterator<Outcome> iterator() {
            return new DeviationIterator(outcome,game,player);
        }
    }

    static class SymmetricDeviationTraversal implements SymmetricOutcomeTraversal {
        SymmetricOutcome outcome;
        SymmetricGame game;
        public SymmetricDeviationTraversal(SymmetricOutcome outcome, SymmetricGame game) {
            this.outcome = outcome;
            this.game = game;

        }

        public Iterator<SymmetricOutcome> iterator() {
            return new SymmetricDeviationIterator(outcome,game);
        }
    }

    static class DeviationIterator implements Iterator<Outcome> {
        Outcome base;
        StrategicGame game;
        Player[] players;
        Action[] actions;
        Outcome cur;
        Iterator<Action> actionIterator;
        int playerIdx;

        public DeviationIterator(Outcome outcome, StrategicGame game, Player player) {
            this.base = outcome;
            this.game = game;


            players = game.players().toArray(new Player[0]);
            playerIdx = -1;
            // Find the player index
            for(int i = 0; i < players.length; i++) {
                if(player.equals(players[i])) {
                    playerIdx = i;
                    break;
                }
            }

            actions = new Action[players.length];
            for(int i = 0; i < players.length; i++ ) {
                actions[i] = outcome.getAction(players[i]);
            }

            Set<Action> set = new HashSet<Action>(game.getActions(player));
            set.remove(actions[playerIdx]);
            actionIterator = set.iterator();
            update();
        }

        public boolean hasNext() {
            return cur!=null;
        }

        public Outcome next() {
            Outcome next = cur;
            update();
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported in this iterator.");
        }

        private void update() {
             if(actionIterator==null || !actionIterator.hasNext()) {
                cur = null;
            } else {
                actions[playerIdx] = actionIterator.next();
                cur = Games.createOutcome(players,actions);
            }
        }

    }

    static class SymmetricDeviationIterator implements Iterator<SymmetricOutcome> {
        SymmetricOutcome base;
        SymmetricGame game;
        SymmetricOutcome cur;
        List<Player> players;
        DefaultSymmetricOutcomeMap<Boolean> outcomeMap;
        Iterator<Outcome> iterator;

        public SymmetricDeviationIterator(SymmetricOutcome outcome, SymmetricGame game) {
            this.base = outcome;
            this.game = game;
            players = new LinkedList<Player>(game.players());
            outcomeMap = new DefaultSymmetricOutcomeMap<Boolean>();
            outcomeMap.put(outcome,true);
            update();
        }

        public boolean hasNext() {
            return cur!=null;
        }

        public SymmetricOutcome next() {
            SymmetricOutcome next = cur;
            update();
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is not supported in this iterator.");
        }

        private void update() {
            if(iterator==null || !iterator.hasNext()) {
                cur = null;

                if(!players.isEmpty()) {
                    Player p = players.remove(0);
                    iterator =  new DeviationIterator(base,game,p);
                    update();
                } else {
                    return;
                }
            } else {
                SymmetricOutcome so = Games.createSymmetricOutcome(iterator.next());
                if(outcomeMap.get(so)!=null) {
                    update();
                    return;
                }

                cur = so;
                outcomeMap.put(so,true);
            }
        }

    }
}
