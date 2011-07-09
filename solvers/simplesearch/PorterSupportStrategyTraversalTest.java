/**
 * 
 */
package simplesearch;

import static org.junit.Assert.*;
import egat.game.*;

import java.util.*;

import org.junit.Test;

/**
 * @author Viknesh Krishnan
 *
 */
public class PorterSupportStrategyTraversalTest {
	
	private class PayoffValueImpl implements PayoffValue {
		private double value;
		
		PayoffValueImpl(double val) {
			value = val;
		}
		
		private int sgn(double n) {
			if (n < 0)
				return -1;
			if (n > 0)
				return 1;
			return 0;
		}
		
		public double getValue() {
			return value;
		}
		
		public int compareTo(PayoffValue o) {
			return sgn(value - o.getValue());
		}
	}
	
	private DefaultStrategicGame<PayoffValue> makeGame() {
		DefaultStrategicGame<PayoffValue> game = 
			new DefaultStrategicGame<PayoffValue>();
		
		Player one = Games.createPlayer("1");
		Player two = Games.createPlayer("2");
		
		Action one_a = Games.createAction("1a");
		Action one_b = Games.createAction("1b");
		Action one_c = Games.createAction("1c");
		Action two_a = Games.createAction("2a");
		Action two_b = Games.createAction("2b");
		
		Player[] playerList = new Player[2];
		playerList[0] = one;
		playerList[1] = two;
		
		Action[] actionList = new Action[2];
		actionList[0] = one_a;
		actionList[1] = two_a;
		Outcome aa = Games.createOutcome(playerList, actionList);
		
		actionList[1] = two_b;
		Outcome ab = Games.createOutcome(playerList, actionList);
		
		actionList[0] = one_b;
		actionList[1] = two_a;
		Outcome ba = Games.createOutcome(playerList, actionList);
		
		actionList[1] = two_b;
		Outcome bb = Games.createOutcome(playerList, actionList);
		
		actionList[0] = one_c;
		actionList[1] = two_a;
		Outcome ca = Games.createOutcome(playerList, actionList);
		
		actionList[1] = two_b;
		Outcome cb = Games.createOutcome(playerList, actionList);
		
		
		Set<Action> one_set = new HashSet<Action>();
		one_set.add(one_a);
		one_set.add(one_b);
		one_set.add(one_c);
		
		Set<Action> two_set = new HashSet<Action>();
		two_set.add(two_a);
		two_set.add(two_b);
		
		game.putActions(one, one_set);
		game.putActions(two, two_set);
		
		game.addPlayer(one);
		game.addPlayer(two);
		
		double[] valueList = new double[2];
		valueList[0] = 1.0;
		valueList[1] = 100.0;
		Payoff<PayoffValue> p1 = PayoffFactory.createPayoff(playerList, valueList);
		
		valueList[0] = 4.0;
		valueList[1] = 15.0;
		Payoff<PayoffValue> p2 = PayoffFactory.createPayoff(playerList, valueList);
		
		valueList[0] = 2.0;
		valueList[1] = 19.0;
		Payoff<PayoffValue> p3 = PayoffFactory.createPayoff(playerList, valueList);
		
		valueList[0] = 10.0;
		valueList[1] = 21.0;
		Payoff<PayoffValue> p4 = PayoffFactory.createPayoff(playerList, valueList);
		
	
		valueList[0] = 7.0;
		valueList[1] = 10.0;
		Payoff<PayoffValue> p5 = PayoffFactory.createPayoff(playerList, valueList);
		
		valueList[0] = 17.0;
		valueList[1] = 11.0;
		Payoff<PayoffValue> p6 = PayoffFactory.createPayoff(playerList, valueList);
		
		game.build();
		
		game.putPayoff(aa, p1);
		game.putPayoff(ab, p2);
		game.putPayoff(ba, p3);
		game.putPayoff(bb, p4);
		game.putPayoff(ca, p5);
		game.putPayoff(cb, p6);
		
		return game;
	}

	
	private void printGameInfo(DefaultStrategicGame game){
		System.out.print("Number of players is: ");
		System.out.println(game.players().size());
		
		int i = 0;
		for (Player a : game.players()){
			
			System.out.print("Number of actions for Player ");
			System.out.print(++i);
			System.out.print(" is: ");
			System.out.print(game.getActions(a).size());
			System.out.println();
		}
	}
	/**
	 * Test method for {@link simplesearch.PorterSupportStrategyTraversal#iterator()}.
	 */
	@Test
	public void testIterator() {
		
		DefaultStrategicGame<PayoffValue> game = makeGame();
		PorterSupportStrategyTraversal obj = new PorterSupportStrategyTraversal(game);
		
		Iterator<int[]> iter = obj.iterator();
		
		printGameInfo(game);
		
		int i = 0;
		while(iter.hasNext()) {
			System.out.print("The ");
			System.out.print(++i);
			System.out.println("th tuple is:");
			int[] intList = iter.next();
			for (int a : intList) {
				System.out.print(a);
				System.out.print(" ");
			}
			System.out.println();
		}	
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
