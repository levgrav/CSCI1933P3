//nguy5571 & eby00009
package CSCI1933P3;
/**
 * Updated by Adam Kvant for Fall 2024 in May 2024.
 *
 * @param <T> Generic type for the list interface.
 */
public interface List<T extends Comparable<T>> {



  /**
   * Add an element to end of the list. If element is null,
   * it will NOT add it and return false.  Otherwise, it
   * will add it and return true. Updates isSorted to false if
   * the element added breaks sorted order.
   *
   * @param element element to be added to the list.
   * @return if the addition was successful.
   */
  boolean add(T element);



  /**
   * Add an element at specific index. This method should
   * also shift the element currently at that position (if
   * any) and any subsequent elements to the right (adds
   * one to their indices). If element is null, or if index
   * index is out-of-bounds (index < 0 or index >= size_of_list),
   * it will NOT add it and return false. Otherwise it will
   * add it and return true. See size() for the definition
   * of size of list. Also updates isSorted variable to false if the
   * element added breaks the current sorted order.
   *
   * @param index index at which to add the element.
   * @param element element to be added to the list.
   * @return if the addition was successful.
   */
  boolean add(int index, T element);



  /**
   * Remove all elements from the list and updates isSorted accordingly.
   */
  void clear();



  /**
   * Return the element at given index. If index is
   * out-of-bounds, it will return null.
   *
   * @param index index to obtain from the list.
   * @return the element at the given index.
   */
  T get(int index);



  /**
   * Return the first index of element in the list. If element
   * is null or not found in the list, return -1. 
   * 
   * If isSorted is true, uses the ordering of the list to increase the efficiency
   * of the search.
   *
   *
   * @param element element to be found in the list.
   * @return first index of the element in the list.
   */
  int indexOf(T element);



  /**
   * Return true if the list is empty and false otherwise.
   *
   * @return if the list is empty.
   */
  boolean isEmpty();



  /**
   * size() return the number of elements in the list. Be careful
   * not to confuse this for the length of a list like for an ArrayList.
   * For example, if 4 elements are added to a list, size will return
   * 4, while the last index in the list will be 3. Another example
   * is that an ArrayList like [5, 2, 3, null, null] would have a size
   * of 3 for an ArrayList.
   * ArrayList and LinkedList hint: create a class variable in both ArrayList
   * and LinkedList to keep track of the sizes of the respective lists.
   *
   * @return size of the list.
   */
  int size();



  /**
   *Sort the elements of the list in ascending order using one of the following sorting algorithms: Selection, Bubble, Insertion
   * 
   * If isSorted is true, increase the efficiency of the sort method
   * 
   * Hint: Since T extends Comparable, you will find it useful
   * to use the public int compareTo(T other) method.
   * Updates isSorted accordingly.
   */
  void sort();



  /**
   * Remove whatever is at index 'index' in the list and return
   * it. If index is out-of-bounds, return null. For the ArrayList,
   * elements to the right of index should be shifted over to maintain
   * contiguous storage. Must check to see if the list is sorted after removal
   * of the element at the given index and updates isSorted accordingly.
   *
   * @param index position to remove from the list.
   * @return the removed element.
   */
  T remove(int index);



  /**
   * Reverses the list IN PLACE. Any use of intermediate data structures will yield
   * your solution invalid.
   */
  void reverse();



  /**
   * Remove all duplicate elements from the list.
   * The removal must be done in a stable manner,
   * or in other words the first occurrence of an element must keep its relative order
   * to the first occurrences of other elements.
   * Example A,B,A,C,B,A,D -> A,B,C,D
   */
  void removeDuplicates();



  /**
   * Creates the intersection of 2 lists. The resulting intersection should be reflected in the calling List
   * Recall, intersection is the list of all common elements within two lists;
   * There should be no duplicates within the resulting List.
   * If otherList is null, do not attempt to merge.
   *
   * Sort should be called on the List that calls "intersect()", since the resulting list should be sorted.
   * Moreover, should also update isSorted to true.
   * Note, you will have to cast otherList from a List<T> type to a ArrayList<T> type or LinkedList<T>.
   *
   * After checking for possible errors, you're first two lines of code should be:
   * LinkedList<T> other = (LinkedList<T>) otherList; or ArrayList<T> other = (Arraylist<T>) otherList;
   * sort();
   *
   * @param otherList list to be used for finding the intersection.
   */
  void intersect(List<T> otherList);



/**
   * Merges two sorted lists together into this list. If other is null, do not
   * attempt to merge.
   * Sort MUST be called first on both this list and other list. The resulting
   * list should be sorted.
   * Updates isSorted to true. You will have to cast otherList from a List<T> type
   * to a LinkedList<T> type.
   *
   * After error checking, the first two lines of your code should be:
   * LinkedList<T> other = (LinkedList<T>) otherList;
   * sort();
   * other.sort();
   *
   * Other than these two lines, you may not sort, or call the sort method,
   * anywhere else in this function.
   * Ignoring this rule will result in an invalid solution.
   *
   * IMPORTANT NOTE: Ignore the time complexity of the sort function calls when
   * determining the time
   * complexity of this method. (i.e. Just consider the merging portion of this
   * function).
   *
   * Second Note for ArrayList: You will be required to create an array of the
   * perfect size to
   * fill all elements from both lists into the new one. Then you will update the
   * current list to
   * this new one.
   *
   *
   * @param list a second list to be merged with this one.
   */
  void merge(List<T> list);



  /**
   * Returns the minimum value of the List
   * If the list is empty, return NULL.
   * Note, consider how sorting can affect runtime or optimize this solution
   */
  T getMin();



  /**
   * Returns the maximum value of the List
   * 
   * If isSorted is true, make a case where this method is optimized.
   * Hint: Use a tail pointer for your LinkedList implementation.
   */
  T getMax();



  /**
   * Note that this method exists for debugging purposes.
   * It calls the toString method of the underlying elements in
   * the list in order to create a String representation of the list.
   * The format of the toString should appear as follows:
   * Element_1
   * Element_2
   * .
   * .
   * .
   * Element_n
   * Watch out for extra spaces or carriage returns. Each element
   * should be printed on its own line.
   *
   * @return String representation of the list.
   */
  String toString();


  
  /**
   * Simply returns the isSorted attribute.
   *
   * @return isSorted boolean attribute.
   */
  boolean isSorted();
}
