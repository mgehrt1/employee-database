import java.util.List;

public interface RedBlackTreeMultiInterface<T extends Comparable<T>> extends SortedCollectionInterface<T> {
  
  public void putMultiple(List<T> insertionList);

  public void removeMultiple(List<T> removalList);

  public List<T> inOrderTraversal();

  public int getNumberOfNodes();

  public T get(T identifier);

  public T get(int employeeID);
}
