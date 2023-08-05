
// --== CS400 Spring 2023 File Header Information ==--
// Name: Sampreeth
// Email: immidisetty@wisc.edu 
// Team: AI
// TA: Rachit Tibdewal
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.Stack;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;

/**
 * Red-Black Tree implementation with a Node inner class for representing the nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red black tree by
 * modifying the insert functionality. In this activity, we will start with implementing rotations
 * for the binary search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

  /**
   * This class represents a node holding a single value within a binary tree.
   */
  protected static class Node<T> {
    public T data;
    // The context array stores the context of the node in the tree:
    // - context[0] is the parent reference of the node,
    // - context[1] is the left child reference of the node,
    // - context[2] is the right child reference of the node.
    // The @SupressWarning("unchecked") annotation is used to supress an unchecked
    // cast warning. Java only allows us to instantiate arrays without generic
    // type parameters, so we use this cast here to avoid future casts of the
    // node type's data field.
    @SuppressWarnings("unchecked")
    public Node<T>[] context = (Node<T>[]) new Node[3];

    // new additions
    public int blackHeight = 0;

    public Node(T data) {
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the right child of that parent, otherwise
     *         return false
     */
    public boolean isRightChild() {
      return context[0] != null && context[0].context[2] == this;
    }


    // helper method

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    private boolean isLeftChild() {
      return context[0] != null && context[0].context[1] == this;
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if not
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when data is already contained in the tree
   */
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");

    Node<T> newNode = new Node<>(data);
    if (this.root == null) {
      // add first node to an empty tree
      root = newNode;
      size++;
      enforceRBTreePropertiesAfterInsert(newNode);
      return true;
    } else {
      // insert into subtree
      Node<T> current = this.root;
      while (true) {
        int compare = newNode.data.compareTo(current.data);
        if (compare == 0) {
          throw new IllegalArgumentException(
              "This RedBlackTree already contains value " + data.toString());
        } else if (compare < 0) {
          // insert in left subtree
          if (current.context[1] == null) {
            // empty space to insert into
            current.context[1] = newNode;
            newNode.context[0] = current;
            this.size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.context[1];
          }
        } else {
          // insert in right subtree
          if (current.context[2] == null) {
            // empty space to insert into
            current.context[2] = newNode;
            newNode.context[0] = current;
            this.size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.context[2];
          }
        }
      }
    }
  }

  /**
   * Enforces RBT Properties after insert algorithm
   * @param node - node to be checked
   */
  protected void enforceRBTreePropertiesAfterInsert(Node<T> node) {

    // do nothing if insertion is not illegal (changing root to black)
    if (node.equals(root) || node.context[0].blackHeight == 1) {
      root.blackHeight = 1;
      return;
    }

    Node<T> grandparent = node.context[0].context[0];

    // changing root to black if grandparent does not exist
    if (grandparent == null) {
      root.blackHeight = 1;
      return;
    }


    // Illegal insertion cases (red - red node property violation):
    // case 1 - inserted child has black uncle of same direction

    // inserted node is right child
    if (node.isRightChild()) {
      if (node.context[0].blackHeight == 0 && node.context[0].isLeftChild() // checking if parent is
                                                                            // left child
          && (grandparent.context[2] == null || grandparent.context[2].blackHeight == 1)) {

        // extra rotate to get to case 2
        rotate(node, node.context[0]);
        enforceRBTreePropertiesAfterInsert(node.context[1]);

        root.blackHeight = 1;
        return;
      }
    }

    // inserted node is left child
    if (node.isLeftChild()) {
      if (node.context[0].blackHeight == 0 && node.context[0].isRightChild() // checking if parent
                                                                             // is right child
          && (grandparent.context[1] == null || grandparent.context[1].blackHeight == 1)) {

        // extra rotate to get case 2
        rotate(node, node.context[0]);
        enforceRBTreePropertiesAfterInsert(node.context[2]);

        root.blackHeight = 1;
        return;
      }
    }


    // case 2 - inserted child has black uncle of opposite direction

    // inserted node is left child
    if (node.isLeftChild()) {

      if (node.context[0].blackHeight == 0
          && (grandparent.context[2] == null || grandparent.context[2].blackHeight == 1)) {

        // rotate and color swap
        rotate(node.context[0], node.context[0].context[0]);
        node.context[0].blackHeight = 1;
        node.context[0].context[2].blackHeight = 0;

        root.blackHeight = 1;
        return;
      }
    }

    // inserted node is right child
    if (node.isRightChild()) {

      if (node.context[0].blackHeight == 0
          && (grandparent.context[1] == null || grandparent.context[1].blackHeight == 1)) {

        // rotate and color swap
        rotate(node.context[0], node.context[0].context[0]);
        node.context[0].blackHeight = 1;
        node.context[0].context[1].blackHeight = 0;

        root.blackHeight = 1;
        return;
      }
    }



    // case 3 - inserted child has red uncle

    // inserted node is left child

    if (grandparent.context[1].blackHeight == 0
        && (grandparent.context[2] != null && grandparent.context[2].blackHeight == 0)
        && node.context[0].blackHeight == 0) {
      grandparent.context[1].blackHeight = 1;
      grandparent.context[2].blackHeight = 1;
      grandparent.blackHeight = 0;

      enforceRBTreePropertiesAfterInsert(grandparent);

      root.blackHeight = 1;
      return;
    }
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

    // child.context[2] = child.context[0];
    // child.context[0] = null;
    // }
    //
    // // left rotation
    // if (parent.context[2].equals(child)) {
    // child.context[1] = child.context[0];
    // child.context[0] = null;
    // }

    // if (parent.context[1].equals(child)) {
    // Node<T> lChild = child.context[1];
    //
    // child.context[1] = lChild.context[2];
    // if (lChild.context[2] != null) {
    // lChild.context[2].context[0] = child;
    // }
    //
    // lChild.context[2] = child;
    // child.context[0] = lChild;
    //
    // if (parent == null) {
    // root = lChild;
    // } else if (parent.context[1] == child) {
    // parent.context[1] = lChild;
    // } else if (parent.context[2] == child) {
    // parent.context[2] = lChild;
    // } else {
    // throw new IllegalStateException("Node is not a child of its parent");
    // }
    //
    // if (lChild != null) {
    // lChild.context[0] = parent;
    // }
    // }



  }

  /**
   * Get the size of the tree (its number of nodes).
   * 
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * 
   * @return true of this.size() return 0, false if this.size() > 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
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
          this.replaceNode(successorNode, null);
        } else {
          // successor has a right child, replace successor with its child
          this.replaceNode(successorNode, successorNode.context[2]);
        }
      } else if (hasRightChild) {
        // only right child, replace with right child
        this.replaceNode(nodeWithData, nodeWithData.context[2]);
      } else if (hasLeftChild) {
        // only left child, replace with left child
        this.replaceNode(nodeWithData, nodeWithData.context[1]);
      } else {
        // no children, replace node with a null node
        this.replaceNode(nodeWithData, null);
      }
      this.size--;
      return true;
    }
  }

  /**
   * Checks whether the tree contains the value *data*.
   * 
   * @param data the data value to test for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(T data) {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.findNodeWithData(data);
      // return false if the node is null, true otherwise
      return (nodeWithData != null);
    }
  }

  /**
   * Helper method that will replace a node with a replacement node. The replacement node may be
   * null to remove the node from the tree.
   * 
   * @param nodeToReplace   the node to replace
   * @param replacementNode the replacement for the node (may be null)
   */
  protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
    if (nodeToReplace == null) {
      throw new NullPointerException("Cannot replace null node.");
    }
    if (nodeToReplace.context[0] == null) {
      // we are replacing the root
      if (replacementNode != null)
        replacementNode.context[0] = null;
      this.root = replacementNode;
    } else {
      // set the parent of the replacement node
      if (replacementNode != null)
        replacementNode.context[0] = nodeToReplace.context[0];
      // do we have to attach a new left or right child to our parent?
      if (nodeToReplace.isRightChild()) {
        nodeToReplace.context[0].context[2] = replacementNode;
      } else {
        nodeToReplace.context[0].context[1] = replacementNode;
      }
    }
  }

  /**
   * Helper method that will return the inorder successor of a node with two children.
   * 
   * @param node the node to find the successor for
   * @return the node that is the inorder successor of node
   */
  protected Node<T> findMinOfRightSubtree(Node<T> node) {
    if (node.context[1] == null && node.context[2] == null) {
      throw new IllegalArgumentException("Node must have two children");
    }
    // take a steop to the right
    Node<T> current = node.context[2];
    while (true) {
      // then go left as often as possible to find the successor
      if (current.context[1] == null) {
        // we found the successor
        return current;
      } else {
        current = current.context[1];
      }
    }
  }

  /**
   * Helper method that will return the node in the tree that contains a specific value. Returns
   * null if there is no node that contains the value.
   * 
   * @return the node that contains the data, or null of no such node exists
   */
  protected Node<T> findNodeWithData(T data) {
    Node<T> current = this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found our value
        return current;
      } else if (compare < 0) {
        // keep looking in the left subtree
        current = current.context[1];
      } else {
        // keep looking in the right subtree
        current = current.context[2];
      }
    }
    // we're at a null node and did not find data, so it's not in the tree
    return null;
  }

  /**
   * This method performs an inorder traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * 
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          sb.append(popped.data.toString());
          if (!nodeStack.isEmpty() || popped.context[2] != null)
            sb.append(", ");
          current = popped.context[2];
        } else {
          nodeStack.add(current);
          current = current.context[1];
        }
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection). This method will be helpful as a
   * helper for the debugging and testing of your rotation implementation.
   * 
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.context[1] != null)
          q.add(next.context[1]);
        if (next.context[2] != null)
          q.add(next.context[2]);
        sb.append(next.data.toString());
        if (!q.isEmpty())
          sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
  }


  /**
   * Test for the correct node colours of adding root and its children
   */
