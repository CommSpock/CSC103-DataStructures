// File: UnboundedInt.java 

// Project #2: Chapter 4, project 10 - UnboundedInt Linked List
// Authors: Rafael Ferrer and Carmen Chiu
// Due Date: Wednesday 3/23/16

/*****************************************************************************************************************  
* An UnboundedInt is a positive whole number, base 10 integer, of arbitrary length. The UnboundedInt class allows 
* for the creation of unbounded integers and provides methods for performing basic arithmetic operations with
* UnboundedInt's (addition and multiplication).
*
* @note
* 	(1) An UnboundedInt can be increased indefinitely after it's created, but the maximum size is limited 
*   by the amount of free memory on the machine. The UnboundedInt(int... elements), addFront(int element), 
*   addEnd(int element), add(UnboundedInt addendInt), multiply(UnboundedInt multiplierInt), and clone() 
*   methods will result in an OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) The elements of an UnboundedInt are stored in a sequence in increasing order from the element containing 
*   the ones place to the element containing the highest multiple of ten.
*   <p>
*   (3) Each element of an UnboundedInt contains a positive one, two, or three digit integer that represents
*   three placeholders of UnboundedInt.
*
* @version
*   March 20, 2016
*****************************************************************************************************************/


public class UnboundedInt implements Cloneable {
	
	// Invariant of the UnboundedInt class:
	//   1. The number of three integer elements in the UnboundedInt is in the instance variable nodeCount.
	//   2. The hundreds place in the UnboundedInt is in the instance variable head, which is also the first 
	//      element of the linked list holding the elements of the UnboundedInt.
	//   3. The highest power of ten in the UnboundedInt is in the instance variable tail, which is also the
	//      last element of the linked list holding the elements of the UnboundedInt.
	//   4. The instance variable current points to the currently selected element of the linked list. If there 
	//      is no currently selected element, the cursor will be null and at the end of the linked list.
	//   5. We care what is stored in every element that the head links to (directly or indirectly). We do not 
	//      care about any elements that the head has no link to (directly or indirectly).
 
	
	// Private Instance Variables
	private IntNode head;
	private IntNode tail;
	private IntNode cursor;
	private int nodeCount;
	
	
	///// Constructors /////
	/**
	 * Default constructor to initialize an empty UnboundedInt with no integer elements, 
	 * until elements are added with addFront(int element) or addEnd(int element).
	 * @param - none
	 * @postcondition
	 *   This UnboundedInt is empty, with no integer elements.
	 **/ 
	public UnboundedInt()
	{
		head = null;
		tail = null;
		cursor = null;
		nodeCount = 0;
		
	}//End UnboundedInt() constructor
	
	/**
	 * Constructor to initialize an UnboundedInt of arbitrary length, by elements of one, two, or 
	 * three integers. The UnboundedInt should be entered from the highest multiple of ten to the 
	 * ones place, with a comma after each element (except the last element). No element may be 
	 * negative and leading zeros should not be included in any element.  
	 * @param - elements
	 *   An arbitrary number of one, two, or three integer positive elements, entered from 
	 *   UnaboundedInt's highest multiple of 10 to the ones place, with a comma after each element.
	 * @precondition
	 *   Each element is one, two or three integers long, with a comma after each element (except 
	 *   the last element), and no element contains leading zeros. The UnboundedInt should be 
	 *   entered from the highest multiple of 10 to the ones place.
	 * @postcondition
	 *   This UnboundedInt is composed of the integer elements entered. The element containing the
	 *   ones place of UnboundedInt is the currently selected element. 
	 * @exception IllegalStateException
	 *   Indicates an element is negative or greater than three integers long.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for new UnboundedInt.
	 * @exception RunTimeError
	 *   May occur if an element contains leading zeros. 
	 * @note
	 *   If an element contains leading zeros and there is no compilation or runtime error, then the 
	 *   add(UnboundedInt addendInt) and multiply(UnboundedInt multiplierInt) methods will not work 
	 *   correctly. 
	 **/ 
	public UnboundedInt(int... elements)
	{
		final int CRITICAL = 999;
		final int ZERO = 0;
		
		for (int element: elements){
			//Verify that each element is a legal argument
			if(element < ZERO){
				throw new IllegalStateException("One of the elements entered is a negative number!");
			}
			if(element > CRITICAL){
				throw new IllegalStateException("One of the elements entered is more than three digits long!");
			}
			//Add each element from least to greatest placeholder
			addFront(element);
		}
		
	}//End UnboundedInt(int... elements) constructor
	
	
	///// Accessor Methods /////
	/**
	 * Accessor method to get the currently selected integer element of the UnboundedInt.
	 * @param - none
	 * @precondition
	 *   There must currently be a non-null element selected.
	 * @return
	 *   Returns the integer held in the currently selected element of UnboundedInt.
	 * @exception IllegalStateException
	 *   Indicates there is no current element selected, or the element is null.
	 **/ 
	public int getElement()
	{
		//Verify that there is a current element
		if (cursor == null){
			throw new IllegalStateException("There is no element currently selected!");
		}
		
		//Return the current element
		return cursor.getData();
		
	}//End getElement() Method
	
