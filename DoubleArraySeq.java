// File: DoubleArraySeq.java 

// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.


/******************************************************************************
* This class is a homework assignment;
* A DoubleArraySeq is a collection of double numbers.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the bag class 
* (start, getCurrent, advance and isCurrent).
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
*   March 5, 2002
******************************************************************************/
public class DoubleArraySeq implements Cloneable
{
	// Invariant of the DoubleArraySeq class:
	//   1. The number of elements in the sequences is in the instance variable 
	//      manyItems.
	//   2. For an empty sequence (with no elements), we do not care what is 
	//      stored in any of data; for a non-empty sequence, the elements of the
	//      sequence are stored in data[0] through data[manyItems-1], and we
	//      don’t care what’s in the rest of data.
	//   3. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems. 
	
	// Private Instance Variables
	private double[ ] data;
	private int manyItems;
	private int currentIndex; 
   
	//Constructors
	/**
	 * Initialize an empty sequence with an initial capacity of 10.  
	 * Note that the addAfter and addBefore methods work efficiently (without needing more memory) until this capacity is reached.
	 * @param - none
	 * @postcondition
	 *   This sequence is empty and has an initial capacity of 10.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new double[10].
	 **/   
	public DoubleArraySeq()
	{
		int INITAL_CAPACITY = 10; //Set a default value for new DoubleArraySeq
		
		data = new double[INITAL_CAPACITY];
		manyItems = 0;
		currentIndex = 0;
	}//end DoubleArraySeq() method
    
	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that
	 * the addAfter and addBefore methods work
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param initialCapacity
	 *   the initial capacity of this sequence
	 * @precondition
	 *   initialCapacity is non-negative.
	 * @postcondition
	 *   This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException
	 *   Indicates that initialCapacity is negative.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: 
	 *   new double[initialCapacity].
	 **/   
	public DoubleArraySeq(int initialCapacity)
	{
		// Implemented by student.
	}
        
	
	// Accessor Methods
	/**
	 * Accessor method to get the current element of this sequence. 
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   the current element of this sequence
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public double getCurrent()
	{
		// Implemented by student.
	}
	
	/**
	 * Accessor method to determine whether this sequence has a specified current element that can be retrieved with the getCurrent method. 
	 * @param - none
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent()
	{
		if (currentIndex >= manyItems)
			return false;
		else
			return true;
	}//end isCurrent() method
	
	/**
	 * Accessor method to get the current capacity of this sequence. 
	 * The add method works efficiently (without needing
	 * more memory) until this capacity is reached.
	 * @param - none
	 * @return
	 *   the current capacity of this sequence
	 **/
	public int getCapacity()
	{
		// Implemented by student.
	}

	/**
	 * Accessor method to determine the number of elements in this sequence.
	 * @param - none
	 * @return
	 *   the number of elements in this sequence
	 **/ 
	public int size()
	{
		return manyItems;
	}//end size() method
	
	
	// Setter Methods
	/**
	 * Move forward, so that the current element is now the next element in this sequence.
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true. 
	 * @postcondition
	 *   If the current element was already the end element of this sequence (with nothing after it), then there is no longer any current element. 
	 *   Otherwise, the new element is the element immediately after the original current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so advance may not be called.
	 **/
	public void advance()
	{
		if (isCurrent() == true)
			currentIndex++;
		else
			throw new IllegalStateException ("There is no current element, so advance may not be called.");
	}//end advance() method
	
	/**
	 * Set the current element at the front of this sequence.
	 * @param - none
	 * @postcondition
	 *   The front element of this sequence is now the current element 
	 *   (but if this sequence has no elements at all, then there is no current element).
	 **/ 
	public void start()
	{
		// Implemented by student.
	}
	
	/**
	 * A method that makes the last element of the sequence the current element. 
	 * Throw an IllegalStateException if the sequence is empty.
	 * @param element
	 *		
	 * @postcondition
	 *		
	 * @exception OutOfMemoryError
	 *		
	 * @note
	 *		
	 **/
	public void setCurrentLast()
	{
		
	}
	
	/**
	 * A method that makes the nth element become the current element.
	 * @param n
	 *   The nth element in the sequence. 
	 * @postcondition
	 *   The nth element of this sequence is now the current element 
	 *   (but if this sequence has no elements at all, then there is no current element).
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty, or n is greater than the sequence size (or less than 1).	
	 **/
	public void setCurrent(int n)
	{
		//n is between 1 and manyItems, and the DoubleArraySeq is not empty
		if (manyItems > 0 && n >= 1 && n <= manyItems)
			currentIndex = n-1;
		else
			throw new IllegalStateException ("The sequence is empty, or n is greater than the sequence size (or less than 1).");
	}// end setCurrent(int n) method
	
