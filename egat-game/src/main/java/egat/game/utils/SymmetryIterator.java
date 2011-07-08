package egat.game.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SymmetryIterator<T> implements Iterator<List<T>> {
    private Iterator<T> iterator;
    private T point;
    private int count;
    private int countMax;
    private SymmetryIterator<T> subIterator;
    private Set<T> set;
    private List<T> next;

    public SymmetryIterator(final Set<T> set, final int countMax) {
        //Defensive copy
        this.set = new HashSet<T>(set);

        this.countMax = countMax;
        iterator = set.iterator();
        if (countMax > 0) {
            count = 0;
            update();
        }
    }

    private void update() {

        if (point != null && subIterator != null && subIterator.hasNext()) {
            next = subIterator.next();

            for (int i = 0; i < count; i++) {
                next.add(0, point);
            }

        } else if (point != null && count < countMax) {
            count++;
            if (count == countMax) {
                subIterator = null;
                next = new LinkedList<T>();
                for (int i = 0; i < count; i++) {
                    next.add(0, point);
                }
            } else {
                subIterator = new SymmetryIterator<T>(set, countMax - count);
                update();
            }
        } else if (iterator.hasNext()) {
            count = 0;
            point = iterator.next();
            set.remove(point);
            update();
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
}