	/**
	 * Accessor method to get the number of integer elements composing the UnboundedInt.  
	 * @param - none
	 * @precondition
	 *   The UnboundedInt may not be null.
	 * @return
	 *   Returns the number of integer elements composing the UnboundedInt.
	 **/ 
	public int getElementCount()
	{
		return nodeCount;
		
	}//End getElementCount() Method
	
	
	///// Setter Methods /////
	/**
	 * A method to set the currently selected element to the element containing the ones place
	 * of the UnboundedInt.  
	 * @param - none
	 * @postcondition
	 *   The element containing the ones place of the UnboundedInt is now the currently selected
	 *   element.
	 **/ 
	public void start()
	{
		cursor = head;
		
	}//End start() Method
		
	/**
	 * A method to make the next element (next highest three digit placeholder of UnboundedInt) after 
	 * the currently selected element the new currently selected element of the UnboundedInt.
	 * @param - none
	 * @precondition
	 *   There must currently be a non-null element selected.
	 * @postcondition
	 *   The next element after the currently selected element is the new currently selected element 
	 *   of the UnboundedInt. If the previously selected element was the last element of the 
	 *   UnboundedInt, then the selected element will be null until the start() method is called.
	 * @note
	 *   This method does not throw any NullPointerExceptions or IllegalStateExceptions 
	 *   in order for the add(UnboundedInt addendInt) method to complete. If the cursor is null, 
	 *   then the getElement() method will throw a NullPointerException if called.
	 **/
	public void advance()
	{
		try {
			cursor = cursor.getLink();
		} 
		catch (NullPointerException e){}
		
	}//End advance() Method
	
	
	///// Modify Methods /////
	/**
	 * A method to add additional positive elements of one, two, or three integers to the UnboundedInt 
	 * before the current ones place. Leading zeros should not be included in element.
	 * @param - element
	 *   A positive one, two, or three integer element.
	 * @precondition
	 *   The element is positive, one, two, or three integers long, and contains no leading zeros.
	 * @postcondition
	 *   This UnboundedInt now has element added before the previous ones place, making element the
	 *   new ones place and shifting all other elements up by 1000. This element is the new currently 
	 *   selected element.
	 * @exception IllegalStateException
	 *   Indicates the element is negative or greater than three integers long.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for new UnboundedInt.
	 * @exception RunTimeError
	 *   May occur if element contains leading zeros. 
	 * @note
	 *   If element contains leading zeros and there is no compilation or runtime error, then the 
	 *   add(UnboundedInt addendInt) and multiply(UnboundedInt multiplierInt) methods will not work 
	 *   correctly.
	 **/ 
	public void addFront(int element)
	{
		final int CRITICAL = 999;
		final int ZERO = 0;
		
		//Verify that element is a legal argument
		if(element < ZERO){
			throw new IllegalStateException("The element entered is a negative number!");
		}
		if(element > CRITICAL){
			throw new IllegalStateException("The element entered is more than three digits long!");
		}
		
		//Make element the new head of the UnboundedInt
		if (head == null){
			head = new IntNode(element, null);
			tail = head;
			cursor = head;
		}
		else {
			IntNode newNode = new IntNode(element, head);
			head = newNode;
			cursor = head;
		}
		
		//Increase nodeCount
		nodeCount++;
		
	}//End addFront(int element) Method
	
