package simplesearch;

public class PorterTupleWrapper implements java.lang.Comparable<PorterTupleWrapper> {
	private int[] tuple;
	private int numPlayers;
	private int supportSize;
	private int imbalanceFactor;
	
	public PorterTupleWrapper(int[] tuple, int numPlayers, int supportSize){
		
		this.tuple = tuple;
		this.numPlayers = numPlayers;
		this.supportSize = supportSize;
		imbalanceFactor = CalculateImbalance();
	}
	
	public int[] GetTuple(){
		return tuple;
	}
	
	public int GetSize(){
		return supportSize;
	}
	
	private int CalculateImbalance(){
		
		int min = tuple[0];
		int max = tuple[0];
		
		for (int i=1; i<numPlayers; i++){
			if (tuple[i] < min)
				min = tuple[i];
			if (tuple[i] > max)
				max = tuple[i];
		}
		
		return max - min;
	}

	public int compareTo(PorterTupleWrapper o) {
		
		return (this.imbalanceFactor - o.imbalanceFactor); 
	}
	
/*	public PorterTupleWrapperComparator getComparator(){
		return new PorterTupleWrapperComparator();
	}
	
	public class PorterTupleWrapperComparator implements java.util.Comparator {
		public PorterTupleWrapperComparator(){
		}
		
		public int compare(PorterTupleWrapper o1, PorterTupleWrapper o2) {
			return (o1.imbalanceFactor - o2.imbalanceFactor); //again, negative or not?
		}
		
		public int compare(Object o1, Object o2){
			throw new UnsupportedOperationException("Can only be used to compare PorterTupleWrappers");
		}
	}*/
	
}
