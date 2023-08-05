// --== CS400 Spring 2023 File Header Information ==--
// Name: Sampreeth
// Email: immidisetty@wisc.edu
// Team: AI
// TA: Rachit Tibdewal
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

/**
 * Class to test RedBlackTreeMultiAE implementation
 * @author sampreethimmidisetty - Algorithm Engineer
 *
 */
public class AlgorithmEngineerTests {

  /**
   * Tests RBT after remove method on leaf nodes (red and black)
   */
  @Test 
  void test1() {

    // removing red leaf node

    RedBlackTreeMultiAE<Integer> testRBT1 = new RedBlackTreeMultiAE<Integer>();
    
    // inserting elements into tree

    testRBT1.insert(5);
    testRBT1.insert(9);
    testRBT1.insert(2);
    testRBT1.insert(14);
    testRBT1.insert(4);
    testRBT1.insert(1);

    testRBT1.remove(4);

    // testing colour changes and level order traversal of tree
    assertEquals(1, testRBT1.root.blackHeight,
     "node must be black");

     assertEquals(0, testRBT1.root.context[2].context[2].blackHeight,
     "node must be red");
    
     assertEquals(1, testRBT1.root.context[2].blackHeight,
     "node must be black");
    
     assertEquals(1, testRBT1.root.context[1].blackHeight,
     "node must be black");

     assertEquals("[ 5, 2, 9, 1, 14 ]", testRBT1.toLevelOrderString());



    RedBlackTreeMultiAE<Integer> testRBT2 = new RedBlackTreeMultiAE<Integer>();
    
    // inserting elements into tree

    testRBT2.insert(5);
    testRBT2.insert(12);
    testRBT2.insert(3);
    testRBT2.insert(17);
    testRBT2.insert(9);
    testRBT2.insert(19);
    
    // removing black leaf node
    testRBT2.remove(9);

    // testing colour changes and level order traversal of tree
     assertEquals(1, testRBT2.root.context[2].context[2].blackHeight,
     "node must be black");
    
     assertEquals(1, testRBT2.root.context[2].context[1].blackHeight,
     "node must be black");
    
     assertEquals(0, testRBT2.root.context[2].blackHeight,
     "node must be black");

     assertEquals("[ 5, 3, 17, 12, 19 ]", testRBT2.toLevelOrderString());

  }

  /**
   * Tests RBT after remove method on node with one child
   */
  @Test
  void test2() {

    // removing red leaf node

    RedBlackTreeMultiAE<Integer> testRBT = new RedBlackTreeMultiAE<Integer>();
    
    // inserting elements into tree
    testRBT.insert(5);
    testRBT.insert(12);
    testRBT.insert(3);
    testRBT.insert(17);
    testRBT.insert(9);
    testRBT.insert(19);

    testRBT.remove(17);

    // testing colour changes and level order traversal of tree

     assertEquals(1, testRBT.root.context[2].context[2].blackHeight,
     "node must be black");
    
     assertEquals(1, testRBT.root.context[2].context[1].blackHeight,
     "node must be black");

     assertEquals("[ 5, 3, 12, 9, 19 ]", testRBT.toLevelOrderString());

  }

  /**
   * Tests RBT after remove method on node with two children
   */
  @Test
  void test3() {

    RedBlackTreeMultiAE<Integer> testRBT = new RedBlackTreeMultiAE<Integer>();

    // inserting elements into tree
    testRBT.insert(5);
    testRBT.insert(12);
    testRBT.insert(3);
    testRBT.insert(17);
    testRBT.insert(9);
    testRBT.insert(19);

    testRBT.remove(12);

    // testing colour changes and level order traversal of tree
     assertEquals(1, testRBT.root.context[2].context[2].blackHeight,
     "node must be black");
    
     assertEquals(1, testRBT.root.context[2].context[1].blackHeight,
     "node must be black");
     assertEquals(0, testRBT.root.context[2].blackHeight,
     "node must be red");

     assertEquals("[ 5, 3, 17, 9, 19 ]", testRBT.toLevelOrderString());

  }