	/**
	 * A method to add additional positive elements of one, two, or three integers to the UnboundedInt 
	 * after its current highest multiple of ten. Leading zeros should not be included in element.
	 * @param - element
	 *   A positive one, two, or three integer element.
	 * @precondition
	 *   The element is positive, one, two, or three integers long, and contains no leading zeros.
	 * @postcondition
	 *   This UnboundedInt now has element added after the current highest multiple of ten. This 
	 *   element is the new currently selected element.
	 * @exception IllegalStateException
	 *   Indicates the element is negative or greater than three integers long.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for new UnboundedInt.
	 * @exception RunTimeError
	 *   May occur if element contains leading zeros. 
	 * @note
	 *   If element contains leading zeros and there is no compilation or runtime error, then the 
	 *   add(UnboundedInt addendInt) and multiply(UnboundedInt multiplierInt) methods will not work 
	 *   correctly.
	 **/ 
	public void addEnd(int element)
	{
		final int CRITICAL = 999;
		final int ZERO = 0;
		
		//Verify that element is a legal argument
		if(element < ZERO){
			throw new IllegalStateException("The element entered is a negative number!");
		}
		if(element > CRITICAL){
			throw new IllegalStateException("The element entered is more than three digits long!");
		}
		
		//Make element the new tail of the UnboundedInt
		if (tail == null){
			tail = new IntNode(element, null);
			head = tail;
			cursor = tail;
		}
		else {
			tail.addNodeAfter(element);
			tail = tail.getLink();
			cursor = tail;
		}
		
		//Increase nodeCount
		nodeCount++;
		
	}//End addEnd(int element) Method
	
	
	///// Arithmetic Operators /////
	/**
	 * A method to add one unboundedInt to another UnboundedInt and return their sum.
	 * @param - addendInt
	 *   The UnboundedInt to be added to the invoked UnboundedInt.
	 * @precondition
	 *   The invoked UnboundedInt and the addendInt may be any size or empty. 
	 *   An UnboundedInt may be added to itself. 
	 * @return
	 *   Returns the sum of the invoked UnboundedInt and the addendInt as a new UnboundedInt. 
	 *   The returned UnboundedInt's currently selected element is the element containing the ones place.
	 **/ 
	public UnboundedInt add(UnboundedInt addendInt)
	{
		//Instance Variables
		int sum;
		boolean remainder = false;
		UnboundedInt summedInt = new UnboundedInt();
		
		//Constants
		final int REMAINDER = 1;
		final int THOUSAND = 1000;
		final int CRITICAL = 999;
		
		//Account for adding an UnboundedInt to itself
		if (addendInt == this){
			addendInt = (UnboundedInt) this.clone();
		}
		
		//Set cursors for all UnboundedInt's to the ones place of their linked list
		start();
		addendInt.start();

		//Part I - Sum element pairs and compose summedInt until one UnboundedInt runs out of elements
		while (cursor != null && addendInt.cursor != null){
			
			//Get the sum of the current elements
			sum = getElement() + addendInt.getElement();
			
			//Add the sum to summedInt
			if (remainder == true){
				sum = sum + REMAINDER;
			}
			
			if (sum > CRITICAL){
				sum = sum - THOUSAND;
				summedInt.addEnd(sum);
				remainder = true;
			}
			else {
				summedInt.addEnd(sum);
				remainder = false;
			}
			
			//Advance to the next pair of elements
			advance();
			addendInt.advance();
		}//end while

		//Part 2 - Finish composing summedInt if either UnboundedInt runs out of elements or any remainder is left
		while (cursor != null || addendInt.cursor != null || remainder == true){
			
			//Handle any cases where a remainder exists
			if (remainder == true){
				if (cursor == null && addendInt.cursor == null){
					summedInt.addEnd(REMAINDER);
					remainder = false;
				}
				else if (cursor != null){
					sum = getElement() + REMAINDER;
					if (sum > CRITICAL){
						sum = sum - THOUSAND;
						summedInt.addEnd(sum);
						remainder = true;
					}
					else {
						summedInt.addEnd(sum);
						remainder = false;
					}
				}//end else if
				else if (addendInt.cursor != null){
					sum = addendInt.getElement() + REMAINDER;
					if (sum > CRITICAL){
						sum = sum - THOUSAND;
						summedInt.addEnd(sum);
						remainder = true;
					}
					else {
						summedInt.addEnd(sum);
						remainder = false;
					}
				}//end else if
			}//end if
			
			//Handle any cases where a remainder does not exists
			else if (remainder == false){
				if (cursor != null){
					summedInt.addEnd(getElement());
				}
				else if (addendInt.cursor != null){
					summedInt.addEnd(addendInt.getElement());
				}
			}//end else if
			
			//Advance to any remaining nodes
			advance();
			addendInt.advance();
		}//end while
		
		//Return summedInt
		summedInt.start();
		return summedInt;
		
	}//End add(UnboundedInt addend) method
	
