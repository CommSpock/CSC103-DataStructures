// File: TableDoubleHash.java

// Project #4: Analysis of Hashing versus Double Hashing
// Authors: Rafael Ferrer
// Due Date: Monday 5/22/16

/****************************************************************************************************
* A TableDoubleHash is a double hash table with a fixed capacity. This table has been modified to 
* track and store statistics about the number of collisions that occur when an element is added to it. 
* The TableDoubleHash class is used in conjunction with the HashTesting class.
*
* <dt><b>Java Source Code for this class:</b><dd>
*   <A HREF="../../../../edu/colorado/collections/Table.java">
*   http://www.cs.colorado.edu/~main/edu/colorado/collections/Table.java
*   </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
* @author Rafael Ferrer
* 
* @version
*   May 20, 2016
****************************************************************************************************/


public class TableDoubleHash< K , E >
{
	// Invariant of the Table class:
	//   1. The number of items in the table is in the instance variable manyItems.
	//   2. The preferred location for an element with a given key is at index hash(key). 
	//      If a collision occurs, then next-Index is used to search forward to find the next open address. 
	//      When an open address is found at an index i, then the element itself is placed in data[i] and 
	//      the element’s key is placed at keys[i].
	//   3. An index i that is not currently used has data[i] and key[i] set to null.
	//   4. If an index i has been used at some point (now or in the past), then hasBeenUsed[i] is true; otherwise it is false.
    //   5. The instance variables avgCollisions, maxCollisions, firstCollisions, and totalCollisions store statistical data
	//      about the number of collisions that occur in a table when adding elements.
	
	/// Private Instance Variables ///
	
	private int manyItems;
	private double avgCollisions;
	private int maxCollisions;
	private int firstCollisions;
	private int totalCollisions;
	private Object[] keys;
	private Object[] data;
	private boolean[] hasBeenUsed;   
	
	
	/// Constructors ///
	
	/**
	 * Initialize an empty table with a specified capacity.
	 * @param capacity
	 *   The capacity for this new double hash table.
	 * @postcondition
	 *   This table is empty and has the specified capacity.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for the specified capacity. 
	 **/   
	public TableDoubleHash(int capacity)
	{
		// The manyItems instance variable is automatically set to zero, which is the correct initial value. 
		// The three arrays are allocated to be the specified capacity. The boolean array is automatically
		// initialized to false, and the other two arrays are automatically initialized to all null.
		if (capacity <= 0){
			throw new IllegalArgumentException("Capacity is negative");
		}
		keys = new Object[capacity];
		data = new Object[capacity];
		hasBeenUsed = new boolean[capacity];
		
	}//End TableDoubleHash(int capacity) Class
	
	
	/// Accessor Methods ///
	
	/**
	 * Returns the number of elements that are currently in this TableDoubleHash.
	 * @return
	 *   The number of elements that are currently in this TableDoubleHash.  
	 **/
	public double getElementCount()
	{
		return manyItems;
		
	}//End getElementCount() Method
	
	/**
	 * Returns the average number of collisions experienced by each element added to TableDoubleHash.
	 * @return
	 *   The average number of collisions experienced by each element added to TableDoubleHash.
	 **/
	public double getAvgCollisions()
	{
		return avgCollisions;
		
	}//End getAvgCollisions() Method
	
	/**
	 * Returns the maximum number of collisions experienced by a single element added to TableDoubleHash.
	 * @return
	 *   The maximum number of collisions experienced by a single element added to TableDoubleHash.
	 **/
	public int getMaxCollisions()
	{
		return maxCollisions;
		
	}//End getMaxCollisions() Method
	
	/**
	 * Returns the number of elements that experienced any collisions when added to TableDoubleHash.
	 * @return
	 *   The number of elements that experienced any collisions when added to TableDoubleHash. 
	 **/
	public int getFirstCollisions()
	{
		return firstCollisions;
		
	}//End getFirstCollisions() Method
	
	/**
	 * Returns the total sum of collisions that occurred after adding all elements to TableDoubleHash.
	 * @return
	 *   The total sum of collisions that occurred after adding all elements to TableDoubleHash.
	 **/
	public int getTotalCollisions()
	{
		return totalCollisions;
		
	}//End getTotalCollisions() Method
	
	/** 
	 * Retrieves an object for a specified key.
	 * @param key
	 *   The non-null key to look for.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   A reference to the object with the specified key (if this TableDoubleHash contains an such an object);  null otherwise. 
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the TableDoubleHash.
	 * @exception NullPointerException
	 *   Indicates that key is null.
	 **/
	@SuppressWarnings("unchecked") //return (E) data[index];
	public E get(K key)
	{
		//Instance Variables
		int index = findIndex(key);
		
		if (index == -1){
			return null;
		}
		else {
			return (E) data[index];
		}
		
	}//End get(K key) Method
	
	/**
	 * Determines whether a specified key is in this TableDoubleHash.
	 * @param key
	 *   The non-null key to look for.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   True (if this TableDoubleHash contains an object with the specified key); false otherwise.
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the TableDoubleHash.
	 * @exception NullPointerException
	 *   Indicates that key is null.
	 **/
	public boolean containsKey(K key)
	{
		return findIndex(key) != -1;
		
	}//End containsKey(K key) Method
	
	
	/// Private Hashing Related Methods ///
	
	/**
	 * The first hash function that returns the index of the TableDoubleHash's array that corresponds to the provided key.
	 * @param key
	 *   The non-null key to hash.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *  The return value is a valid index of the TableDoubleHash's arrays. The index is calculated as the remainder 
	 *  when the absolute value of the key’s hash code is divided by the size of the TableDoubleHash's arrays.
	 **/
	private int hash(Object key)
	{
		return Math.abs(key.hashCode( )) % data.length;
		
	}//End hash(Object key) Method
	
