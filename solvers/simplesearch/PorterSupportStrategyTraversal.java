/**
 * 
 */
package simplesearch;

import egat.game.*;

import java.util.*;

/**
 * @author Viknesh Krishnan
 *
 *	An iterator to return tuples of support sizes, ordered properly.
 * To access the iterator, do
 * PorterSupportStrategyTraversal(game).iterator()
 *
 */
public class PorterSupportStrategyTraversal implements Iterable<int[]> {
	/* will later probably want to implement this similar to the deviation
	 * traversal in "DeviationFactory.java"
	 */
	private StrategicGame theGame;
	
	public PorterSupportStrategyTraversal(StrategicGame game){
		theGame = game;
	}
	
	public Iterator<int[]> iterator() {
		return new PorterSupportStrategyIterator(theGame);
		//return instance of StratGame iterator
	}
	
	
	static class PorterSupportStrategyIterator implements Iterator<int[]> {
		int numPlayers;
		Player[] playerArray;
		int[] numStrategies;
		int maxSupportSize;

		
		PriorityQueue<PorterTupleWrapper> tupleCollection;
		int currSupportSize;
		
		
		PorterSupportStrategyIterator(StrategicGame game){
			numPlayers = game.players().size();
			
			playerArray = new Player[game.players().size()];
				
			game.players().toArray(playerArray);
			
			numStrategies = new int[numPlayers];
			maxSupportSize = 0;
			
			for (int i = 0; i < numPlayers; i++){
				numStrategies[i] = game.getActions(playerArray[i]).size();
				maxSupportSize += numStrategies[i];
			}
			
			currSupportSize = numPlayers-1;
			
			tupleCollection = 
				new PriorityQueue<PorterTupleWrapper>();
		}
		
		public boolean hasNext() {
			return (currSupportSize < maxSupportSize);
		}
		
		public int[] next() { //should probably throw an error if currSupportSize gets too large
			//although, in theory, tupleCollection.remove() will
			if(tupleCollection.isEmpty()){
				currSupportSize++; 
				partitioner();
			}
			
			return tupleCollection.remove().GetTuple();
		}
		
		public void remove() {
			throw new UnsupportedOperationException("remove is not supported by this iterator.");
		}

		private void partitioner(){
			
			int[] initial = new int[numPlayers];
			for (int i=0; i<numPlayers; i++)
				initial[i] = 0;
				
			partition_helper(initial, 0, currSupportSize);
		}
		
		private int min(int a, int b){
			if (a<b)
				return a;
			return b;
		}
		
		private void printArray(int[] array){
			for (int a : array){
				System.out.print(a);
				System.out.print(" ");
			}
			
			System.out.println();
			
		}
		
		private void printCollection(java.util.Collection<PorterTupleWrapper> col){
			for (PorterTupleWrapper a : col){
				printArray(a.GetTuple());
			}
		}
		
		private void partition_helper(int[] prevTuple, int index, int currSizeUnused){
			
			if (index==numPlayers){
					if (currSizeUnused == 0){
						PorterTupleWrapper toAdd = new PorterTupleWrapper(prevTuple, numPlayers, currSupportSize);
						tupleCollection.add(toAdd);	
					}
					else
						return;
			}
			else{
				
				int playerMin;
				playerMin = min(currSizeUnused-(numPlayers-(index+1)), numStrategies[index]);
	
				for (int i=1; i<=playerMin; i++){
					int[] nextTuple = prevTuple.clone();
					nextTuple[index] = i;
					partition_helper(nextTuple, index+1, currSizeUnused-i);
				}
			}
		}
		
	} //end PSSI class
}