	/**
	 * A method to multiply one unboundedInt by another UnboundedInt and return their product.  
	 * @param - multiplierInt
	 *   The UnboundedInt to multiply the invoked UnboundedInt by.
	 * @precondition
	 *   Neither the invoked UnboundedInt or multiplierInt may be empty.
	 *   An UnboundedInt may be multiplied by itself. 
	 * @return
	 *   Returns the product of the invoked UnboundedInt and the multiplierInt as a new UnboundedInt. 
	 *   The returned UnboundedInt's currently selected element is the element containing the ones place.
	 * @exception IllegalStateException
	 *   Indicates that either the invoked UnboundedInt or the multiplierInt is empty. 
	 **/ 
	public UnboundedInt multiply(UnboundedInt multiplierInt)
	{
		//check that both numbers are not empty
		if (this.nodeCount != 0 && multiplierInt.nodeCount!= 0){
			//make a variable to hold the carryover
			int carryOver = 0;
			//make a variable to keep track of zeros
			int zeroCount = 0;
			
			UnboundedInt total = new UnboundedInt();
			total.addEnd(0);
			
			int currentProduct;
			
			//zero constant
			final int ZERO = 0;
			
			for (IntNode thisCursor = head; thisCursor !=null; thisCursor = thisCursor.getLink()){
				//set carryOver to 0 when starting a new node
				carryOver = 0;
				
				//check if this needs to be set to null each time
				UnboundedInt uCurrentTotal = new UnboundedInt();
				
				for (int i = 0; i<zeroCount; i++){
					uCurrentTotal.addEnd(ZERO);
				}
				
				//start calculating the inputInt digits
				for (IntNode inputCursor = multiplierInt.head; inputCursor !=null; inputCursor = inputCursor.getLink()){
					currentProduct = (inputCursor.getData() * thisCursor.getData()) + carryOver;
					//carryOver happens if the currentProduct is larger than 1000
					carryOver = currentProduct/1000;
					
					//make sure currentProduct does not exceed 1000 so set it to mod 1000
					currentProduct = currentProduct%1000;
					
					uCurrentTotal.addEnd(currentProduct);
				}//end loop
				//need to check for carryOver that might be left over
				if (carryOver > 0){
					uCurrentTotal.addEnd(carryOver);
				}
				//add to the total
				total = total.add(uCurrentTotal);
				
				uCurrentTotal = null;
				
				zeroCount++;
				}//end of outer loop
			
			total.start();
			return total;
		}//end of multiply
		else {
			throw new IllegalStateException("There is nothing in this number!");
		}
		
	}//End multiply(UnboundedInt multiplierInt) method
	
	
	///// Overridden and Static Methods /////
	/**
	 * A method to generate an independent copy (clone) of this UnboundedInt.  
	 * @param - none
	 * @return
	 *   The return value is an independent copy (clone) of this UnboundedInt.
	 *   The copy's currently selected element is the element containing the ones place.
	 *   Subsequent changes to the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 * @exception RuntimeException
	 *   Indicates this class does not implement cloneable.
	 **/
	public Object clone()
	{
		UnboundedInt clonedInt;
		IntNode[] nodeArray;
		
		try {
			clonedInt = (UnboundedInt) super.clone();
		}
		catch (CloneNotSupportedException e){
			throw new RuntimeException ("This class does not implement cloneable.");
		}
		//Assign head and tail to an array
		nodeArray = IntNode.listCopyWithTail(head);
		
		//Assign the head and tail to the head and tail references of the array
		clonedInt.head = nodeArray[0];
		clonedInt.tail = nodeArray[1];
		//Make sure to set the cursor to the tail of the array
		clonedInt.cursor = clonedInt.tail;
		
		clonedInt.start();
		return clonedInt;
		
	}//End clone() Method
	
