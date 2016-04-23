// File: TreeBag.java 
// The implementation of most methods in this file is left as a student exercise from
// Section 9.5 of "Data Structures and Other Objects Using Java"

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
	public E retrieve(E target)
	{
		// Student will replace this return statement with their own code:
		return target;
		
	}//End retrieve(E target) Method
	
	/**
	 * Remove one copy of a specified element from this bag.
	 * @param <CODE>target</CODE>
	 *   The element to remove from the bag.
	 * <dt><b>Postcondition:</b><dd>
	 *   If <CODE>target</CODE> was found in the bag, then one copy of <CODE>target</CODE> has been removed and the method returns true.
	 *   Otherwise the bag remains unchanged and the method returns false.
	 **/
	public boolean remove(E target)
	{
		// Student will replace this return statement with their own code:
		return false;
		
	}//End remove(E target) Method
	
	/**
	 * Accessor method to count the number of occurrences of a particular element in this bag.
	 * @param <CODE>target</CODE>
	 *   The element that needs to be counted.
	 * @return
	 *   The number of times that <CODE>target</CODE> occurs in this bag.
	 **/
	public int countOccurrences(E target)
	{
		// Student will replace this return statement with their own code:
		return 0;
		
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
		// Student will replace this with their own code:
		
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
	public void add(E element)
	{      
		// Implemented by student.
		
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
	
	
	/// Static Methods ///
	
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

