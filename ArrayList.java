package CSCI1933P3;
public class ArrayList<T extends Comparable<T>> implements List<T>{
    
    private T[] a;
    private boolean isSorted;
    private int maxSize;
    private int size;
    
    public ArrayList() {
        this.maxSize = 2;
        this.size = 0;
        this.isSorted = true;
        this.a = (T[]) new Comparable[maxSize];
    }
    
    public ArrayList(T[] a) {
        this.maxSize = a.length;
        this.size = a.length;
        this.a = a.clone();
        this.isSorted = false;
        this.checkIsSorted();
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
    public boolean add(T element) {
        if (element == null)
            return false;

        if (size >= maxSize)
            grow();

        this.a[size] = element;
        
        if (size != 0 && this.a[size].compareTo(this.a[size - 1]) < 0)
            this.isSorted = false;
        
        size ++;
        return true;
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
    public boolean add(int index, T element) {
        if (element == null)
            return false;

        if (size >= maxSize)
            grow();

        if (index < 0 || index > this.size)
            return false;

        for (int i = size; i > index; i--)
            this.a[i] = this.a[i-1];

        this.a[index] = element;

        if (size != 0 && (
            this.a[index].compareTo(this.a[index - 1]) < 0 
            || (index != this.size 
            && this.a[index].compareTo(this.a[index + 1]) > 0)))
            this.isSorted = false;
        
        size ++;
        return true;

    }

    /**
     * Adds space to the ArrayList by increasing masSize x2 and adding more space to the array. Used in the add methods
     */
    public void grow() {
        this.maxSize *= 2;
        T[] a = (T[]) new Comparable[maxSize];
        for (int i = 0; i < size; i++) {
            a[i] = this.a[i];
        }
        this.a = a;
    }
    
    /**
     * Remove all elements from the list and updates isSorted accordingly.
     */
    public void clear() {
        this.maxSize = 2;
        this.size = 0;
        this.isSorted = true;
        this.a = (T[]) new Comparable[maxSize];
    }

    /**
     * Return the element at given index. If index is
     * out-of-bounds, it will return null.
     *
     * @param index index to obtain from the list.
     * @return the element at the given index.
     */
    public T get(int index) {
        if(index >= 0 && index < size)
            return this.a[index];
        else
            return null;
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
    public int indexOf(T element) {
        if (element == null)
            return -1;

        if (isSorted) {
            int lower = 0;
            int upper = size - 1;
            ind idx;

            while (lower <= upper) {
                idx = (lower + upper) / 2;
                if (element == get(idx))
                    return idx;
                else if (element.compareTo(get(idx)) > 0)
                    lower = idx + 1; 
                else if (element.compareTo(get(idx)) < 0)
                    upper = idx - 1;
                else {

                }
            }

            return -1;

        } else {
            for (int i = 0; i < size; i++) {
                if (this.a[i] == element)
                    return i;
            }
            return -1;
        }
    }

    /**
     * Return true if the list is empty and false otherwise.
     *
     * @return if the list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * size() return the number of elements in the list. 
     * @return size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Sort the elements of the list in ascending order using one of the following sorting algorithms: Selection, Bubble, Insertion
     * If isSorted, does nothing 
     */
    public void sort() {
        if (this.isSorted || this.size <= 1) {
            return;
        }
        
        T t;
        int j;
        for (int i = 1; i < this.size; i ++) {
            t = this.a[i];
            for (j = i - 1; j >= 0 && this.a[j].compareTo(t) > 0; j--) {
                this.a[j + 1] = this.a[j];
            }
            this.a[j + 1] = t;
        }

        this.isSorted = true;
    }
    
    /**
     * Remove whatever is at index 'index' in the list and return
     * it. If index is out-of-bounds, return null. Elements to the right of index 
     * should be shifted over to maintain contiguous storage. Must check to see 
     * if the list is sorted after removal of the element at the given index and 
     * updates isSorted accordingly.
     *
     * @param index position to remove from the list.
     * @return the removed element.
     */
    public T remove(int index) {
        if (size <= 0)
            return null;

        if (index < 0 || index >= this.size)
            return null;

        T value = this.a[index];

        for (int i = index + 1; i < size; i++) 
            this.a[i-1] = this.a[i];

        if (this.size <= 1)
            this.isSorted = true;

        this.a[size-1] = null;
        size --;
        return value;
        
    }
    
    /**
     * Reverses the list IN PLACE
     */
    public void reverse() { // Note: You must reverse the list IN-PLACE (no intermediate data structures). 
        for (int i = 0; i < size / 2; i++) {
            T temp = this.a[i];
            this.a[i] = this.a[size - 1 - i];
            this.a[size - 1 - i] = temp;
        }
        this.isSorted = false;
    }

    /**
     * Remove all duplicate elements from the list.
     * The removal must be done in a stable manner,
     * or in other words the first occurrence of an element must keep its relative order
     * to the first occurrences of other elements.
     * Example A,B,A,C,B,A,D -> A,B,C,D
     */
    public void removeDuplicates() {
        for (int i = 0; i < this.size - 1; i++) {
            for (int j = i + 1; j < this.size; j++) {
                if (this.a[i] == this.a[j])
                    this.remove(j);
            }
        }
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
    public void intersect(List<T> otherList) {
        if (otherList == null || otherList.isEmpty())
            return;

        this.removeDuplicates();
        this.sort();
        
        boolean keep;
        for(int i = 0; i < this.size; i++) {
            keep = false;
            for(int j = 0; j < otherList.size(); j++) {
                if(this.get(i) == otherList.get(j))
                    keep = true;
            }
            if (!keep) {
                this.remove(i);
                i--;
            }
        }

    }

    /**
     * Merges two sorted lists together into this list. If other is null, do not
     * attempt to merge.
     * Sort MUST be called first on both this list and other list. The resulting
     * list should be sorted.
     * Updates isSorted to true.
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
     * @param list a second list to be merged with this one.
     */
    public void merge(List<T> list) {
        ArrayList<T> other = (ArrayList<T>) otherList;
        this.sort();
        other.sort();

        T[] aNew = (T[]) new Comparable[this.size + other.size];

        int thisIdx = 0;
        int otherIdx = 0;
        T thisElem;
        T otherElem;

        while (thisIdx < this.size || otherIdx < other.size) {
            
            thisElem = this.get(thisIdx);
            otherElem = other.get(otherIdx);

            if (thisElem.compareTo(otherElem) <= 0 || otherIdx >= other.size) {
                aNew[thisIdx + otherIdx] = thisElem;
                thisIdx += 1;
            } else {
                aNew[thisIdx + otherIdx] = otherElem;
                otherIdx += 1;
            } 
        }

        this.maxSize = aNew.length;
        this.size = aNew.length;
        this.isSorted = true;
        this.a = aNew;
    }

    /**
     * Returns the minimum value of the List
     * If the list is empty, return NULL.
     * If sorted, returns first element
     * @return thr minimum value of the list
     */
    public T getMin() {
        if (this.isEmpty())
            return null;

        if (this.isSorted) {
            return this.get(0);
        } 

        T min = this.get(0);
        for (int i = 1; i < this.size; i++) {
            if (min.compareTo(this.get(i)) > 0)
                min = this.get(i);
        }
        return min;
    }
    
    /**
     * Returns the maximum value of the List
     * If isSorted is true, returns last element
     * @return thr maximum value of the list
     */
    public T getMax() {
        if (this.isEmpty())
            return null;

        if (this.isSorted) {
            return this.a[this.size-1];
        } 

        T max = this.a[0];
        for (int i = 1; i < this.size; i++) {
            if (max.compareTo(this.a[i]) < 0)
                max = this.a[i];
        }
        return max;
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
    public String toString() {
        String out = "";
        for (int i = 0; i < this.size; i++) {
            out += this.a[i] + "\n";
        }
        out += "\b";
        return out;
    }

    /**
     * Simply returns the isSorted attribute.
     *
     * @return isSorted boolean attribute.
     */
    public boolean isSorted() {
        return isSorted;
    }
    
    /**
     * If isSorted, simply returns true.
     * Otherwise, runs through the list to see if elements are in ascending order
     * updates isSorted to match and returns the value
     * @return if ArrayList is sorted.
     */
    public boolean checkIsSorted() {
        if(this.isSorted)
            return true;

        for (int i = 0; i < this.size - 1; i++) {
            if(this.a[i].compareTo(this.a[i + 1]) > 0)
                return false;
        }

        this.isSorted = true;
        return true;
    }
    public static void main(String[] args) {
        // ArrayList tests. working properly
        // Integer[] data = {1, 2, 3, 4};
        // ArrayList<Integer> a = new ArrayList<Integer>(data);
        // System.out.println(a);
        // System.out.println(a.isSorted());
        // System.out.println(a.checkIsSorted());
        // a.reverse();
        // System.out.println(a);
        // System.out.println(a.checkIsSorted());
        // a.sort();
        // System.out.println(a);
        // System.out.println(a.isSorted());
        // a.add(5);
        // System.out.println(a);
        // System.out.println(a.isSorted());
        // a.add(1, 6);
        // System.out.println(a);
        // System.out.println(a.isSorted());
        // System.out.println(a.getMax());
        // System.out.println(a.getMin());
        // Integer[] data2 = {1, 2, 3, 4, 13, 15, 2, 3};
        // ArrayList<Integer> b = new ArrayList<Integer>(data2);
        // System.out.println(b);
        // a.merge(b);
        // System.out.println(a);
        // a.removeDuplicates();
        // System.out.println(a);
        // Integer[] data3 = {4, 5, 6, 7, 8, 9, 12, 13};
        // ArrayList<Integer> c = new ArrayList<Integer>(data3);
        // System.out.println(c);
        // a.intersect(c);
        // System.out.println(a);
    }
}