	/**
	 * A method to compare two UnboundedInt objects and determine if they are equivalent.  
	 * @param - obj
	 *   The UnboundedInt that is being compared to the current UnboundedInt.
	 * @postcondition
	 *   If the UnboundedInt's being compared are equivalent, then equals will return true. 
	 *   Otherwise equals will return false.
	 **/ 
	public boolean equals(Object obj)
	{
		boolean areEqual = false;
		
		//Verify 1) That obj is an UnboundedInt.
		if (obj instanceof UnboundedInt){
			UnboundedInt candidate = (UnboundedInt) obj;
			//Verify 2) That candidate has the same number of elements as the invoked UnboundedInt.
			if (nodeCount == candidate.nodeCount){
				//Verify 3) That the elements in candidate and the invoked DoubleArraySeq are the same elements, in the same order.
				boolean isEqual = true;
				start();
				candidate.start();
				while (cursor != null && isEqual){
					if (getElement() != candidate.getElement()){
						isEqual = false;
					}
					advance();
					candidate.advance();
				}//end while
				if (isEqual){
					areEqual = true;
				}
			}//end if
		}//end if
		
		return areEqual;
		
	}//end equals(Object obj) Method
	
	/**
	 * Method Description  
	 * @param - none
	 * @precondition
	 *   This UnboundedInt is not empty.
	 * @return
	 *   Returns a String that represents this UnboundedInt.
	 * @exception IllegalStateException
	 *   Indicates the UnboundedInt is empty.
	 **/ 
	public String toString()
	{
		//Verify that the UnboundedInt is not empty
		if(head == null){
			throw new IllegalStateException("This UnboundedInt is empty!");
		}
		
		//Create the String of UnboundedInt to return
		String unboundedInt = "";
		
		//Set cursor for UnboundedInt to the element containing the ones place
		start();
		
		//Write the element containing the ones place of UnboundedInt
		unboundedInt = Integer.toString(cursor.getData());
		if (nodeCount == 1){
			unboundedInt = Integer.toString(getElement());
		}
		else if (cursor.getData() < 10){
			unboundedInt = "00" + unboundedInt;
		}
		else if (cursor.getData() < 100){
			unboundedInt = "0" + unboundedInt;
		}
		advance();
		
		//Write the following elements of UnboundedInt
		while (cursor != null){
			if (getElement() < 10 && cursor.getLink() == null){
				unboundedInt = Integer.toString(getElement()) + "," + unboundedInt;
			}
			else if (getElement() < 10){
				unboundedInt = "00" + Integer.toString(getElement()) + "," + unboundedInt;
			}
			else if (getElement() < 100 && cursor.getLink() == null){
				unboundedInt = Integer.toString(getElement()) + "," + unboundedInt;
			}
			else if (getElement() < 100){
				unboundedInt = "0" + Integer.toString(getElement()) + "," + unboundedInt;
			}
			else if (getElement() >= 100){
				unboundedInt = Integer.toString(getElement()) + "," + unboundedInt;
			}
			advance();
		}//end while
		
		//Return the the String of UnboundedInt
		return unboundedInt;
		
	}//End toString() Method
	
	
}//End UnboundedInt Class