	/**
	 * A method that returns the nth element of the sequence, and makes current element the nth element 
	 * (n does not represent the array location). 
	 * @param n
	 *   The nth element in the sequence.
	 * @postcondition
	 *   The nth element of this sequence is now the current element 
	 *   (but if this sequence has no elements at all, then there is no current element).
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty, or n is greater than the sequence size (or less than 1).
	 * @note
	 *		
	 **/
	public double getElement(int n)
	{
		double element;
		
		//n is between 1 and manyItems, and the DoubleArraySeq is not empty
		if (manyItems > 0 && n >= 1 && n <= manyItems){
			currentIndex = n-1;
			element = data.getCurrent();
			return element;
		}//end if
		else
			throw new IllegalStateException ("The sequence is empty, or n is greater than the sequence size (or less than 1).");
	}//end getElement(int n) method
	
	
	// Size Management Methods
	/**
	 * Change the current capacity of this sequence.
	 * @param minimumCapacity
	 *   the new capacity for this sequence
	 * @postcondition
	 *   This sequence's capacity has been changed to at least minimumCapacity.
	 *   If the capacity was already at or greater than minimumCapacity,
	 *   then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new int[minimumCapacity].
	 **/
	public void ensureCapacity(int minimumCapacity)
	{
		// Implemented by student.
	}
	
	/**
	 * Reduce the current capacity of this sequence to its actual size (i.e., the number of elements it contains).
	 * @param - none
	 * @postcondition
	 *   This sequence's capacity has been changed to its current size.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for altering the capacity. 
	 **/
	public void trimToSize()
	{
		double[ ] trimmedArray;
		
		if (data.length != manyItems){
			trimmedArray = new double[manyItems];
			System.arraycopy(data, 0, trimmedArray, 0, manyItems);
			data = trimmedArray;
		}
	} 
	
	
	// Add Element Methods
	/**
	 * Add a new element to this sequence, after the current element. 
	 * If the new element would take this sequence beyond its current capacity,
	 * then the capacity is increased before adding the new element.
	 * @param element
	 *   the new element that is being added
	 * @postcondition
	 *   A new copy of the element has been added to this sequence. If there was
	 *   a current element, then the new element is placed after the current
	 *   element. If there was no current element, then the new element is placed
	 *   at the end of the sequence. In all cases, the new element becomes the
	 *   new current element of this sequence. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond
	 *   Integer.MAX_VALUE will cause the sequence to fail with an
	 *   arithmetic overflow.
	 **/
	public void addAfter(int element)
	{
		// Implemented by student.
	}

	/**
	 * Add a new element to this sequence, before the current element. 
	 * If the new element would take this sequence beyond its current capacity,
	 * then the capacity is increased before adding the new element.
	 * @param element
	 *   the new element that is being added
	 * @postcondition
	 *   A new copy of the element has been added to this sequence. If there was
	 *   a current element, then the new element is placed before the current
	 *   element. If there was no current element, then the new element is placed
	 *   at the start of the sequence. In all cases, the new element becomes the
	 *   new current element of this sequence. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond
	 *   Integer.MAX_VALUE will cause the sequence to fail with an
	 *   arithmetic overflow.
	 **/
	public void addBefore(int element)
	{
		//Make sure there is enough capacity to add another element
		if (data.length == manyItems)
			ensureCapacity(manyItems*2 + 1);
		
		if (isCurrent()){
			//Move all elements up an index, begging at the end of the DoubleArraySeq and ending at the currentIndex
			for (int i = manyItems; i > currentIndex; i--){
				data[i] = data[i-1];
			}//end for loop
			//Add the new element before the current element (in the current element's old index)
			data[currentIndex] = element;
			manyItems ++;
		}//end if
		else { //currentIndex is beyond the last element or the DoubleArraySeq is empty
			//Move all elements in the sequence up an index (only if currentIndex is beyond the last element)
			for (int i = manyItems; i > 0; i--){
				data[i] = data[i-1];
			}//end for loop
			//Add the new element to the beginning of the sequence
			data[0] = element;
			manyItems ++;
			currentIndex = 0;
		}//end else	
	}//end addBefore(int element) method
	
	/**
	 * A method to add a new element at the front of the sequence and make it the current element.
	 * @param element
	 *		
	 * @postcondition
	 *		
	 * @exception OutOfMemoryError
	 *		
	 * @note
	 *		
	 **/
	public void addFront(double element)
	{
		
	}
	
