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
	
	
	//Constructors
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - none
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public UnboundedInt()
	{
		tail = new IntNode(0, null);
		head = new IntNode(0, tail);
		nodeCount = 0;
		cursor = head.getLink();
		
	}//end UnboundedInt() constructor
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public UnboundedInt(int... bigInt)
	{
		tail = new IntNode(0, null);
		head = new IntNode(0, tail);
		nodeCount = 0;
		
		for (int smallInt: bigInt){
			//Set the tail node to the first bigInt one time.
			if (tail.getData() == 0){
				tail.setData(smallInt);
				nodeCount ++;
			}//end if
			//Add the next smallInt to the following node, moving toward the head node.
			else {
				head.addNodeAfter(smallInt);
				nodeCount ++;
			}//end else			
		}//end for
		
		//Set the head node to the nodeCount of this UnboundedInt
		head.setData(nodeCount);
		
		//Set cursors.
		cursor = head.getLink();
		
	}//end UnboundedInt(int... bigInt) constructor
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public void start()
	{
		cursor = head.getLink();
	}
		
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public void advance()
	{
		cursor = cursor.getLink();
	}
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public int getElement()
	{
		return cursor.getData();
	}
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public void addEnd(int smallInt)
	{
		tail.addNodeAfter(smallInt);
		tail = tail.getLink();
		cursor = tail;
	}
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public UnboundedInt add(UnboundedInt addend)
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
		addend.start();
		summedInt.start();

		//Sum nodes and write summedInt - Part I
		while (cursor != null && addend.cursor != null){
			sum = getElement() + addend.getElement();
			if (remainder == true){
				sum = sum + REMAINDER;
			}
			if (sum > CRITICAL){
				sum = sum - THOUSAND;
				if (summedInt.tail.getData() == 0){
					summedInt.tail.setData(sum);
				}
				else {
					summedInt.addEnd(sum);
				}
				advance();
				addend.advance();
				summedInt.nodeCount ++;
				remainder = true;
			}//end if
			else {
				if (summedInt.tail.getData() == 0){
					summedInt.tail.setData(sum);
				}
				else {
					summedInt.addEnd(sum);
				}
				advance();
				addend.advance();
				summedInt.nodeCount ++;
				remainder = false;
			}//end else
		}//end while

		//Write summedInt - Part II
		while (cursor != null || addend.cursor != null || remainder == true){
			if (remainder == true){
				if (cursor == null && addend.cursor == null){
					summedInt.addEnd(REMAINDER);
					summedInt.nodeCount ++;
					remainder = false;
				}
				else if (cursor != null){
					summedInt.addEnd(getElement() + REMAINDER);
					if (summedInt.getElement() > CRITICAL){
						summedInt.cursor.setData(0);
						summedInt.nodeCount ++;
						advance();
						remainder = true;
					}
					else {
						summedInt.nodeCount ++;
						advance();
						remainder = false;
					}
				}//end else if
				else if (addend.cursor != null){
					summedInt.addEnd(addend.getElement() + REMAINDER);
					if (summedInt.getElement() > CRITICAL){
						summedInt.cursor.setData(0);
						summedInt.nodeCount ++;
						advance();
						remainder = true;
					}
					else {
						summedInt.nodeCount ++;
						advance();
						remainder = false;
					}
				}//end else if
			}//end if
			else if (remainder == false){
				if (cursor != null){
					summedInt.addEnd(getElement());
					summedInt.nodeCount ++;
					advance();
				}
				else if (addend.cursor != null){
					summedInt.addEnd(addend.getElement());
					summedInt.nodeCount ++;
					advance();
				}
			}//end else if
		}//end while
		
		//Return summedInt, sum of both UnboundedInt's
		summedInt.head.setData(nodeCount);
		summedInt.start();
		return summedInt;
	}//end add(UnboundedInt addend) method
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public UnboundedInt multiply(UnboundedInt multiplier){
		UnboundedInt productInt = new UnboundedInt();
		
		multiplier.start();
		
		for (int i = 0; i < multiplier.head.getData(); i++){
			for (int j = 0; j < multiplier.cursor.getData(); j++){
				productInt = productInt.add(this);
			}
		}
		return productInt;
	}//end multiply(UnboundedInt multiplier) method
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public UnboundedInt clone(){
		
	}
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public boolean equals(Object obj){
		
	}
	
	
	/**
	 * Initializes and instantiates a new unbounded integer of arbitrary length.  
	 * @param - bigInt
	 * @postcondition
	 *   
	 * @exception OutOfMemoryError
	 *   
	 **/ 
	public String toString()
	{
		String bigInt = "";
		
		//Set cursor for UnboundedInt to the hundreds place of the linked list
		start();
		
		//Write the hundreds node
		bigInt = Integer.toString(cursor.getData());
		if (head.getData() == 1){
			bigInt = Integer.toString(getElement());
		}
		else if (cursor.getData() < 10){
			bigInt = "00" + bigInt;
		}
		else if (cursor.getData() < 100){
			bigInt = "0" + bigInt;
		}
		advance();
		//Write the following nodes
		while (cursor != null){
			if (getElement() < 10 && cursor.getLink() == null){
				bigInt = Integer.toString(getElement()) + "," + bigInt;
			}
			else if (getElement() < 10){
				bigInt = "00" + Integer.toString(getElement()) + "," + bigInt;
			}
			else if (getElement() < 100 && cursor.getLink() == null){
				bigInt = Integer.toString(getElement()) + "," + bigInt;
			}
			else if (getElement() < 100){
				bigInt = "0" + Integer.toString(getElement()) + "," + bigInt;
			}
			else if (getElement() >= 100){
				bigInt = Integer.toString(getElement()) + "," + bigInt;
			}
			advance();
		}//end while
		return bigInt;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		UnboundedInt testInt1 = new UnboundedInt(105,256,987,352,501,248,392);
		UnboundedInt testInt2 = new UnboundedInt(1,423,198,764,321,146);
		
		UnboundedInt testIntSum = testInt1.add(testInt2);
		
		System.out.println(testIntSum.toString());
		System.out.println("105,258,410,551,265,569,538");
		
		
	}
	
	
	
	
	
	

}//End UnboundedInt Class
