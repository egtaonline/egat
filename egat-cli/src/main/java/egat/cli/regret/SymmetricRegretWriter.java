/*
 * SymmetricRegretWriter.java
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
package egat.cli.regret;
import egat.game.*;
import java.io.PrintStream;
import java.util.Map;

/**
 * @author Patrick Jordan
 */
public class SymmetricRegretWriter {
    private PrintStream printStream;

    public SymmetricRegretWriter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public PrintStream writeRegret(SymmetricGame game) {
        writeHeader();

        for (SymmetricOutcome outcome : Games.symmetricTraversal(game)) {
            writeRegret(outcome, Games.regret(outcome, game));
        }

        writeFooter().flush();

        return printStream;
    }

    protected PrintStream writeRegret(SymmetricOutcome outcome, double regret) {
        printStream.print(String.format("<profile regret=\"%s\">", regret));

        for (Map.Entry<Action, Integer> entry : outcome.actionEntrySet()) {
            printStream.print(String.format("<outcome action=\"%s\" count=\"%s\" />", entry.getKey(), entry.getValue()));
        }

        printStream.print("</profile>");

        return printStream;
    }

    protected PrintStream writeHeader() {
        printStream.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        printStream.print("<profiles>");

        return printStream;
    }

    protected PrintStream writeFooter() {
        printStream.print("</profiles>");

        return printStream;
    }
}
