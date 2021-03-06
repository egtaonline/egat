/*
 * FormationSearchNode.java
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

import egat.game.StrategicGame;

/**
 * @author Patrick R. Jordan
 */
public class FormationSearchNode<T extends StrategicGame, S> implements Comparable<FormationSearchNode<T,S>> {
    private double value;
    private T game;
    private S key;
    private int size;

    public FormationSearchNode(T game, S key, double value, int size) {
        this.value = value;
        this.game = game;
        this.key = key;
        this.size = size;
    }

    public double getValue() {
        return value;
    }

    public T getGame() {
        return game;
    }

    public S getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }

    public int compareTo(FormationSearchNode<T,S> o) {
        int c = Double.compare(value,o.getValue());

        if(c==0) {
            c = size - o.size;
        }

        return c;
    }
}
