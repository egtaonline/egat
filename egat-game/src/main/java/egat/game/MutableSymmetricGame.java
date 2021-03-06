/*
 * MutableSymmetricGame.java
 *
 * Copyright (C) 2006-2010 Patrick R. Jordan
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
package egat.game;

import java.util.Map;

/**
 * Mutable symmetric game is an extension of the {@link SymmetricGame symmetric game} interface that allows
 * the payoffs for a given {@link Outcome outcome} to be set.
 *
 * @param <T> the payoff value type.
 * @author Patrick Jordan
 */
public interface MutableSymmetricGame<T extends PayoffValue> extends MutableMultiAgentSystem,
                                                                     SymmetricGame<T>,
                                                                     MutableSymmetricMultiAgentSystem {
    /**
     * Sets the payoff for a given outcome.
     * @param outcome the outcome whose payoff is to be set.
     * @param values the mapping for the values.
     */
    void putPayoff(Outcome outcome, Map<Action, T> values);
}
