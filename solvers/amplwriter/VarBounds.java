package amplwriter;

public class VarBounds {
	double min;
	double max;
	
	public VarBounds(double min, double max){
		this.min = min;
		this.max = max;
	}
	
	public VarBounds(){
		this.min = Double.NEGATIVE_INFINITY;
		this.max = Double.POSITIVE_INFINITY;
	}
	
	public double getMin(){
		return this.min;
	}
	
	public double getMax(){
		return this.max;
	}
}
