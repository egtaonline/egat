package amplwriter;

import java.util.*;

public class test {

	/**
	 * @param args
	 */		
	public static String toString(String name, Collection<Integer> dims){
		Integer[] dimsArray = new Integer[dims.size()];
		dims.toArray(dimsArray);
		String s;
		s = "var "+ name + "{";
		for(int i=0; i<dims.size()-1; i++){
			s+="1.."+Integer.toString(dimsArray[i])+",";
		}
		s+="1.."+Integer.toString(dimsArray[dims.size()-1])+"}";
		return s;
	}
	
	public static void main(String[] args) {
		Integer[] d = {2,3};
		System.out.println(toString("hi", Arrays.asList(d)));
	}

}
