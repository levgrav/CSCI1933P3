package CSCI1933P3;

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private boolean isSorted;
    private int size;

    public LinkedList() {
        isSorted = true;
        head = null;
        tail = null;
        size = 0;
    }
    /**
   * Add an element to end of the list. If element is null,
   * it will NOT add it and return false.  Otherwise, it
   * will add it and return true. Updates isSorted to false if
   * the element added breaks sorted order.
   *
   * @param element element to be added to the list.
   * @return if the addition was successful.
   */
  public boolean add(T element){
    if (!(element == null)){
        if (head == null){
            head = new Node<T>(element);
            tail = head;
        } else {
            Node<T> curNode = head;
            while (curNode.getNext() != null){
                curNode = curNode.getNext();
            }
            curNode.setNext(new Node<T>(element));
            if (curNode.getData().compareTo(element) > 0){
                isSorted = false;
            }
            tail = curNode.getNext();
        }
        size += 1;
        return true;
    }
    return false;
  }



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
  public boolean add(int index, T element){
      if (index == 0){
              head = new Node<T>(element, head);
      } else if (size == 0) {
          head = new Node<T>(element);
          tail = head;
      } else if (index > 0 && index < size){
          Node<T> cNode = head;
          for (int i = 0; i < index - 1; i++){
              cNode = cNode.getNext();
          }
          Node<T> insertNode = new Node<T>(element, cNode.getNext());
          cNode.setNext(insertNode);
          size ++;
          if (cNode.getData().compareTo(element) > 0 || element.compareTo(insertNode.getNext().getData()) > 0) {
              isSorted = false;
          }
          if (cNode.getNext().getNext() == null){
              tail = cNode.getNext();
          }
      } else {
          return false;
      }
      return true;
  }



  /**
   * Remove all elements from the list and updates isSorted accordingly.
   */
  public void clear(){
    head = null;
    tail = null;
    size = 0;
    isSorted = true;
  }



  /**
   * Return the element at given index. If index is
   * out-of-bounds, it will return null.
   *
   * @param index index to obtain from the list.
   * @return the element at the given index.
   */
  public T get(int index){
    Node<T> cNode = head;
    if (index < size && index >= 0){
        for (int i = 0; i < index; i++){
            cNode = cNode.getNext();
        }
        return cNode.getData();
    } else {
        return null;
    }
  }



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
  public int indexOf(T element){
    Node<T> currNode = head;
    int idx = 0;

    while (currNode != null && element != null){
      if (currNode.getData().compareTo(element) == 0){
        return idx;
      } else if ((currNode.getData().compareTo(element) > 0 && isSorted)) {
          return -1;
      } else{
          currNode = currNode.getNext();
          idx += 1;
      }
    }
    return -1;
  }



  /**
   * Return true if the list is empty and false otherwise.
   *
   * @return if the list is empty.
   */
  public boolean isEmpty() {
      return head == null;
  }


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
  public int size(){
      return this.size;
  }

