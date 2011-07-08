package egat.game.utils;

import java.util.*;

public class AllCombinationsGenerator {
	public static <T> List<List<T>> generate(List<T> initialSet, int maxSize){
		List<List<T>> returnList = new ArrayList<List<T>>();
		for(int i = 1; i <= maxSize; i++){
			for(CombinationGenerator<T> cg = new CombinationGenerator<T>(initialSet, i); cg.hasNext();)
				returnList.add(cg.next());
		}
		return returnList;
	}
	
	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		for(List<String> listing : AllCombinationsGenerator.generate(list, 3))
			System.out.println(listing.toString());
	}
	
}