	/**
	 * A method to add a new element at the end of the sequence and make that element the current element. 
	 * @param element
	 *		
	 * @postcondition
	 *		
	 * @exception OutOfMemoryError
	 *		
	 * @note
	 *		
	 **/
	public void addEnd(double element)
	{
		
	}
	
	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * @param addend
	 *   A sequence whose contents will be placed at the end of this sequence
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The elements from addend have been placed at the end of this sequence. 
	 *   The current element of this sequence remains where it was, and the addend is also unchanged.
	 * @exception NullPointerException
	 *   Indicates that addend is null. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory to increase the size of this sequence.
	 * @note
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause 
	 *   an arithmetic overflow that will cause the sequence to fail.
	 **/
	public void addAll(DoubleArraySeq addend)
	{
		//Make sure there is enough capacity to add the other DoubleArraySeq
		ensureCapacity(manyItems + addend.manyItems);
		
		System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
		manyItems += addend.manyItems;
	}  
	
	/**
	 * Create a new sequence that contains all the elements from one sequence followed by another.
	 * @param s1
	 *   the first of two sequences
	 * @param s2
	 *   the second of two sequences
	 * @precondition
	 *   Neither s1 nor s2 is null.
	 * @return
	 *   a new sequence that has the elements of s1 followed by the elements of s2 (with no current element)
	 * @exception NullPointerException.
	 *   Indicates that one of the arguments is null.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the new sequence.
	 * @note
	 *   An attempt to create a sequence with a capacity beyond Integer.MAX_VALUE will cause 
	 *   an arithmetic overflow that will cause the sequence to fail.
	 **/   
	public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2)
	{
		DoubleArraySeq newSequence = new DoubleArraySeq(s1.manyItems + s2.manyItems);
		
		System.arraycopy(s1.data, 0, newSequence.data, 0, s1.manyItems);
		System.arraycopy(s2.data, 0, newSequence.data, s1.manyItems, s2.manyItems);
		newSequence.manyItems = (s1.manyItems + s2.manyItems);
		newSequence.currentIndex = 0;
		return newSequence;
	}//end concatenation(DoubleArraySeq s1, DoubleArraySeq s2) method
	
	
	// Remove Element Methods
	/**
	 * Remove the current element from this sequence.
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this sequence, and the following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so removeCurrent may not be called. 
	 **/
	public void removeCurrent()
	{
		if (isCurrent()){
			//Move each element down an index, starting with the element after the currentIndex
			for (int i = currentIndex; i < manyItems; i++){
				data[i] = data[i + 1];
			}//end for loop
			manyItems--;
		}//end if
		else
			throw new IllegalStateException("There is no current element, so removeCurrent may not be called.");
	}
	
	/**
	 * A method to remove the element at the front of the sequence. 
	 * If there is a next element, make that element the current element. 
	 * Throw an IllegalStateException if the sequence is empty
	 * @param element
	 *		
	 * @postcondition
	 *		
	 * @exception OutOfMemoryError
	 *		
	 * @note
	 *		
	 **/
	public void removeFront()
	{
		
	}
	
	
	// Overridden Java Methods (clone, equals, toString)
	/**
	 * Generate a copy of this sequence.
	 * @param - none
	 * @return
	 *   The return value is a copy of this sequence. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public DoubleArraySeq clone()
	{  // Clone a DoubleArraySeq object.
		DoubleArraySeq answer;
      
		try{
			answer = (DoubleArraySeq) super.clone( );
		}
		catch (CloneNotSupportedException e){
			// This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}
      
		answer.data = data.clone( );
		return answer;
	}
	
	/**
	 * A method to compare two DoubleArraySeq objects and determine if they are equivalent.
	 * @param obj
	 *   The sequence that is being compared to the current sequence.
	 * @postcondition
	 *   If the sequences being compared are equivalent, then equals will return true. Otherwise equals will return false. 	
	 **/
	public boolean equals(Object obj)
	{
		if (obj instanceof DoubleArraySeq){
			DoubleArraySeq candidate = (DoubleArraySeq) obj;
			if (this.manyItems == candidate.manyItems){
				boolean isEqual = true;
				for (int i = 0; i < manyItems && isEqual; i++){
					if (this.data[i] != candidate.data[i])
						isEqual = false;
				}//end for loop
				return (isEqual);
			}//end if
			else
				return false;
		}//end if
		else
			return false;
	}
	
	/**
	 * Outputs all elements in order separated by a space. 
	 * Throw an IllegalStateException if the sequence is empty.
	 * @param element
	 *		
	 * @postcondition
	 *		
	 * @exception OutOfMemoryError
	 *		
	 * @note
	 *		
	 **/
	public String toString()
	{
		
	}
	
}//end DoubleArraySeq class
