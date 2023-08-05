// --== CS400 Spring 2023 File Header Information ==--
// Name: Sampreeth
// Email: immidisetty@wisc.edu
// Team: AI
// TA: Rachit Tibdewal
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Class to extend the functionality of RedBlackTree
 * @author sampreethimmidisetty - Algorithm Engineer
 *
 * @param <T>
 */
public class RedBlackTreeMultiAE<T extends Comparable<T>> extends RedBlackTree<T>
    implements RedBlackTreeMultiInterface<T> {

  /**
   * Inserts multiple objects into RBT from given list of objects
   * @param insertionList - list of objects to insert
   */
  @Override
  public void putMultiple(List<T> insertionList) {

    for (T node : insertionList) {
      insert(node);
    }

  }

  /**
   * Removes multiple objects from RBT from given list of objects
   * @param removalList - list of objects to remove
   */
  @Override
  public void removeMultiple(List<T> removalList) {

    for (T node : removalList) {
      remove(node);
    }

  }

  /**
   * Traverses through the tree in-order, and returns its objects
   * @return objects of the tree as an ArrayList in-order
   */
  @Override
  public List<T> inOrderTraversal() {

    ArrayList<T> traversal = new ArrayList<T>();

    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          traversal.add(popped.data);
          current = popped.context[2];
        } else {
          nodeStack.add(current);
          current = current.context[1];
        }
      }
    }

    return traversal;
  }

  /**
   * Gets the number of nodes in the tree
   * @return number of nodes in tree
   */
  @Override
  public int getNumberOfNodes() {

    return inOrderTraversal().size();
  }

  /**
   * Gets an object in the tree given an object
   * @param identifier - object to compare to in tree
   * @return identical object in the tree
   */
  @Override
  public T get(T identifier) {
    if(findNodeWithData(identifier) == null){
      return null;
    }
    return findNodeWithData(identifier).data;
  }

  @Override
  public T get(int employeeID) {
    return null;
  }
  /**
   * Removes the value data from the tree if the tree contains the value. This method will not
   * attempt to rebalance the tree after the removal and should be updated once the tree uses
   * Red-Black Tree insertion.
   * 
   * @return true if the value was remove, false if it didn't exist
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when data is not stored in the tree
   */
  @Override
  public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.findNodeWithData(data);
      // throw exception if node with data does not exist
      if (nodeWithData == null) {
        throw new IllegalArgumentException(
            "The following value is not in the tree and cannot be deleted: " + data.toString());
      }
      boolean hasRightChild = (nodeWithData.context[2] != null);
      boolean hasLeftChild = (nodeWithData.context[1] != null);
      if (hasRightChild && hasLeftChild) {
        // has 2 children
        Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
        // replace value of node with value of successor node
        nodeWithData.data = successorNode.data;
        // remove successor node
        if (successorNode.context[2] == null) {
          // successor has no children, replace with null

          if (successorNode.blackHeight == 1) {
            successorNode.blackHeight = 2;
            enforceRBTreePropertiesAfterRemovalDoubleBlack(successorNode);

          }
          this.replaceNode(successorNode, null);
        } else {
          // successor has a right child, replace successor with its child
          enforceRBTreePropertiesAfterRemovalOneChild(successorNode.context[2]);
          this.replaceNode(successorNode, successorNode.context[2]);
        }
      } else if (hasRightChild) {
        // only right child, replace with right child
        enforceRBTreePropertiesAfterRemovalOneChild(nodeWithData.context[2]);
        this.replaceNode(nodeWithData, nodeWithData.context[2]);
      } else if (hasLeftChild) {
        // only left child, replace with left child
        enforceRBTreePropertiesAfterRemovalOneChild(nodeWithData.context[1]);
        this.replaceNode(nodeWithData, nodeWithData.context[1]);
      } else {
        // no children, replace node with a null node

        if (nodeWithData.blackHeight == 1) {
          nodeWithData.blackHeight = 2;
          enforceRBTreePropertiesAfterRemovalDoubleBlack(nodeWithData);

        }
        this.replaceNode(nodeWithData, null);
      }
      this.size--;
      return true;
    }
  }


  /**
   * Enforces RBT properties when a double black is created
   * @param node - the double black node before it is removed
   */
  protected void enforceRBTreePropertiesAfterRemovalDoubleBlack(Node<T> node) {

    // root node

    if (node.context[0] == null) {
      node.blackHeight = 1;
      return;
    }

    // case 1 - black sibling with red child in opposite direction

    try {

      if (!node.isRightChild()) {
        if (node.context[0].context[2].blackHeight == 1
            && node.context[0].context[2].context[2].blackHeight == 0) {
          rotate(node.context[0].context[2], node.context[0]);
          node.blackHeight = 1;
          node.context[0].context[0].blackHeight = node.context[0].blackHeight;
          node.context[0].blackHeight = 1;
          node.context[0].context[0].context[2].blackHeight = 1;
          return;
        }
      }
      if (node.isRightChild()) {
        if (node.context[0].context[1].blackHeight == 1
            && node.context[0].context[1].context[1].blackHeight == 0) {
          rotate(node.context[0].context[1], node.context[0]);
          node.blackHeight = 1;
          node.context[0].context[0].blackHeight = node.context[0].blackHeight;
          node.context[0].blackHeight = 1;
          node.context[0].context[0].context[1].blackHeight = 1;
          return;
        }
      }

    } catch (NullPointerException e) {

    }

    // case 1.5 - black sibling with red child in same direction

    try {

      if (!node.isRightChild()) {
        if (node.context[0].context[2].blackHeight == 1
            && node.context[0].context[2].context[1].blackHeight == 0) {
          rotate(node.context[0].context[2].context[1], node.context[0].context[2]);
          node.context[0].context[2].context[2].blackHeight = 0;
          node.context[0].context[2].blackHeight = 1;
          return;
        }
      }
      if (node.isRightChild()) {
        if (node.context[0].context[1].blackHeight == 1
            && node.context[0].context[1].context[2].blackHeight == 0) {
          rotate(node.context[0].context[1].context[2], node.context[0].context[1]);
          node.context[0].context[1].context[1].blackHeight = 0;
          node.context[0].context[1].blackHeight = 1;
          return;
        }
      }

    } catch (NullPointerException e) {

    }
    // case 2 - black sibling and black nephews

    try {

      if (node.isRightChild()) {
        if (node.context[0].context[1].blackHeight == 1
            && (node.context[0].context[1].context[1] == null
                || node.context[0].context[1].context[1].blackHeight == 1)
            && (node.context[0].context[1].context[2] == null
                || node.context[0].context[1].context[2].blackHeight == 1)) {
          node.blackHeight = 1;
          node.context[0].context[1].blackHeight = 0;
          node.context[0].blackHeight++;

          if (node.context[0].blackHeight == 2) {
            enforceRBTreePropertiesAfterRemovalDoubleBlack(node.context[0]);
          }

          return;
        }
      }
      if (!node.isRightChild()) {
        if (node.context[0].context[2].blackHeight == 1
            && (node.context[0].context[2].context[1] == null
                || node.context[0].context[2].context[1].blackHeight == 1)
            && (node.context[0].context[2].context[2] == null
                || node.context[0].context[2].context[2].blackHeight == 1)) {
          node.blackHeight = 1;
          node.context[0].context[1].blackHeight = 0;
          node.context[0].blackHeight++;

          if (node.context[0].blackHeight == 2) {
            enforceRBTreePropertiesAfterRemovalDoubleBlack(node.context[0]);
          }

          return;
        }
      }

    } catch (NullPointerException e) {

    }

    // case 3 - red sibling

    try {

      if (node.isRightChild()) {
        if (node.context[0].context[1].blackHeight == 0) {
          rotate(node.context[0].context[1], node.context[0]);
          node.context[0].blackHeight = 0;
          node.context[0].context[1].blackHeight = 1;
          enforceRBTreePropertiesAfterRemovalDoubleBlack(node);
        }
      }

      if (!node.isRightChild()) {
        if (node.context[0].context[2].blackHeight == 0) {
          rotate(node.context[0].context[2], node.context[0]);
          node.context[0].blackHeight = 0;
          node.context[0].context[2].blackHeight = 1;
          enforceRBTreePropertiesAfterRemovalDoubleBlack(node);
        }
      }

    } catch (NullPointerException e) {

    }

  }

  /**
   * Enforcing RBT properties on a node with one child before removal
   * @param movingNode - Node to be moved up the tree
   */
  protected void enforceRBTreePropertiesAfterRemovalOneChild(Node<T> movingNode) {
    // case 1: child is red - make child black
    if (movingNode.blackHeight == 0) {
      movingNode.blackHeight = 1;
      return;
    }
    // case 2: child is black - check color of parent
    if (movingNode.context[0].blackHeight == 0) {
      return;
    } else {
      movingNode.blackHeight = 2;
      enforceRBTreePropertiesAfterRemovalDoubleBlack(movingNode);
    }
    return;
  }


  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will throw
   * an IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // TODO: Implement this method.
    if (parent.context[1] != child && parent.context[2] != child) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    // creating grandparent node
    Node<T> gparent = parent.context[0];

    // linking initial child node to grandparent node
    child.context[0] = gparent;

    // if grandparent node exists, linking it to the child node instead of parent node
    if (gparent != null) {
      if (gparent.context[2] == parent) {
        gparent.context[2] = child;
      } else {
        gparent.context[1] = child;
      }
    }


    // right rotation
    if (parent != null && parent.context[1] == child) {

      // linking all the values greater than the initial child and lesser than initial parent to the
      // left side of the initial parent
      if (child.context[2] != null) {
        child.context[2].context[0] = parent;
      }
      parent.context[1] = child.context[2];

      // linking initial parent and initial child as a child and parent respectively
      child.context[2] = parent;
      parent.context[0] = child;

      if (gparent == null && this.root != child) {
        this.root = child;
      }
    }

    // left rotation
    if (parent != null && parent.context[2] == child) {

      // linking all the values lesser than the initial child and greater than initial parent to the
      // right side of the initial parent
      if (child.context[1] != null) {
        child.context[1].context[0] = parent;
      }
      parent.context[2] = child.context[1];

      // linking initial parent and initial child as a child and parent respectively
      child.context[1] = parent;
      parent.context[0] = child;

      if (gparent == null && this.root != child) {
        this.root = child;
      }
    }

  }
}

