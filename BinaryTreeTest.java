//Radu Molea(100992298)
//Assignment5

public class BinaryTreeTest{
  
  public static void main(String[] args){
    BinaryTree bt = new BinaryTree();
 
    System.out.println("Adding several nodes");
 
    bt.add("D");
    bt.add("B");
    bt.add("A");
    bt.add("C");
    bt.add("E");
    bt.add("F");
    bt.add("G");
 
    System.out.println("Displaying the tree: ");
    
    bt.display();
  }
}
  
  
  