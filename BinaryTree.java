//Radu Molea(100992298)
//Assignment5

import java.util.*;

public class BinaryTree {
  private String  data;
  private BinaryTree leftChild;
  private BinaryTree rightChild;
  
  //A default constructor with no data
  public BinaryTree() {
    data = null;
    leftChild = null;
    rightChild = null;
  }
  
  // A constructor that takes root data only and
  // makes a tree with no children (i.e., a leaf)
  public BinaryTree(String d) {
    data = d;
    leftChild = null;
    rightChild = null;
  }
  
  // A constructor that takes root data as well as two subtrees
  // which then become children to this new larger tree.
  public BinaryTree(String d, BinaryTree left, BinaryTree right) {
    data = d;
    leftChild = left;
    rightChild = right;
  }
  
  // Get methods
  public String getData() { return data; }
  public BinaryTree getLeftChild() { return leftChild; }
  public BinaryTree getRightChild() { return rightChild; }
  
  // Set methods
  public void setData(String d) { data = d; }
  public void setLeftChild(BinaryTree left) { leftChild = left; }
  public void setRightChild(BinaryTree right) { rightChild = right; }
  
  //recursive method that adds item to the binary tree lexicographically
  public void add(String item) {
    if (this.getData() == null){             //base case
      this.setData(item);                   
      this.setLeftChild(new BinaryTree());
      this.setRightChild(new BinaryTree());
    }
    else{                                      //all the other cases
      if(item.compareTo(this.getData()) <=0)   //if parameter string has smaller value then data
        this.getLeftChild().add(item);        //it will go recursive on left child
      else                                    //otherwise
        this.getRightChild().add(item);      //on right child
    }      
  }
  
  //recursively prints data from tree starting from left most child 
  public void display(){
    if (this.getData() == null)  //base case
      return;
    else {                              //general cases
      this.getLeftChild().display();          //goes to left 
      System.out.print(" " + this.getData()); //in general will print until all nodes applied to base case are checked 
      this.getRightChild().display();        //goes to right
    }
  }
  
}