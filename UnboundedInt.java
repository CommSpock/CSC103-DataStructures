// File: UnboundedInt.java 

// Project #2: Chapter 4, project 10 - UnboundedInt Linked List
// Authors: Rafael Ferrer and Carmen 
// Due Date: Wednesday 3/23/16

/************************************************************************************************************  
* An UnboundedInt is a whole number integer of arbitrary length.
* The UnboundedInt class allows for the creation of unbounded integers and provides methods for manipulating
* those integers by means of basic arithmetic operators (addition, subtraction, multiplication, division).
*
* @note
* 	(1) The capacity of one a sequence can change after it's created, but
*   the maximum capacity is limited by the amount of free memory on the 
*   machine. The constructor, addAfter, addBefore, clone, and concatenation 
*   will result in an OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) A sequence's capacity cannot exceed the maximum integer 2,147,483,647
*   (Integer.MAX_VALUE). Any attempt to create a larger capacity
*   results in a failure due to an arithmetic overflow. 
*
* @note
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @see
*   <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
*   </A>
*
* @version
*   March 12, 2016
************************************************************************************************************/


public class UnboundedInt implements Cloneable {
	
	// Invariant of the UnboundedInt class:
	//   1. The number of elements in the sequences is in the instance variable manyItems.
	//   2. For an empty sequence (with no elements), we do not care what is stored in any of data; 
	//		for a non-empty sequence, the elements of the sequence are stored in data[0] through 
	//		data[manyItems-1], and we don’t care what’s in the rest of data.
	//   3. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems. 
	
	// Private Instance Variables
	private IntNode head;
	private IntNode tail;
	private IntNode cursor;
	private int nodeCount;
	
	
	///// Constructors /////
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates
	 **/ 
	public UnboundedInt()
	{
		head = null;
		tail = null;
		cursor = null;
		nodeCount = 0;
		
	}//End UnboundedInt() constructor
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates
	 **/ 
	public UnboundedInt(int... elements)
	{		
		for (int element: elements){
			addFront(element);
		}
	}//End UnboundedInt(int... elements) constructor
	
	
	///// Accessor Methods /////
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates
	 **/ 
	public int getElement()
	{
		return cursor.getData();
	}//End getElement() Method
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public int getNodeCount()
	{
		return nodeCount;
	}//End getNodeCount() Method
	
	
	///// Setter Methods /////
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public void start()
	{
		cursor = head;
	}//End start() Method
		
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates
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
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public void addFront(int element)
	{
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
		nodeCount++;
	}//End addFront(int element) Method
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public void addEnd(int element)
	{
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
		nodeCount++;
	}//End addEnd(int element) Method
	
	
	///// Arithmetic Operators /////
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public UnboundedInt add(UnboundedInt addendInt)
	{
		//Instance Variables
		int sum;
		final int REMAINDER = 1;
		final int THOUSAND = 1000;
		final int CRITICAL = 999;
		boolean remainder = false;
		UnboundedInt summedInt = new UnboundedInt();
		
		//Set cursors for all UnboundedInt's to the hundreds place of their linked list
		start();
		addendInt.start();

		//Sum node pairs and compose summedInt until one UnboundedInt runs out of nodes - Part I
		while (cursor != null && addendInt.cursor != null){
			
			//Get the sum of the current nodes
			sum = getElement() + addendInt.getElement();
			
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
			
			//Advance to the next pair of nodes
			advance();
			addendInt.advance();
		}//end while

		//Finish composing summedInt if any UnboundedInt runs out of nodes or a remainder is left  - Part II
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
		
		//Return summedInt, sum of both UnboundedInt's
		summedInt.start();
		return summedInt;
		
	}//End add(UnboundedInt addend) method
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates  
	 **/ 
	public UnboundedInt multiply(UnboundedInt multiplierInt){
		
	}//End multiply(UnboundedInt multiplier) method
	
	
	///// Overridden and Static Methods /////
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public UnboundedInt clone(){
		
	}//End clone() Method
	
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates  
	 **/ 
	public boolean equals(Object obj){
		
	}//end equals(Object obj) Method
	
	
	/**
	 * Method Description  
	 * @param - parameter
	 *   Parameter Description
	 * @precondition
	 *   Preconditions
	 * @postcondition
	 *   Postconditions
	 * @exception Exception
	 *   Indicates 
	 **/ 
	public String toString()
	{
		String unboundedInt = "";
		
		//Set cursor for UnboundedInt to the hundreds place of the linked list
		start();
		
		//Write the hundreds node
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
		
		//Write the following nodes
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
		
		return unboundedInt;
		
	}//End toString() Method
	
	
}//End UnboundedInt Class
