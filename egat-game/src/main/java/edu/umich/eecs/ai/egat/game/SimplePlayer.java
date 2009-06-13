/*
 * SimplePlayer.java
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
package edu.umich.eecs.ai.egat.game;

import java.io.Serializable;

/**
 * The simple player class is solely defined by the supplied player id.
 *
 * @author Patrick Jordan
 */
public class SimplePlayer extends AbstractIdentifiable<Player> implements Player, Serializable {
    public SimplePlayer(final String id) {
        super(id);
    }

    protected final boolean checkClass(final Object o) {
        return o instanceof Player;
    }
}
