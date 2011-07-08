package egat.game.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CrossProductIterator<T> implements Iterator<List<T>> {
	private Iterator<T> iterator;
    private T point;
    private CrossProductIterator<T> subIterator;
    private List<Set<T>> list;
    private List<T> next;

    public CrossProductIterator(List<Set<T>> list) {
        this.list = list;

        if (list.size() > 0) {
            iterator = list.get(0).iterator();
            update();
        }
    }

    private void update() {

        if (point != null && subIterator != null && subIterator.hasNext()) {
           next = subIterator.next();
           next.add(0, point);
        } else if (iterator.hasNext()) {
            point = iterator.next();

            if (list.size() > 1) {
                subIterator = new CrossProductIterator<T>(list.subList(1, list.size()));
                update();
            } else {
                next = new LinkedList<T>();
                next.add(point);
            }
        } else {
            next = null;
        }
    }  

    public boolean hasNext() {
        return next != null;
    }

    public List<T> next() {
        List<T> cur = next;
        update();
        return cur;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() not supported in this iterator.");
    }
    
    public static void main(String args[]){
    	List<Set<String>> list = new ArrayList<Set<String>>();
    	Set<String> a = new HashSet<String>();
    	a.add("A");
    	a.add("B");
    	Set<String> b = new HashSet<String>();
    	b.add("A");
    	b.add("B");
    	list.add(a);
    	list.add(b);
    	for(CrossProductIterator cpi = new CrossProductIterator(list); cpi.hasNext();){
    		System.out.println(cpi.next().toString());
    	}
    }
}