  /**
   * Testing put and remove multiple
   */
  @Test
  void test4() {

    RedBlackTreeMultiAE<Integer> testRBT = new RedBlackTreeMultiAE<Integer>();

    ArrayList<Integer> inserts = new ArrayList<Integer>();
    inserts.add(5);
    inserts.add(12);
    inserts.add(3);
    inserts.add(17);
    inserts.add(9);
    inserts.add(19);

    testRBT.putMultiple(inserts);

    // testing colour changes and level order traversal of tree after putMultiple
     assertEquals("[ 5, 3, 12, 9, 17, 19 ]", testRBT.toLevelOrderString());
     assertEquals(1, testRBT.root.blackHeight,
     "node must be black");
     assertEquals(0, testRBT.root.context[2].blackHeight,
     "node must be red");
     assertEquals(1, testRBT.root.context[2].context[1].blackHeight,
     "node must be black");
     assertEquals(1, testRBT.root.context[2].context[2].blackHeight,
     "node must be black");
     assertEquals(0, testRBT.root.context[2].context[2].context[2].blackHeight,
     "node must be red");
     assertEquals(1, testRBT.root.context[2].context[1].blackHeight,
     "node must be black");

     ArrayList<Integer> removals = new ArrayList<Integer>();
     removals.add(12);
     removals.add(19);
     removals.add(5);
    
     testRBT.removeMultiple(removals);

     // testing colour changes and level order traversal of tree after removeMultiple
     assertEquals("[ 9, 3, 17 ]", testRBT.toLevelOrderString());
     assertEquals(1, testRBT.root.blackHeight,
     "node must be black");
     assertEquals(1, testRBT.root.context[2].blackHeight,
     "node must be black");
     assertEquals(1, testRBT.root.context[1].blackHeight,
     "node must be black");


  }
  
  /**
   * Testing in-order traversal, and getNumberOfNodes
   */
  @Test
  void test5() {

    RedBlackTreeMultiAE<Integer> testRBT = new RedBlackTreeMultiAE<Integer>();

    // inserting elements into tree
    ArrayList<Integer> inserts = new ArrayList<Integer>();
    inserts.add(5);
    inserts.add(12);
    inserts.add(3);
    inserts.add(17);
    inserts.add(9);
    inserts.add(19);

    testRBT.putMultiple(inserts);
    
    ArrayList<Integer> traversalList = new ArrayList<Integer>();
    traversalList.add(3);
    traversalList.add(5);
    traversalList.add(9);
    traversalList.add(12);
    traversalList.add(17);
    traversalList.add(19);

    // testing getting number of nodes and in-order traversal of tree
     assertEquals(traversalList, testRBT.inOrderTraversal());
     assertEquals(6, testRBT.getNumberOfNodes(),
     "node count is incorrect");
  }

/*
  void testIntegration1(){
//    RedBlackTreeMultiInterface<Employee> testRBT = new RedBlackTreeMultiAE<Employee>();
//    EmployeeReaderInterface employeeReader = new EmployeeReader();
//    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(testRBT, employeeReader);

    EmployeeReaderInterface employeeReader = new EmployeeReader();
    RedBlackTreeMultiInterface<EmployeeInterface> testTree = new RedBlackTreeMultiAE<>();
    EmployeeDatabaseBackendInterface backend = new EmployeeDatabaseBackendBD(tree, reader);
    TextUITester tester = new TextUITester(
        "A\nJohn Smith\nSWE\n10\n20230325\nC\nTed Johnson\nHR\n10\n20230326\nA\nQ\n");
    Scanner scnr = new Scanner(System.in);
    // Create a frontend instance to test its methods on
    EmployeeDatabaseFrontendInterface frontend = new EmployeeDatabaseFrontendFD(scnr, backend, reader);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    scnr.close();

     
  }
*/


  @Test
  void testCodeReviewOfDataWrangler1() {
    EmployeeReader e = new EmployeeReader();
    
    try {
      List<EmployeeInterface> testEmployees = e.readEmployeesFromFile("testEmployees.txt");
      assertEquals("Amanda", testEmployees.get(1).getEmployeeName(),"Incorrect employee");
      assertEquals("Otis", testEmployees.get(4).getEmployeeName(),"Incorrect employee");
      assertEquals("Matt", testEmployees.get(6).getEmployeeName(),"Incorrect employee");

    } catch (FileNotFoundException e1) {
        fail("invalid exception thrown");
        e1.printStackTrace();
    }
  }


}
