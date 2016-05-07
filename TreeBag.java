// File: TreeBag.java
// The implementation of most methods in this file is left as a student exercise from
// Section 9.5 of "Data Structures and Other Objects Using Java"

// Project #4: Golfer Scores Database using a Binary Search Tree
// Authors: Rafael Ferrer and Carmen Chiu
// Due Date: Monday 5/9/16

/******************************************************************************************
* This class is a homework assignment;
* An <CODE>TreeBag</CODE> is a collection of int numbers.
*
* <dl><dt><b>Limitations:</b> <dd>
*   Beyond <CODE>Integer.MAX_VALUE</CODE> elements, <CODE>countOccurrences</CODE>,
*   and <CODE>size</CODE> are wrong. 
*
* <dt><b>Note:</b><dd>
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @version
*   April 23, 2016
******************************************************************************************/

@SuppressWarnings("rawtypes") //TreeBag<E extends Comparable>
public class TreeBag<E extends Comparable> implements Cloneable
{
	// The Term E extends Comparable is letting the compiler know that any type used to instantiate E must implement Comparable. 
	// i.e. That means that whatever type E is must have a compareTo method so that elements can be compared against one another.
	// This is required because we are doing comparisons in our methods.
	
	
	// Invariant of the TreeBag class:
	//   1. The elements in the bag are stored in a binary search tree.
	//   2. The instance variable root is a reference to the root of the binary search tree (or null for an empty tree).
	
	/// Instance Variables ///
	private BTNode<E> root;
	
	/// Constructors ///
	// The default constructor TreeBag() will be called.
	// The single instance variable "root" will be set to null by default.
	
	
	/// Accessor Methods ///
	
	public BTNode getRoot()
	{
		return root;
		
	}//End BTNode getRoot() Method
	
	/**
	 * Retrieve location of a specified element from this bag.
	 * @param <CODE>target</CODE>
	 *   The element to locate in the bag.
	 * @return 
	 *  The return value is a reference to the found element in the tree.
	 * <dt><b>Postcondition:</b><dd>
	 *   If <CODE>target</CODE> was found in the bag, then method returns a reference to a comparable element.
	 *   If the target was not found then the method returns null. The bag remains unchanged.
	 **/
	@SuppressWarnings("unchecked") //target.compareTo(cursor.getData())
	public E retrieve(E target)
	{		
		if (root != null){
			BTNode<E> cursor = root;
			boolean done = false;
			
			while (cursor != null && !done){
				if (target.compareTo(cursor.getData()) < 0){
					cursor = cursor.getLeft();
				}
				else if (target.compareTo(cursor.getData()) > 0){
					cursor = cursor.getRight();
				}
				else if (target.compareTo(cursor.getData()) == 0){
					done = true;
					return cursor.getData();
				}
			}//end while
			return null;
		}//end if
		else {
			return null;
		}
		
	}//End retrieve(E target) Method
	
	/**
	 * Accessor method to count the number of occurrences of a particular element in this bag.
	 * @param <CODE>target</CODE>
	 *   The element that needs to be counted.
	 * @return
	 *   The number of times that <CODE>target</CODE> occurs in this bag.
	 **/
	@SuppressWarnings("unchecked") //if (target.compareTo(cursor.getData()) <= 0)
	public int countOccurrences(E target)
	{
		int count = 0;
		
		if (root != null){
			BTNode<E> cursor = root;
			
			while (cursor != null){
				if (target.equals(cursor.getData())){
					count++;
				}
				if (target.compareTo(cursor.getData()) <= 0){
					cursor = cursor.getLeft();
				}
				else {
					cursor = cursor.getRight();
				}
			}//end while
		}//end if
		
		return count;
		
	}//End countOccurrences(E target) Method
	
	/**
	 * Determine the number of elements in this bag.
	 * @param - none
	 * @return
	 *   The number of elements in this bag.
	 **/                           
	public int size()
	{
		return BTNode.treeSize(root);
		
	}//End size() Method
	
	/**
	 * Displays the entire tree of Node elements in a order specified by the elements compareTo method.
	 * @param 
	 *   none
	 * <dt><b>Postcondition:</b><dd>
	 *   Outputs all elements in the tree to Screen. Does not change the structure.
	 **/
	public void display()
	{
		//Handle null root (empty tree) case
		if (root == null){
			System.out.println("There are currently no elements.");
		}
		//Handle non-empty tree case
		else {
			root.inorderPrint();
		}
		
	}//End display() Method
	
	
	/// Modifier Methods ///
	
	/**
	 * Insert a new element into this bag.
	 * @param <CODE>element</CODE>
	 *   The new element that is being inserted.
	 * <dt><b>Postcondition:</b><dd>
	 *   A new copy of the element has been added to this bag.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory a new BTNode.
	 **/
	@SuppressWarnings("unchecked") //if (element.compareTo(cursor.getData()) <= 0)
	public void add(E element)
	{
		//Handle null root (empty tree) case
		if (root == null){
			root = new BTNode<E>(element, null, null);
		}
		//Handle non-empty tree case
		else {
			BTNode<E> cursor = root;
			boolean nodeAdded = false;
			
			while (!nodeAdded){
				if (element.compareTo(cursor.getData()) <= 0){
					if (cursor.getLeft() == null) {
						cursor.setLeft(new BTNode<E>(element, null, null));
						nodeAdded = true;
					}
					else {
						cursor = cursor.getLeft();
					}
				}//end if
				else {
					if (cursor.getRight() == null) {
						cursor.setRight(new BTNode<E>(element, null, null));
						nodeAdded = true;
					}
					else {
						cursor = cursor.getRight();
					}
				}//end else
			}//end while
		}//end else
	
	}//End add(E element) Method
	
