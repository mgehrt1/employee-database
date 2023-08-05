import java.util.ArrayList;
import java.util.List;

public class RedBlackTreeMultiBD<T extends Comparable<T>> extends RedBlackTree<T>
        implements RedBlackTreeMultiInterface<T> {
    List<T> list = new ArrayList<T>();

    @Override
    public void putMultiple(List<T> insertionList) {
        for (int i = 0; i < insertionList.size(); i++) {
            list.add(insertionList.get(i));
        }
    }

    @Override
    public void removeMultiple(List<T> removalList) {
        list.remove(3);
    }

    @Override
    public List<T> inOrderTraversal() {
        return null;
    }

    @Override
    public int getNumberOfNodes() {
        return 3;
    }

    @Override
    public T get(int identifier) {
        return null;
    }

    @Override
    public T get(T identifier) {
        // Hardcoded for testing removeEmployees
        if (list.size() >= 1 && identifier.compareTo(list.get(0)) == 0) {
            return list.get(0);
        }
        if (list.size() >= 2 && identifier.compareTo(list.get(1)) == 0) {
            return list.get(1);
        }
        if (list.size() >= 3 && identifier.compareTo(list.get(2)) == 0) {
            return list.get(2);
        }
        if (list.size() >= 4 && identifier.compareTo(list.get(3)) == 0) {
            return list.get(3);
        }
        if (list.size() >= 5 && identifier.compareTo(list.get(4)) == 0) {
            return list.get(4);
        }

        return null;

    }

}