/*  @Test
  void test1() {
      
      RedBlackTree<Integer> testTree = new RedBlackTree<Integer>();

      // Root node must be black when inserted 
      testTree.insert(12);
      assertEquals(1, testTree.root.blackHeight,     
              "New root node must be black"); 

      // Node must be red when inserted
      testTree.insert(20);
      assertEquals(0, testTree.root.context[2].blackHeight,     
              "Added node must be red"); 

      // Node must be red when inserted
      testTree.insert(6);
      assertEquals(0, testTree.root.context[1].blackHeight,     
              "Added node must be red"); 

  }
*/
  /**
   * Test tree with invalid insertion of node with black uncle (case 1 & 2)
   */
/*  @Test
>>>>>>> 2f6de576d2e36b3f87488a1836861177e93738aa
  void test2() {

      RedBlackTree<Integer> testTree = new RedBlackTree<Integer>();
      
      // creating a tree where an invalid node is added with a red parent and red uncle
      testTree.insert(5);
      testTree.insert(3);
      testTree.insert(7);
      testTree.insert(9);
      testTree.insert(2);
      testTree.insert(4);
      testTree.insert(1);
      testTree.insert(0);

  }
*/
  /**
   * Test tree with invalid insertion of node with red uncle (case 3)
   */
/*  @Test
>>>>>>> 2f6de576d2e36b3f87488a1836861177e93738aa
  void test3() {

	RedBlackTree<Integer> testTree1 = new RedBlackTree<Integer>();
        
	// creating tree where invalid node is inserted with red parent and black uncle (node and uncle are in same direction) 
        testTree1.insert(18);
        testTree1.insert(14);
        testTree1.insert(22);
        testTree1.insert(26);
        testTree1.insert(12);
        testTree1.insert(16);
        testTree1.insert(10);
        testTree1.insert(7);
        
        // node with data 10 must be black after rotation 
        assertEquals(1, testTree1.root.context[1].context[1].blackHeight,     
                "node should have a blackHeight of 1"); 
        
        assertEquals(0, testTree1.root.context[1].context[1].context[1].blackHeight,     
                "node should have a blackHeight of 0"); 
        
        assertEquals("[ 18, 14, 22, 10, 16, 26, 7, 12 ]", testTree1.toLevelOrderString(),     
                "Level order should match");
        
                
        RedBlackTree<Integer> testTree2 = new RedBlackTree<Integer>();
        
        // creating tree where invalid node is inserted with red parent and black uncle (node and uncle are in different direction) 
        testTree2.insert(47);
        testTree2.insert(12);
        testTree2.insert(62);
        testTree2.insert(3);
        testTree2.insert(27);
        testTree2.insert(34);
        testTree2.insert(30);
        
        
        assertEquals(1, testTree2.root.context[1].context[2].blackHeight,     
                "node should have a blackHeight of 1"); 
        
        assertEquals(0, testTree2.root.context[1].context[2].context[1].blackHeight,     
                "node should have a blackHeight of 0"); 
        
        assertEquals(0, testTree2.root.context[1].context[2].context[2].blackHeight,     
                "node should have a blackHeight of 0"); 
        
        assertEquals("[ 47, 12, 62, 3, 30, 27, 34 ]", testTree2.toLevelOrderString(),     
                "Level order should match"); 

  }
*/
}