	/**
	 * Add the contents of another bag to this bag.
	 * @param <CODE>addend</CODE>
	 *   A bag whose contents will be added to this bag.
	 * <dt><b>Precondition:</b><dd>
	 *   The parameter, <CODE>addend</CODE>, is not null.
	 * <dt><b>Postcondition:</b><dd>
	 *   The elements from <CODE>addend</CODE> have been added to this bag.
	 * @exception IllegalArgumentException
	 *   Indicates that <CODE>addend</CODE> is null.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory to increase the size of the bag.
	 **/
	public void addAll(TreeBag<E> addend)
	{
		// Implemented by student.
		
	}//End addAll(TreeBag<E> addend) Method
	
	/**
	 * Remove one copy of a specified element from this bag.
	 * @param <CODE>target</CODE>
	 *   The element to remove from the bag.
	 * <dt><b>Postcondition:</b><dd>
	 *   If <CODE>target</CODE> was found in the bag, then one copy of <CODE>target</CODE> has been removed and the method returns true.
	 *   Otherwise the bag remains unchanged and the method returns false.
	 **/
	@SuppressWarnings("unchecked") //(BTNode<E>) cursorArray[1];
	public boolean remove(E target)
	{
		Object[] cursorArray = retrieveForRemoval(target);
		BTNode<E> cursor = (BTNode<E>) cursorArray[1];
		BTNode<E> parentOfCursor = (BTNode<E>) cursorArray[0];
		
		//If the target is null
		if (cursor == null){
			return false;
		}
		//If cursor is root and has nothing
		else if (cursor == root && cursor.getLeft() == null && cursor.getRight() == null){
			root = null;
		}
		//If cursor is root and has a null left node
		else if (cursor == root && cursor.getLeft() == null){
			root = root.getRight();
		}
		//If cursor is root and has a null right node
		else if (cursor == root && cursor.getRight()== null){
			root = root.getLeft();
		}
		//If the cursor has a left node but no right node
		else if (cursor.getRight() == null){
			if (cursor == parentOfCursor.getLeft()){
				parentOfCursor.setLeft(cursor.getLeft());
			}
			else if (cursor == parentOfCursor.getRight()){
				parentOfCursor.setRight(cursor.getLeft());
			}
		}//end else if
		//If the cursor has a right node but no left node
		else if (cursor.getLeft() == null){
			if (cursor == parentOfCursor.getLeft()){
				parentOfCursor.setLeft(cursor.getRight());
			}
			else if (cursor == parentOfCursor.getRight()){
				parentOfCursor.setRight(cursor.getRight());
			}
		}//end else if
		//if the cursor has left and right children
		else {
			cursor.setData(cursor.getLeft().getRightmostData());
			cursor.setLeft(cursor.getLeft().removeRightmost());
		}
		
		return false;
		
	}//End remove(E target) Method
	
	/**
	 * Description
	 * this method retrieves the target for removal - only usage is for the removal method
	 * is Object because it returns an array of nodes
	 * @param
	 *   
	 * @precondition
	 *   
	 * @postcondition / return
	 *   
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	@SuppressWarnings("unchecked") //target.compareTo(cursor.getData()) 
	private Object[] retrieveForRemoval(E target)
	{
		BTNode<E> cursor = root;
		BTNode<E> parentOfCursor = null;
		boolean done = false;
		Object[] btArray = new Object[2];
		
		while (cursor != null && !done){
			if (target.compareTo(cursor.getData()) == 0){
				done = true;
			}
			else if (target.compareTo(cursor.getData()) < 0){
				parentOfCursor = cursor;
				cursor = cursor.getLeft();
			}
			else if (target.compareTo(cursor.getData()) > 0){
				parentOfCursor = cursor;
				cursor = cursor.getRight();
			}
		}//end while
		
		btArray[1] = cursor;
		btArray[0] = parentOfCursor;
		return btArray;
		
	}//End retrieveForRemoval(E target) Method
	
	/**
	 * Create a new bag that contains all the elements from two other bags.
	 * @param <CODE>b1</CODE>
	 *   The first of two bags.
	 * @param <CODE>b2</CODE>
	 *   The second of two bags.
	 * <dt><b>Precondition:</b><dd>
	 *   Neither b1 nor b2 is null.
	 * @return
	 *   The union of b1 and b2.
	 * @exception IllegalArgumentException
	 *   Indicates that one of the arguments is null.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the new bag.
	 **/   
	public static TreeBag union(TreeBag b1, TreeBag b2)
	{
		// Student will replace this return statement with their own code:
		return null;
		
	}//End union(TreeBag b1, TreeBag b2) Method
	
	
	/// Cloneable Interface ///
	
	/**
	 * Generate a copy of this bag.
	 * @param - none
	 * @return
	 *   The return value is a copy of this bag. Subsequent changes to the copy will not affect the original, nor vice versa.
	 *   Note that the return value must be type cast to an <CODE>TreeBag</CODE> before it can be used.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/
	public TreeBag<E> clone()
	{
		// Clone an IntTreeBag object.
		// Student will replace this return statement with their own code:
		return null;
		
	}//End clone() Method
	
	
}//End TreeBag Class

