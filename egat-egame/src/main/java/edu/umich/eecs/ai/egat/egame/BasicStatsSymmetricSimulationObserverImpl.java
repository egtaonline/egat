/*
 * BasicStatsSymmetricSimulationObserverImpl.java
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
package edu.umich.eecs.ai.egat.egame;

import edu.umich.eecs.ai.egat.game.*;

import java.util.List;

/**
 * @author Patrick Jordan
 */
public class BasicStatsSymmetricSimulationObserverImpl implements BasicStatsSymmetricSimulationObserver {
    protected SymmetricSimulationObserver baseObserver;

    public BasicStatsSymmetricSimulationObserverImpl(SymmetricMultiAgentSystem simulation) {
        this(new AbstractSymmetricSimulationObserver(simulation));
    }

    public BasicStatsSymmetricSimulationObserverImpl(SymmetricSimulationObserver baseObserver) {
        this.baseObserver = baseObserver;
    }

    public SymmetricPayoff meanObservationPayoff(SymmetricOutcome outcome) {
        List<SymmetricPayoff> payoffs = getObservations(outcome);

        return EGames.meanObservationPayoff(payoffs,outcome);        
    }

    public int observationCount(SymmetricOutcome outcome) {
        return getObservations(outcome).size();
    }


    public SymmetricMultiAgentSystem getSymmetricSimulation() {
        return baseObserver.getSymmetricSimulation();
    }

    public void observe(SymmetricOutcome outcome, SymmetricPayoff payoff) {
        baseObserver.observe(outcome, payoff);
    }

    public List<SymmetricPayoff> getObservations(SymmetricOutcome outcome) {
        return baseObserver.getObservations(outcome);
    }

    public long getObservationCount() {
        return baseObserver.getObservationCount();
    }
}
