//Written by nguy5571 & eby00009

package CSCI1933P3;

import java.util.Random; //remove at end

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private boolean isSorted;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        isSorted = true;
        size = 0;
    }

    public boolean add(T element) {
        if (element == null) { //checks to make sure element is not null
            return false;
        }

        Node<T> newNode = new Node<T>(element); //creates Node to "store" element
        if (head == null) { // Case 1: empty list
            head = newNode; // head points to the new node
            tail = newNode; //tail points to new node
        } else { // Case 2: list with at east 1 node
            tail.setNext(newNode); // puts node after tail

            // Check sorted status using tail (previous last element)
            // Do not need to check if list is unsorted, since adding element will not sort it
            if (isSorted && tail.getData().compareTo(element) > 0) {
                isSorted = false;
            }

            tail = newNode;  // Update tail to last node
        }

        size++; //increases size by 1
        return true;
    }

    public boolean add(int index, T element) {
        if (element == null || index < 0 || index > size) {
            // checks to make sure that element is not null, or invalid index
            return false;
        }

        Node<T> newNode = new Node<T>(element); // makes new node to "store" element

        if (index == 0) { //Case 1: Insert before head
            newNode.setNext(head); // make node point to head
            head = newNode; // then make head point to new node
            if (size == 0) { // if the list was empty, tail also points to new node
                tail = head;
            }
            // Check if adding to head breaks sorted order
            if (isSorted && size > 0 && head.getNext() != null &&
                    element.compareTo(head.getNext().getData()) > 0) {
                isSorted = false;
            }
        } else { // Case 2: Insert in between or at the end of list
            Node<T> prev = head; //starts at head
            for (int i = 0; i < index - 1; i++) { //iterates to the node before index
                prev = prev.getNext();
            }

            // inserts node at index by having new node point to the node after previous node
            // and previous node point to new node
            newNode.setNext(prev.getNext());
            prev.setNext(newNode);

            if (index == size) { // if inserted at the end of list
                tail = newNode; // tail points to new node
            }

            // Check both previous and next elements
            // Do not need to check if list is unsorted, since adding element will not sort it
            if (isSorted) {
                // Check previous element
                if (prev.getData().compareTo(element) > 0) {
                    isSorted = false;
                }
                // Check next element (if exists)
                if (newNode.getNext() != null &&
                        element.compareTo(newNode.getNext().getData()) > 0) {
                    isSorted = false;
                }
            }
        }

        size++;
        return true;
    }

    public void clear() {
        //sets head, tail, size, and isSorted back to the original values
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.isSorted = true;
    }

    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;  // Return null if invalid index
        }

        Node<T> cNode = this.head; // starts at head
        for (int i = 0; i < index; ++i) { // iterates until index
            cNode = cNode.getNext();
        }

        return cNode.getData();  // Return the data
    }

    public int indexOf(T element) {
        Node<T> currNode = this.head; // starts at head

        for(int idx = 0; currNode != null && element != null; ++idx) {
            // while there is a next element and the element is not null,
            // iterates through linked list, keeping track of the index
            if (currNode.getData().compareTo(element) == 0) { // if element found, returns index
                return idx;
            }

            if (currNode.getData().compareTo(element) > 0 && this.isSorted) {
                // if went through list and at a bigger value than element when list is sorted = does not exist
                // terminates early to reduce runtime
                return -1;
            }

            currNode = currNode.getNext(); // goes to next node after checking the two conditionals
        }

        return -1; // returns -1 if index for element was not found
    }

    public boolean isEmpty() {
        return head == null; // if the head pointer is empty = empty list
    }

    public int size() {
        return this.size; // returns size of list
    }

    public void sort() {
        //bubble sort
        //is more efficient when already sorted O(n),
        // dont know if you wanted me to just have a conditional so that we skip check when isSorted = true
        //if swapped even once, iterates through list again to bubble out the largest element
        //keeps iterating until all elements are sorted
        if (head != null && head.getNext() != null) { //Case 1: more than 1 element
            boolean swapped = true; // boolean to keep track of whether an element has been swapped (for reiteration)

            while(swapped) { // if an element has been swapped, recheck list until all elements are in right place
                swapped = false; //set to false
                Node<T> prev = null; // nothing prior to head
                Node<T> curr = this.head;// starts at head

                while(curr.getNext() != null) { // iterates until end of list
                    Node<T> next = curr.getNext(); // sets nextNode to node after current node
                    if (curr.getData().compareTo(next.getData()) > 0) {
                        // compares current and next node and swaps them if current is bigger than next node, is stable
                        if (prev == null) {
                            //Case 1: if at head, switch head and the one after it
                            curr.setNext(next.getNext());
                            next.setNext(curr);
                            head = next; // changes head pointer to next
                            prev = next; //curr Node at index 1, so previous goes to head
                        } else {
                            // Case 2: swaps at middle/end of list
                            prev.setNext(next); // previous node points to next node
                            curr.setNext(next.getNext()); // current node points to node after next node
                            next.setNext(curr); // next node points to current node
                            prev = next; // previous advances to next node
                        }

                        swapped = true; // sets swapped to true, since we swapped nodes
                    } else { // no swap for current and next Node, so continue iterating
                        prev = curr;
                        curr = curr.getNext();
                    }
                }
            }

            for(Node<T> cNode = this.head; cNode != null; cNode = cNode.getNext()) { // finds tail
                if (cNode.getNext() == null) {
                    tail = cNode; //sets tail
                }
            }
            this.isSorted = true;

        } else { // Case 2: only 1 element
            this.isSorted = true;
        }
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            return null; // invalid index
        }

        T removedData; // stores data to return
        if (index == 0) { // at head, take data and move head over one
            removedData = head.getData();
            head = head.getNext();
            if (size == 1) {
                tail = head;
            }
        } else { // somewhere in between/ at the end
            Node<T> prev = head; //iterates through until before index
            for (int i = 0; i < index - 1; i++) {
                prev = prev.getNext();
            }
            removedData = prev.getNext().getData(); // stores the one to be removed
            prev.setNext(prev.getNext().getNext()); // changes pointer to remove node to be removed
            if (index == size - 1) {
                tail = prev; // tail pointer if last element
            }
        }

        size--; // decreases by 1
        if (size <= 1) {
            isSorted = true;  // Is sorted since only 1 element
        } else if (!isSorted) {
            checkSorted();    // might have become sorted after removal, so checks
        }

        return removedData;
    }

    //helper function to check if list is sorted
    private void checkSorted() {
        if (size <= 1) { // if one element then already sorted
            isSorted = true;
            return;
        }

        isSorted = true;  // Assume sorted until proven otherwise
        Node<T> current = head; //starts from head

        while (current.getNext() != null) { // iterates through whole list
            if (current.getData().compareTo(current.getNext().getData()) > 0) {
                //compares current and next node, if current node is bigger, unsorted
                isSorted = false;
                break; //terminated since found
            }
            current = current.getNext(); // iterates to next node
        }
    }


    public void reverse() {
        if (head != null && head.getNext() != null) { //Case 1: lists is longer than one element
            Node<T> prev = null; // previous starts out null
            Node<T> curr = this.head; // starts from head

            Node<T> next;
            tail = head; // tail pointer points to head, since we are reversing list
            while (curr != null){ // iterates until last element
                next = curr.getNext(); // next is element after current node
                curr.setNext(prev); // current node points to previous node
                prev = curr; // then previous node points to current node
                curr = next; // current node points to next node
            }

            head = prev; //at the end, previous node will be the head of the reversed linked list
            checkSorted(); // makes sure it is sorted
        } // Case 2: lists is one element or less, no need to reverse
    }

    public void removeDuplicates() {
        if (head == null || head.getNext() == null) {
            return; // Empty list or single-node list, nothing to do
        }

        Node<T> curNode = head; // Start at head
        Node<T> prevNode = null;
        while (curNode != null) { // iterates through list
            prevNode = curNode; // Previous node for tracking
            Node<T> cNode = curNode.getNext(); // node to compare with curNode, start at one after curNode

            while (cNode != null) { // iterates through list with cNode
                if (curNode.getData().compareTo(cNode.getData()) == 0) { //Case 1: it is a duplicate
                    prevNode.setNext(cNode.getNext()); //previous node points to node after cNode
                    cNode = prevNode.getNext(); // Move to the next node
                    size --; // reduces size by 1
                } else { // Case 2: different Node
                    prevNode = cNode; // iterates to next node
                    cNode = cNode.getNext(); //iterates to next node
                }
            }
            curNode = curNode.getNext(); // Move to the next unique node
        }
        tail = prevNode; // sets tail to last node
        checkSorted(); // checks to see if lists is sorted
    }


    public void intersect(List<T> otherList){
        if (otherList == null) { //empty list, nothing to merge
            return;
        }

        LinkedList<T> other = (LinkedList<T>) otherList; // type casts other into linkedlist

        // Sort and deduplicate both lists
        sort();
        removeDuplicates();
        other.sort();
        other.removeDuplicates();

        Node<T> cNode = head; //start from head
        Node<T> prevNode = null; // use previous node to insert node between current and previous node
        Node<T> otherCNode = other.head; // other list also start from head

        while (cNode != null && otherCNode != null) { // continues iterating until reaching the end for one list
            int comparison = cNode.getData().compareTo(otherCNode.getData()); // int to keep track of compareTo value

            if (comparison < 0) {
                // cNode is not in other list, remove it
                if (prevNode == null) { // Case 1: if currently at head
                    head = cNode.getNext();
                    cNode.setNext(null);
                    cNode = head;
                } else { //Case 2: in the middle of the list/last element
                    prevNode.setNext(cNode.getNext());
                    cNode.setNext(null);
                    cNode = prevNode.getNext();
                }
                size--; //makes size one smaller
            } else if (comparison > 0) {
                // Advance other list
                otherCNode = otherCNode.getNext();
            } else {
                // Match found, keep cNode
                prevNode = cNode;
                cNode = cNode.getNext();
                otherCNode = otherCNode.getNext();
            }
        }

        // Remove remaining unmatched nodes from this list
        // don't care about other list, because we are changing only this list
        while (cNode != null) {
            if (prevNode == null) { // Case 1: no match
                head = null;
                tail = null;
                size = 0;
                return; // list is now empty
            } else { // Case 2: at least one match
                //iterates through each of the remaining nodes to calculate size
                prevNode.setNext(cNode.getNext());
                cNode.setNext(null);
                cNode = prevNode.getNext();
                size--;
            }
        }

        tail = prevNode; // updates tail
        isSorted = true; // is sorted
    }

    public void merge(List<T> otherList) {
        if (otherList.isEmpty()) { // if other list is empty, don't merge
            return;
        }

        LinkedList<T> other = (LinkedList<T>) otherList; // type casts into Linked List
        this.sort(); // Sorts list
        other.sort(); // Sorts  other list

        Node<T> thisCurr = head; // starts at head
        Node<T> otherCurr = other.head; // other lists starts at head
        Node<T> prev = null; // previous is null

        if (size == 0){ // Case 1: empty this list
            head = otherCurr;
            tail = other.tail; // tail is other list's tail
        } else { //Case 2: not empty this.list
            if (otherCurr.getData().compareTo(thisCurr.getData()) < 0) {
                // if still at head for this list and other current Node is smaller than current Node
                //change head
                head = otherCurr; // head = other Curr
                otherCurr = otherCurr.getNext(); //gets next node
                head.setNext(thisCurr); //head sets next to this list's current Node
                thisCurr = head; // starts from new head
            }

            while (thisCurr != null && otherCurr != null) {
                // since both lists are sorted and no need to change head, iterates until the end of one of the lists
                if (thisCurr.getData().compareTo(otherCurr.getData()) <= 0) {
                    // Case 1: current node is smaller/equal to other node
                    prev = thisCurr; // iterates to next element
                    thisCurr = thisCurr.getNext(); //iterates to next element
                } else { // Case 2: current node is bigger than other node
                    Node<T> nextOther = otherCurr.getNext(); // marks next node for other list
                    // inserts other list's node in between previous and current node
                    prev.setNext(otherCurr);
                    otherCurr.setNext(thisCurr);
                    //iterates to next element
                    prev = otherCurr;
                    otherCurr = nextOther;
                }
            }

            // Case 1: still items in other list
            if (otherCurr != null ) {
                prev.setNext(otherCurr); // links up to the other list
                tail = other.tail; //tail is other list's tail
            } // Case 2: still items in original list, doesn't matter, tail stays the same
        }
        isSorted = true; // is sorted
        size = size() + other.size(); // size is just the two combined
        other.clear(); // Remove all references from otherList
    }


    public T getMin() {
        if (size == 0) { // Case 1: Lists is empty
            return null;
        } else if (isSorted) { // Case 2: list is sorted, if so get the first element
            return head.getData(); //returns the data
        } else { // Case 3: List is not sorted and not empty, checks list for smallest value
            T value = head.getData(); //starts at head
            Node<T> currNode = this.head.getNext(); // points to node after head

            for(int i = 1; i < this.size; ++i) { // iterates through list
                if (value.compareTo(currNode.getData()) > 0) {
                    // compares data in node to data in value, updates it if value in node is smaller than value
                    value = currNode.getData();
                }

                currNode = currNode.getNext(); // goes to next node
            }

            return value; // returns value
        }
    }

    public T getMax() {
        if (size == 0) { // Case 1: lists is empty
            return null;
        } else if (isSorted) { //Case 2: lists is sorted, gets the last element
            return tail.getData(); //returns the data
        } else { //Case 3: lists is not sorted and is not empty, checks lists for largest value
            T value = head.getData(); // starts at head
            Node<T> currNode = head.getNext(); // points to node after head

            for(int i = 1; i < size; ++i) { // iterates through list
                if (value.compareTo(currNode.getData()) < 0) {
                    // if data at node is bigger than value stored in value, updates value
                    value = currNode.getData();
                }
                currNode = currNode.getNext(); // gets next Node
            }

            return value; // returns value
        }
    }

    public String toString() {
        String s = ""; // string to return
        Node<T> cNode = this.head; // starts from head

        for(int i = 0; i < this.size; ++i) {
            //iterates through each node and adds the data into String, newLine every new element
            s = s + String.valueOf(cNode.getData());
            s = s + "\n";
            cNode = cNode.getNext();
        }

        return s;
    }

    public boolean isSorted() {
        return this.isSorted; // returns isSorted
    }

}