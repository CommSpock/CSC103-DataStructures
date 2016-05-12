// File: TableDoubleHash.java

// Project #4: Analysis of Hashing versus Double Hashing
// Authors: Rafael Ferrer and Carmen Chiu
// Due Date: Monday 5/22/16

/****************************************************************************************************
* A Table is an open-address hash table with a fixed capacity. The purpose is to show students how 
* an open-address hash table is implemented. Programs should generally use java.util.Hashtable
* rather than this hash table.
*
* <dt><b>Java Source Code for this class:</b><dd>
*   <A HREF="../../../../edu/colorado/collections/Table.java">
*   http://www.cs.colorado.edu/~main/edu/colorado/collections/Table.java
*   </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*   
* @version
*   May 12, 2016
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
	
	/// Private Instance Variables ///
	
	private int manyItems;
	private Object[] keys;
	private Object[] data;
	private boolean[] hasBeenUsed;   
	
	
	/// Constructors ///
	
	/**
	 * Initialize an empty table with a specified capacity.
	 * @param capacity
	 *   The capacity for this new open-address hash table.
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
	 * Retrieves an object for a specified key.
	 * @param key
	 *   The non-null key to look for.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   A reference to the object with the specified key (if this table contains an such an object);  null otherwise. 
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the table.
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
	 * Determines whether a specified key is in this table.
	 * @param key
	 *   The non-null key to look for.
	 * @precondition
	 *   Key cannot be null.
	 * @return
	 *   true (if this table contains an object with the specified key); false otherwise.
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the table.
	 * @exception NullPointerException
	 *   Indicates that key is null.
	 **/
	public boolean containsKey(K key)
	{
		return findIndex(key) != -1;
		
	}//End containsKey(K key) Method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @return
	 *   If the specified key is found in the table, then the return value is the index of the specified key. 
	 *   Otherwise, the return value is -1.
	 * @exception
	 *   
	 * @note
	 *   
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
			i = nextIndex(i);
		}//end while
		
		return -1;
		
	}//End findIndex(K key) Method
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @return
	 *   The return value is normally i+1. But if i+1 is data.length, then the return value is zero instead.
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private int nextIndex(int i)
	{
		if (i+1 == data.length){
			return 0;
		}
		else {
			return i+1;
		}
	
	}//End nextIndex(int i) Method
	
	
	/// Modifier Methods ///
	
	/**
	 * Description
	 * @param
	 *   
	 * @precondition
	 *   
	 * @return
	 *  The return value is a valid index of the table’s arrays. The index is calculated as the remainder 
	 *  when the absolute value of the key’s hash code is divided by the size of the table’s arrays.
	 * @exception
	 *   
	 * @note
	 *   
	 **/
	private int hash(Object key)
	{
		return Math.abs(key.hashCode( )) % data.length;
		
	}//End hash(Object key) Method
	
	/**
	 * Add a new element to this table, using the specified key.
	 * @param key
	 *   The non-null key to use for the new element.
	 * @param element
	 *   The new element that’s being added to this table.
	 * @precondition
	 *   If there is not already an element with the specified key, then this table’s 
	 *   size must be less than its capacity (i.e., size() < capacity()). 
	 *   Also, neither key nor element is null.
	 * @return
	 *   If this table already has an object with the specified key, then that object is 
	 *   replaced by element, and the return value is a reference to the replaced object. 
	 *   Otherwise, the new element is added with the specified key and the return value is null.
	 * @exception IllegalStateException
	 *   Indicates that there is no room for a new object in this table.
	 * @exception NullPointerException
	 *   Indicates that key or element is null.   
	 **/
	@SuppressWarnings("unchecked") //answer = (E) data[index];
	public E put(K key, E element)
	{
		//Instance Variables
		int index = findIndex(key);
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
			while (keys[index] != null){
				index = nextIndex(index);
			}
			keys[index] = key;
			data[index] = element;
			hasBeenUsed[index] = true;
			manyItems++;
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
	 *   If an object was found with the specified key, then that object has been removed from this table and 
	 *   a copy of the removed object is returned; otherwise, this table is unchanged and the null reference is returned. 
	 *   Note that key.equals( ) is used to compare the key to the keys that are in the table.
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
	
}//End TableDoubleHash Class

