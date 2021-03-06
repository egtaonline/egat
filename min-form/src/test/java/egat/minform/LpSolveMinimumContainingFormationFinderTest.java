/*
 * LpSolveMinimumContainingFormationFinderTest.java
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
package egat.minform;

import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;

import egat.gamexml.SymmetricGameHandler;
import egat.game.SymmetricGame;
import egat.game.Games;

import java.io.IOException;


/**
 * @author Patrick Jordan
 */
public class LpSolveMinimumContainingFormationFinderTest {

    @Test
    public void testSymmetricReduction() throws SAXException, ParserConfigurationException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser = factory.newSAXParser();

        SymmetricGameHandler handler = new SymmetricGameHandler();


        parser.parse( LpSolveRationalizableFinderTest.class.getResourceAsStream("/symmetric.xml") , handler);

        LpSolveMinimumContainingFormationFinder finder = new LpSolveMinimumContainingFormationFinder();

        SymmetricGame game = handler.getGame();

        assertNotNull(finder);

        assertNotNull(game);


        SymmetricGame reducedGameC = finder.findMinContainingFormation(Games.createAction("c"),game);
        SymmetricGame reducedGameD = finder.findMinContainingFormation(Games.createAction("d"),game);

        assertNotNull(reducedGameC);
        assertNotNull(reducedGameD);
    }
}
