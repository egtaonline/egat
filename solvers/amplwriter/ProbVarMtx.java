package amplwriter;

import java.util.*;

public class ProbVarMtx implements Var{
	String name;
	Collection<Integer> dims;
	String constraints;
	
	public ProbVarMtx(String name, Collection<Integer> dims){
		this.name = name;
		this.dims = dims;
		constraints = " >= 0, <= 1";
	}
	
	public String toString(){
		Integer[] dimsArray = new Integer[dims.size()];
		dims.toArray(dimsArray);
		
		String s;
		s = "var "+ name + "{";
		for(int i=0; i<dims.size()-1; i++)
			s+="1.."+Integer.toString(dimsArray[i])+",";
		s+="1.."+Integer.toString(dimsArray[dims.size()-1])+"}";
		s+= constraints + ";";
		return s;
	}
	
	public static void main(String[] args) {
		Integer[] pe = {2,3};
		ProbVarMtx a = new ProbVarMtx("me", Arrays.asList(pe));
		System.out.println(a.toString());
	}

}