	/**
	 * The second hash function that returns the index of the TableDoubleHash's array that corresponds to the provided key.
	 * @param key
	 *   The non-null key to hash.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *  The return value is a valid index of the TableDoubleHash's arrays. The index is calculated as the remainder 
	 *  when the absolute value of the key’s hash code is divided by the size of the TableDoubleHash's arrays minus two.
	 **/
	private int doubleHash(Object key)
	{
		return Math.abs(key.hashCode( )) % (data.length-2);
		
	}//End hash(Object key) Method
	
	/**
	 * Returns the next index an element should try to be placed at if a collision occurs.
	 * @param index
	 *   The current index where the collision occurred.
	 * @return
	 *   The return value is calculated by the second hash function, doubleHash().
	 **/
	private int nextIndex(int index, K key)
	{
		return (index + doubleHash(key)) % data.length;
	
	}//End nextIndex(int i) Method
	
	/**
	 * Returns the index that is mapped to a given key.
	 * @param key
	 *   The non-null key to map.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   If the specified key is found in the TableDoubleHash, then the return value is the index of the specified key. 
	 *   Otherwise, the return value is -1.
	 **/
	private int findIndex(K key)
	{
		//Instance Variables
		int count = 0;
		int i = hash(key);
		
		while (count < data.length && hasBeenUsed[i]){
			if (key.equals(keys[i])){
				return i;
			}
			count++;
			i = nextIndex(i, key);
		}//end while
		
		return -1;
		
	}//End findIndex(K key) Method
	
	
	/// Modifier Methods ///
	
	/**
	 * Add a new element to this table, using the specified key.
	 * @param key
	 *   The non-null key to use for the new element.
	 * @param element
	 *   The new element that’s being added to this TableDoubleHash.
	 * @precondition
	 *   If there is not already an element with the specified key, then this TableDoubleHash's 
	 *   size must be less than its capacity (i.e., size() < capacity()). 
	 *   Also, neither key nor element is null.
	 * @return
	 *   If this TableDoubleHash already has an object with the specified key, then that object is 
	 *   replaced by element, and the return value is a reference to the replaced object. 
	 *   Otherwise, the new element is added with the specified key and the return value is null.
	 * @exception IllegalStateException
	 *   Indicates that there is no room for a new object in this TableDoubleHash.
	 * @exception NullPointerException
	 *   Indicates that key or element is null.
	 * @note
	 *   This method also calculates statistics on collisions that occur when trying to add new elements to the TableDoubleHash.
	 *   > The average number of collisions experienced by each element added to TableDoubleHash.
	 *   > The maximum number of collisions experienced by a single element added to TableDoubleHash.
	 *   > The number of elements that experienced any collisions when added to TableDoubleHash.
	 *   > The total sum of collisions that occurred after adding all elements to TableDoubleHash.
	 **/
	@SuppressWarnings("unchecked") //answer = (E) data[index];
	public E put(K key, E element)
	{
		//Instance Variables
		int index = findIndex(key);
		int elementCollisions = 0;
		E answer;
		
		// The key is already in the table.
		if (index != -1){
			answer = (E) data[index];
			data[index] = element;
			return answer;
		}
		// The key is not yet in this Table.
		else if (manyItems < data.length){
			index = hash(key);
			if (keys[index] != null){
				firstCollisions++;
			}
			while (keys[index] != null){
				index = nextIndex(index, key);
				elementCollisions++;
			}
			keys[index] = key;
			data[index] = element;
			hasBeenUsed[index] = true;
			++manyItems;
			//Calculate collision statistics
			totalCollisions = totalCollisions + elementCollisions;
			avgCollisions = (((((double) manyItems) - 1)*avgCollisions)+elementCollisions)/((double) manyItems);
			if (elementCollisions > maxCollisions){
				maxCollisions = elementCollisions;
			}
			return null;
		}//end else if
		// The table is full.
		else {
			throw new IllegalStateException("Table is full.");
		}
		
	}//End (K key, E element) Method
	
	/**
	 * Removes an object for a specified key.
	 * @param key
	 *   The non-null key to look for.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   If an object was found with the specified key, then that object has been removed from this TableDoubleHash and 
	 *   a copy of the removed object is returned; otherwise, this table is unchanged and the null reference is returned. 
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the TableDoubleHash.
	 * @exception NullPointerException
	 *   Indicates that key is null.
	 **/
	@SuppressWarnings("unchecked") //answer = (E) data[index];
	public E remove(K key)
	{
		int index = findIndex(key);
		E answer = null;
		
		if (index != -1){
			answer = (E) data[index];
			keys[index] = null;
			data[index] = null;
			manyItems--;
		}
		
		return answer;
		
	}//End remove(K key) Class
	
	
	/// Additional Methods Used for Testing ///
	
	/**
	 * Prints the element at every index of the Table. Used in testing and analysis of Table.
	 * @postcondition
	 *   Every index of the Table has been printed.
	 **/
	public void printTable()
	{		
		int i = 0;
		
		while (i < 240){
			System.out.println(i + "  " + data[i] + "\t\t" + (i+1) + "  " + data[i+1] + "\t\t" + (i+2) + "  " + data[i+2] + "\t\t" + (i+3) + "  " + data[i+3]);
			i = i + 4;
		}
		i = 240;
		System.out.println(i + "  " + data[i]);
		
	}//End printTable() Method
	
}//End TableDoubleHash Class

