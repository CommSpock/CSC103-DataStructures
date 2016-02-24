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
	//   1. The number of elements in the sequences is in the instance variable manyItems.
	//   2. For an empty sequence (with no elements), we do not care what is stored in any of data; 
	//		for a non-empty sequence, the elements of the sequence are stored in data[0] through 
	//		data[manyItems-1], and we don’t care what’s in the rest of data.
	//   3. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems. 
	
	// Private Instance Variables
	private double[] data;
	private int manyItems;
	private int currentIndex; 
	
	
	//Constructor Methods
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
		try{
			//Set a default capacity for new DoubleArraySeq's.
			int INITAL_CAPACITY = 10;
			
			//Set each instance variable to its initial value.
			data = new double[INITAL_CAPACITY];
			manyItems = 0;
			currentIndex = 0;
		}//end try
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("There is not enough memory to create a new sequence!");
		}//end catch
	}//end DoubleArraySeq() method
	
	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that the addAfter and addBefore methods work
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param initialCapacity
	 *   The initial capacity of this sequence.
	 * @precondition
	 *   InitialCapacity is non-negative.
	 * @postcondition
	 *   This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException
	 *   Indicates that initialCapacity is negative.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new double[initialCapacity].
	 **/   
	public DoubleArraySeq(int initialCapacity)
	{
		try{
			//Set each instance variable to its initial value.
			data = new double[initialCapacity];
			currentIndex = 0;
			manyItems = 0;
		}//end try
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("There is not enough memory to create a new sequence of capacity " + initialCapacity + "!");
		}//end catch
	}//end DoubleArraySeq(int initialCapacity) method
	
	
	// Accessor Methods
	/**
	 * Accessor method to determine whether this sequence has a specified current element that can be retrieved with the getCurrent method. 
	 * @param - none
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent()
	{
		return (currentIndex < manyItems);
	}//end isCurrent() method
	
	/**
	 * Accessor method to get the current element of this sequence. 
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   The current element of this sequence.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so getCurrent may not be called.
	 **/
	public double getCurrent()
	{
		//Confirm that there is a current element first.
		if (isCurrent())
			return data[currentIndex];
		else
			throw new IllegalStateException("There is no current element! Please specify a current element first.");
	}//end getCurrent() method
	
	/**
	 * Accessor method to get the current capacity of this sequence. 
	 * The add method works efficiently (without needing more memory) until this capacity is reached.
	 * @param - none
	 * @return
	 *   The current capacity of this sequence.
	 **/
	public int getCapacity()
	{
		//Returns the number of indexes in the array.
		return data.length;
	}//end getCapacity() method
	
	/**
	 * Accessor method to get the available capacity (number of empty indexes) of this sequence.
	 * @param - none
	 * @return
	 *   The available capacity (number of empty indexes) of this sequence.
	 **/
	public int getAvailCapacity()
	{
		//Returns the number of empty indexes in the array.
		return data.length - manyItems;
	}//end getAvailCapacity() method

	/**
	 * Accessor method to determine the number of elements in this sequence.
	 * @param - none
	 * @return
	 *   The number of elements in this sequence.
	 **/ 
	public int size()
	{
		//Returns the number of elements in the sequence.
		return manyItems;
	}//end size() method
	
	
	// Setter Methods
	/**
	 * A method to move forward, so the current element is now the next element in this sequence.
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
		if (isCurrent())
			currentIndex++;
		else
			throw new IllegalStateException ("There is no current element! Advance may not be called.");
	}//end advance() method
	
	/**
	 * A method to set the current element at the front of this sequence.
	 * @param - none
	 * @precondition
	 *   The sequence may not be empty. 
	 * @postcondition
	 *   The front element of this sequence is now the current element 
	 *   (but if this sequence has no elements at all, then there is no current element).
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty.	
	 **/ 
	public void start()
	{
		if (manyItems > 0)
			currentIndex = 0;
		else
			throw new IllegalStateException("This sequence is empty!");
	}//end start() method
	
	/**
	 * A method that makes the last element of the sequence the current element. 
	 * @param none
	 * @precondition
	 *   The sequence may not be empty. 
	 * @postcondition
	 *   The last element in the sequence has been set to the current index.
	 *   (but if this sequence has no elements at all, then there is no current element).
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty.	
	 **/
	public void setCurrentLast()
	{
		if (manyItems > 0)
			currentIndex = manyItems - 1;
		else
			throw new IllegalStateException("This sequence is empty!");
	}//end setCurrentLast() method
	
	/**
	 * A method that makes the nth element become the current element and returns its value.
	 * @param n
	 *   The nth element in the sequence.
	 * @precondition
	 *   'n' must range from 1 to manyItems and the sequence may not be empty.
	 * @postcondition
	 *   The nth element of this sequence is now the current element and it's value has been returned.
	 *   If this sequence has no elements at all, then there is no current element.
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty, or n is greater than the sequence size (or less than 1).	
	 **/
	public double setCurrent(int n)
	{
		//'n' must range from 1 to manyItems and the DoubleArraySeq may not be empty.
		if (manyItems > 0 && n > 0 && n <= manyItems){
			currentIndex = n-1;
			return data[currentIndex];
		}//end if
		else
			throw new IllegalStateException ("This sequence is either empty or 'n' is greater than the sequence size (or less than 1)!");
	}//end setCurrent(int n) method
	
	/**
	 * A method that and makes the selected element the current element and returns what nth number of the sequence that element is.
	 * @param element
	 *   The selected element in the sequence.
	 * @precondition
	 *   The sequence may not be empty.
	 * @postcondition
	 *   The selected element of this sequence is now the current element, and what nth number of the sequence that element is has been returned.
	 *   If this sequence has no elements at all, then there is no current element.
	 *   If the sequence does not contain the element, then the current element does not change. 
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty, or the selected element does not exist in the sequence.	
	 **/
	public int getElement(double element)
	{		
		//Verify that the sequence is not empty.
		if (manyItems < 1)
			throw new IllegalStateException ("This sequence is empty!");
		//Search for the element in the sequence and return what nth number of the sequence that element is, if found. 
		int i;
		for (i = 0; i < manyItems; i++){
			if (data[i] == element){
				currentIndex = i;
				return currentIndex + 1;
			}//end if
		}//end for
		if (i == manyItems)
			throw new IllegalStateException ("This sequence does not contain the element " + element + "!");
		else
			return 0;
	}//end getElement(double element) method
	
	
	// Size Management Methods
	/**
	 * A method to change the current capacity of this sequence.
	 * @param minimumCapacity
	 *   The new capacity for this sequence.
	 * @postcondition
	 *   This sequence's capacity has been changed to at least minimumCapacity.
	 *   If the capacity was already at or greater than minimumCapacity, then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new int[minimumCapacity].
	 **/
	public void ensureCapacity(int minimumCapacity)
	{
		try{
			if (getCapacity() < minimumCapacity){
				//Create a new array of size minimumCapacity.
				double[] expandData = new double[minimumCapacity];
				//Copy all elements from data into the new array.
				System.arraycopy(data, 0, expandData, 0, manyItems);
				//Change data's reference to expandData.
				data = expandData;
			}//end if
		}//end try
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("This sequence capacity is too large! There is not enough memory to store a sequence of capacity " + minimumCapacity + "! "
										+ "Note: The add methods double the sequence capacity if maximum capacity has already been reached. "
										+ "If necessary, try manually ensuring a smaller capacity before adding more elements to this sequence.");
		}//end catch
	}//end ensureCapacity(int minimumCapacity) method
	
	/**
	 * A method to reduce the current capacity of this sequence to its actual size (i.e., the number of elements it contains).
	 * @param - none
	 * @postcondition
	 *   This sequence's capacity has been changed to its current size.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for altering the capacity. 
	 **/
	public void trimToSize()
	{
		try{
			if (data.length > manyItems){
				//Create a new double[] of size manyItems that data will point to after running this method.
				double[] trimmedArray = new double[manyItems];
				//Copy the information from data to trimmedArray. Then assign data to trimmedArray.
				System.arraycopy(data, 0, trimmedArray, 0, manyItems);
				data = trimmedArray;
			}//end if
		}//end try
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("There is not enough memory left to alter the capacity of this sequence!");
		}//end catch
	}//end trimToSize() method
	
	
	// Add Element Methods
	/**
	 * A method to add a new element to this sequence, after the current element. 
	 * If the new element would take this sequence beyond its current capacity, then the capacity is increased before adding the new element.
	 * @param element
	 *   The new element that is being added.
	 * @postcondition
	 *   A new copy of the element has been added to this sequence. If there was a current element, 
	 *   then the new element is placed after the current element. If there was no current element, 
	 *   then the new element is placed at the end of the sequence. In all cases, the new element 
	 *   becomes the new current element of this sequence. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addAfter(double element)
	{
		//Make sure there is enough capacity to add another element.
		if (data.length == manyItems)
			ensureCapacity(manyItems*2 + 1);
		
		if (isCurrent()){
			//Move all elements up an index, beginning at the end of the DoubleArraySeq and ending at the index after currentIndex. 
			for (int i = manyItems; i > (currentIndex + 1); i--)
	            data[i] = data[i-1];
			//Add the new element after the current element.
			currentIndex++;
			data[currentIndex] = element;
			manyItems++;
		}//end if
		else{
			//If there is no current element, add the element to the end of the sequence.
			currentIndex = manyItems;
			data[currentIndex] = element;
			manyItems++;
		}//end else
	}//end addAfter(double element) method

	/**
	 * A method to add a new element to this sequence, before the current element. 
	 * If the new element would take this sequence beyond its current capacity, then the capacity is increased before adding the new element.
	 * @param element
	 *   The new element that is being added.
	 * @postcondition
	 *   A new copy of the element has been added to this sequence. If there was a current element, 
	 *   then the new element is placed before the current element. If there was no current element, 
	 *   then the new element is placed at the start of the sequence. In all cases, the new element 
	 *   becomes the new current element of this sequence. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addBefore(double element)
	{
		//Make sure there is enough capacity to add another element.
		if (data.length == manyItems)
			ensureCapacity(manyItems*2 + 1);
		
		if (isCurrent()){
			//Move all elements up an index, beginning at the end of the DoubleArraySeq and ending at currentIndex.
			for (int i = manyItems; i > currentIndex; i--)
				data[i] = data[i-1];
			//Add the new element before the current element (in the current element's old index).
			data[currentIndex] = element;
			manyItems ++;
		}//end if
		else{ //currentIndex is beyond the last element or the DoubleArraySeq is empty.
			//Move all elements in the sequence up an index (only if currentIndex is beyond the last element).
			for (int i = manyItems; i > 0; i--)
				data[i] = data[i-1];
			//Add the new element to the beginning of the sequence.
			currentIndex = 0;
			data[currentIndex] = element;
			manyItems ++;
		}//end else	
	}//end addBefore(double element) method
	
	/**
	 * A method to add a new element at the front of the sequence and make it the current element. 
	 * If the new element would take this sequence beyond its current capacity, then the capacity is increased before adding the new element.
	 * @param element
	 *   The new element that is being added.
	 * @postcondition
	 *   The new element has been added to the front of this sequence.
	 *   The new element becomes the new current element of this sequence.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addFront(double element)
	{
		//Make sure there is enough capacity to add another element.
		if (data.length == manyItems)
			ensureCapacity(manyItems*2 + 1);
		
		//Move all elements in the sequence up an index.
		for (int i = manyItems; i > 0; i--)
			data[i] = data[i-1];
		//Add the new element to the beginning of the sequence.
		currentIndex = 0;
		data[currentIndex] = element;
		manyItems ++;
	}//end addFront(double element) method
	
	/**
	 * A method to add a new element at the end of the sequence and make it the current element. 
	 * If the new element would take this sequence beyond its current capacity, then the capacity is increased before adding the new element.
	 * @param element
	 *   The new element that is being added.
	 * @postcondition
	 *   The new element has been added to the end of this sequence.
	 *   The new element becomes the new current element of this sequence.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the sequence's capacity.
	 * @note
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addEnd(double element)
	{
		//Make sure there is enough capacity to add another element.
		if (data.length == manyItems)
			ensureCapacity(manyItems*2 + 1);
		
		//Add the new element to the end of the sequence.
		data[manyItems] = element;
		currentIndex = manyItems;
		manyItems++;
	}//end addEnd(double element) method
	
	/**
	 * A method to place the contents of another sequence at the end of this sequence.
	 * @param addend
	 *   A sequence whose contents will be placed at the end of this sequence.
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
	 *   An attempt to increase the capacity beyond Integer.MAX_VALUE will cause an arithmetic overflow that will cause the sequence to fail.
	 **/
	public void addAll(DoubleArraySeq addend)
	{
		//Make sure there is enough capacity to add the other DoubleArraySeq.
		ensureCapacity(manyItems + addend.manyItems);
		
		//Copy the addend sequence to the end of the invoked sequence.
		System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
		manyItems += addend.manyItems;
	}//end addAll(DoubleArraySeq addend) method
	
	/**
	 * A method to create a new sequence that contains all the elements from one sequence followed by another.
	 * @param s1
	 *   The first of two sequences.
	 * @param s2
	 *   The second of two sequences.
	 * @precondition
	 *   Neither s1 nor s2 is null.
	 * @return
	 *   A new sequence that has the elements of s1 followed by the elements of s2 (with no current element).
	 *   Subsequent changes to the new sequence will not affect the original sequences, nor vice versa.
	 * @exception NullPointerException.
	 *   Indicates that one of the arguments is null.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the new sequence.
	 * @note
	 *   An attempt to create a sequence with a capacity beyond Integer.MAX_VALUE will cause an arithmetic overflow that will cause the sequence to fail.
	 **/   
	public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2)
	{
		try{
			//Create a new DoubleArraySeq large enough to store the s1 and s2 DoubleArraySeq's.
			DoubleArraySeq newSequence = new DoubleArraySeq(s1.manyItems + s2.manyItems);
			
			//Copy DoubleArraySeq s1 and s2 to the new DoubleArraySeq, newSequence.
			System.arraycopy(s1.data, 0, newSequence.data, 0, s1.manyItems);
			System.arraycopy(s2.data, 0, newSequence.data, s1.manyItems, s2.manyItems);
			newSequence.manyItems = (s1.manyItems + s2.manyItems);
			newSequence.currentIndex = newSequence.manyItems;
			return newSequence;
		}//end try
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("The sequences are too large! There is not enough memory to concatenate these sequences!");
		}//end catch
	}//end concatenation(DoubleArraySeq s1, DoubleArraySeq s2) method
	
	
	// Remove Element Methods
	/**
	 * A method to remove the current element from this sequence.
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
			//Move each element down an index, starting with the element after the currentIndex.
			for (int i = currentIndex; i < manyItems; i++){
				data[i] = data[i + 1];
			}//end for loop
			manyItems--;
		}//end if
		else
			throw new IllegalStateException ("There is no current element!");
	}//end removeCurrent() method
	
	/**
	 * A method to remove the first element of the sequence. 
	 * @param - none
	 * @precondition
	 *   The sequence is not empty.
	 * @postcondition
	 *   The first element in this sequence has been removed. 
	 *   If there is a next element, that element is now the current element.
	 *   If there was no following element, then there is now no current element.
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty.	
	 **/
	public void removeFront()
	{
		if (manyItems > 0){
			currentIndex = 0;
			removeCurrent();
		}//end if
		else
			throw new IllegalStateException ("The sequence is empty!");
	}//end removeFront() method
	
	
	// Overridden Java Methods -- clone(), equals(Object obj), toString()
	/**
	 * A method to generate an independent copy (clone) of this sequence.
	 * @param - none
	 * @return 
	 *   The return value is an independent copy (clone) of this sequence. 
	 *   Subsequent changes to the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public DoubleArraySeq clone()
	{
		//Create a new DoubleArraySeq that will be returned as the clone of the invoked DoubleArraySeq. 
		DoubleArraySeq answer;
		
		try{
			//Clone the instance variables of the invoked DoubleArraySeq and assign them to the answer DoubleArraySeq.
			answer = (DoubleArraySeq) super.clone( );
		}//end try
		catch (CloneNotSupportedException e){
			// This exception should not occur. But if it does, it would probably indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable" clause at the start of this class.
			throw new RuntimeException ("This class does not implement Cloneable");
		}//end catch
		catch (OutOfMemoryError e){
			throw new OutOfMemoryError ("There is not enough memory available to clone this sequence!");
		}//end catch
		//Copy the information in the data instance variable (double[]) from the invoked DoubleArraySeq to its clone. 
		answer.data = data.clone( );
		return answer;
	}//end DoubleArraySeq clone() method
	
	/**
	 * A method to compare two DoubleArraySeq objects and determine if they are equivalent.
	 * @param obj
	 *   The sequence that is being compared to the current sequence.
	 * @postcondition
	 *   If the sequences being compared are equivalent, then equals will return true. Otherwise equals will return false. 	
	 **/
	public boolean equals(Object obj)
	{
		boolean areEqual = false;
		
		//Verify 1) That obj is a DoubleArraySeq.
		if (obj instanceof DoubleArraySeq){
			DoubleArraySeq candidate = (DoubleArraySeq) obj;
			//Verify 2) That candidate has the same number of elements as the invoked DoubleArraySeq.
			if (this.manyItems == candidate.manyItems){
				//Verify 3) That the elements in candidate and the invoked DoubleArraySeq are the same elements, in the same order.
				boolean isEqual = true;
				for (int i = 0; i < manyItems && isEqual; i++){
					if (this.data[i] != candidate.data[i])
						isEqual = false;
				}//end for loop
				if (isEqual)
					areEqual = true;
			}//end if
		}//end if
		return areEqual;
	}//end equals(Object obj)
	
	/**
	 * A method to print all elements of the sequence in order, separated by a space.
	 * @param - none
	 * @precondition
	 *   The sequence is not empty.
	 * @postcondition
	 *   The elements of this sequence have been printed in order, separated by a space. 
	 * @exception IllegalStateException
	 *   Indicates that the sequence is empty.	
	 **/
	public String toString()
	{
		//Make a String containing all the elements of this sequence in order. 
		StringBuilder dataString = new StringBuilder("");
		if (manyItems > 0){
			for(int i = 0; i < manyItems; i++)
	            dataString.append(data[i] + " ");
		}//end if
		else
			throw new IllegalStateException ("There is nothing in this sequence!");
		return dataString.toString();
	}//end toString() method
	
}//end DoubleArraySeq class