//TODO:

  /**
   *Sort the elements of the list in ascending order using one of the following sorting algorithms: Selection, Bubble, Insertion
   * 
   * If isSorted is true, increase the efficiency of the sort method
   * 
   * Hint: Since T extends Comparable, you will find it useful
   * to use the public int compareTo(T other) method.
   * Updates isSorted accordingly.
   */
  public void sort() { //change it up a bit
      if (head == null || head.getNext() == null) {
          isSorted = true;
          return;
      }

      boolean swapped = true;
      while (swapped) {
          swapped = false;
          Node<T> prev = null;
          Node<T> curr = head;

          while (curr != null && curr.getNext() != null) {
              Node<T> next = curr.getNext();

              if (curr.getData().compareTo(next.getData()) > 0) {
                  // Swap curr and next
                  if (prev == null) {
                      // Swapping at the head
                      curr.setNext(next.getNext());
                      next.setNext(curr);
                      head = next;
                      prev = next;
                  } else {
                      prev.setNext(next);
                      curr.setNext(next.getNext());
                      next.setNext(curr);
                      prev = next;
                  }

                  swapped = true;
              } else {
                  // No swap, just move forward
                  prev = curr;
                  curr = curr.getNext();
              }
          }
      }
      // Update tail and mark as sorted
      size = 0;
      Node<T> cNode = head;
      while (cNode != null){
          size++;
          if (cNode.getNext() == null) {
              tail = cNode;
          }
          cNode = cNode.getNext();
      }

  }


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
    public T remove(int index) { //change this up a bit
        if (index < 0 || index >= size || head == null) {
            return null;  // Index out of bounds or list is empty
        }

        Node<T> currNode = head;
        Node<T> prevNode = null;
        Node<T> remNode = null;

        if (index == 0) { // Removing from the head
            remNode = head;
            head = head.getNext();  // Move head to the next node
            if (head == null) {
                tail = null;  // If list becomes empty, tail should also be null
            }
        } else if (index == size - 1) { // Removing from the tail
            // Traverse the list to find the node just before the tail
            currNode = head;
            while (currNode.getNext() != null && currNode.getNext().getNext() != null) {
                currNode = currNode.getNext();
            }
            remNode = currNode.getNext();  // This will be the tail node
            currNode.setNext(null);  // Disconnect the tail node
            tail = currNode;  // Update the tail reference
        } else { // Removing from the middle
            for (int i = 0; i < index; i++) {
                prevNode = currNode;
                currNode = currNode.getNext();
            }
            remNode = currNode;
            prevNode.setNext(currNode.getNext());  // Skip the current node
        }

        size--;  // Decrease size of the list
        if (!isSorted) {  // If the list is unsorted, check for sorting condition
            checkSorted();
        }

        return remNode.getData();
    }

    // Method to check if the list is sorted
    private void checkSorted() {
        Node<T> curr = head;
        isSorted = true;  // Assume sorted until proven otherwise

        while (curr != null && curr.getNext() != null) {
            if (curr.getData().compareTo(curr.getNext().getData()) > 0) {
                isSorted = false;
                break;  // No need to check further, it's already unsorted
            }
            curr = curr.getNext();
        }
    }


    /**
   * Reverses the list IN PLACE. Any use of intermediate data structures will yield
   * your solution invalid.
   */
    public void reverse() { // change this up a bit
        if (head == null || head.getNext() == null) {
            return; // Nothing to reverse
        }

        Node<T> prev = null;
        Node<T> curr = head;
        tail = head; // The old head becomes the new tail

        while (curr != null) {
            Node<T> next = curr.getNext(); // Store next
            curr.setNext(prev);            // Reverse link
            prev = curr;                   // Move prev forward
            curr = next;                   // Move curr forward
        }

        head = prev; // Update head to new front

        //TODO: add in a check sorted function
        isSorted = false; // Reversal likely destroys sorted order
    }


    /**
   * Remove all duplicate elements from the list.
   * The removal must be done in a stable manner,
   * or in other words the first occurrence of an element must keep its relative order
   * to the first occurrences of other elements.
   * Example A,B,A,C,B,A,D -> A,B,C,D
   */
  public void removeDuplicates(){

  }

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
  public void intersect(List<T> otherList) { // change this up a bit
      if (otherList == null || otherList.isEmpty()) {
          clear(); // If otherList is null or empty, clear the calling list
          return;
      }

      LinkedList<T> other = (LinkedList<T>) otherList;

      sort(); // Sort the calling list
      other.sort(); // Sort the other list

      Node<T> dummy = new Node<>(null); // Dummy node for building the result
      Node<T> t = dummy;
      Node<T> cNode = head;
      Node<T> otherCNode = other.head;

      while (cNode != null && otherCNode != null) {
          int comparison = cNode.getData().compareTo(otherCNode.getData());

          if (comparison == 0) {
              if (t == dummy || !t.getData().equals(cNode.getData())) {
                  t.setNext(new Node<>(cNode.getData()));
                  t = t.getNext();
              }
              cNode = cNode.getNext();
              otherCNode = otherCNode.getNext();
          } else if (comparison < 0) {
              cNode = cNode.getNext();
          } else {
              otherCNode = otherCNode.getNext();
          }
      }

      // Terminate new list
      t.setNext(null);

      // Replace current list with the intersection
      head = dummy.getNext();
      tail = t;
      //size = calculateSize(head);
      isSorted = true;
  }




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
  public void merge(List<T> otherlist){
//      LinkedList<T> other = (LinkedList<T>) otherList;
//      sort();
//      other.sort();
  }

  /**
   * Returns the minimum value of the List
   * If the list is empty, return NULL.
   * Note, consider how sorting can affect runtime or optimize this solution
   */
  public T getMin(){
    if (size == 0){
        return null;
    } else if (isSorted){
        return head.getData();
    } else {
        T value = head.getData();
        Node<T> currNode = head.getNext();
        for (int i = 1; i < size; i++){
            if (value.compareTo(currNode.getData()) > 0) {
                value = currNode.getData();
            }
            currNode = currNode.getNext();
        }
        return value;
    }
  }

  /**
   * Returns the maximum value of the List
   * 
   * If isSorted is true, make a case where this method is optimized.
   * Hint: Use a tail pointer for your LinkedList implementation.
   */
  public T getMax(){
      if (size == 0){
          return null;
      } else if (isSorted){
          return tail.getData();
      } else {
          T value = head.getData();
          Node<T> currNode = head.getNext();
          for (int i = 1; i < size; i++){
              if (value.compareTo(currNode.getData()) < 0) {
                  value = currNode.getData();
              }
              currNode = currNode.getNext();
          }
          return value;
      }
  }

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
  public String toString(){
      String s = "";
      Node<T> cNode = head;
      for (int i = 0; i< size; i++) {
          s += cNode.getData();
          s += "\n";
          cNode = cNode.getNext();
      }
      return s;
  }

  /**
   * Simply returns the isSorted attribute.
   *
   * @return isSorted boolean attribute.
   */
  public boolean isSorted(){
    return isSorted;
  }
}
