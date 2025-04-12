//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package CSCI1933P3;

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private boolean isSorted = true;
    private int size = 0;

    public LinkedList() {
    }

    public boolean add(T element) {
        if (element == null) { // Make sure element is not null
            return false;
        } else {
            if (head == null) { // Case 1: first element in list
                head = new Node(element);
                tail = head;
            } else { //Case 2: adds to the end of the list
                //goes until end of list
                Node<T> curNode = head;
                while (curNode.getNext() != null) {
                    curNode = curNode.getNext();
                }

                curNode.setNext(new Node(element)); // makes a new node and sets pointer to that node
                if (curNode.getData().compareTo(element) > 0) {
                    // checks if that element is smaller than the element before it
                    // if it is, then sets isSorted to false
                    // Since if isSorted was true, then this would make it false
                    //if isSorted was already false, it will stay false whether
                    // this new addition is larger or smaller than previous one
                    isSorted = false;
                }

                tail = curNode.getNext(); // tail becomes the added node
            }

            size ++; // size increases by 1
            return true;
        }
    }

    public boolean add(int index, T element) {
        if (size == 0 && index >= 0) { // Case 1: new Linked List/ first element
            head = new Node(element); // head becomes new node, connects to former head
            tail = this.head;
        } else if (index == 0) { // Case 2: add before the head
            head = new Node<T>(element, head); // makes new node, head pointer points to head
        } else { // Case 3: somewhere in between/ at the end
            if (index <= 0 || index >= this.size) { // checks to make sure parameter are right
                return false;
            }

            Node<T> cNode = this.head; // starts from head

            for(int i = 0; i < index - 1; ++i) { // gets to the once before the index
                cNode = cNode.getNext();
            }

            Node<T> insertNode = new Node<T>(element, cNode.getNext()); // inserts node at index
            cNode.setNext(insertNode);
            size ++; // increases size by 1
            if (cNode.getData().compareTo(element) > 0 || element.compareTo(insertNode.getNext().getData()) > 0) {
                // checks if that element is smaller than the element before it and
                // if element is larger than element after it
                // if either is true then sets isSorted to false
                // Since if isSorted was true, then this would make it false
                // if isSorted was already false, it will stay false whether
                // this new addition is larger or smaller than previous one
                this.isSorted = false;
            }

            if (insertNode.getNext() == null) { // if insertNode is last element, becomes tail
                this.tail = cNode.getNext();
            }
        }

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
        Node<T> cNode = this.head; // starts at head pointer
        if (index < this.size && index >= 0) { // checks to make sure parameters are correct
            for(int i = 0; i < index; ++i) { // goes to the node at index
                cNode = cNode.getNext();
            }

            return cNode.getData(); //returns the data from that node
        } else {
            return null; // otherwise returns null if parameters are wrong
        }
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
                // if went through list and at a bigger value than element when list is sorted = does not exists
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

        } else { // Case 2: only 1 element
            this.isSorted = true;
        }
    }

    public T remove(int index) {
        if (index >= 0 && index < this.size && this.head != null) {
            // checks parameters and makes sure list is not empty
            Node<T> currNode = this.head; // starts from head
            Node<T> prevNode = null; // previous is null at the beginning
            Node<T> remNode = null; // pointer to node to be removed, empty at the beginning
            if (index == 0) { // Case 1: removes head
                remNode = this.head; // removed node points to head node
                head = head.getNext(); // sets head to next element
                if (head == null) { // if list has only 1 element, list becomes empty
                    tail = null; // sets tail to null
                }
            } else if (index == this.size - 1) { // Case 2: remove tail
                while (currNode.getNext().getNext() != null){
                    //iterates through list to element before last element
                    currNode = currNode.getNext();
                }
                remNode = tail; // removed node points to tail
                currNode.setNext(null); // current node points to nothing (end of list)
                this.tail = currNode; // sets tail to current node
            } else { // Case 3, remove element in the middle somewhere in list
                for(int i = 0; i < index; ++i) {
                    // iterates both previous node and current node to that index
                    prevNode = currNode;
                    currNode = currNode.getNext();
                }

                remNode = currNode; // removed node points to current node
                remNode.setNext(null); // completely removes association to linked list
                prevNode.setNext(currNode.getNext()); // previous node points to node after removed node
            }

            size --; // size is one smaller now
            if (!this.isSorted) {
                // if it is not sorted, checks to see if removal made it sorted
                //if it was sorted, removal will not affect it
                this.checkSorted(); // calls helper function to check if lists is sorted
            }

            return remNode.getData(); // returns the removed node data
        } else {
            return null; // if parameters were wrong/ lists was empty, returns null
        }
    }

    //helper function to check if list is sorted
    private void checkSorted(){
        Node<T> curr = this.head; // starts from head

        isSorted = true; //sets is sorted to true
        while (curr != null && curr.getNext() != null && isSorted) {
            // iterates through the whole linked list, ends when reaching the end or list is not sorted
            if (curr.getData().compareTo(curr.getNext().getData()) > 0) { // if current element is bigger than next
                this.isSorted = false; // sets is sorted to false
            }
            curr = curr.getNext(); // gets next Node
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
            if (isSorted){ // if list was sorted before, reversed lists is definetly not sorted
                isSorted = false;
            }else { // if list is unSorted before, list could be sorted after reversal
                checkSorted(); // uses helper function to check
            }
        } // Case 2: lists is one element or less, no need to reverse
    }

    public void removeDuplicates() {
        if (head == null || head.getNext() == null) {
            return; // Empty list or single-node list, nothing to do
        }

        Node<T> curNode = head; // Start at head

        while (curNode != null) { // iterates through list
            Node<T> prevNode = curNode; // Previous node for tracking
            Node<T> cNode = curNode.getNext(); // node to compare with curNode, start at one after curNode

            while (cNode != null) { // iterates through list with cNode
                if (curNode.getData().compareTo(cNode.getData()) == 0) { //Case 1: it  is a duplicate
                    prevNode.setNext(cNode.getNext()); //previous node points to node after cNode
                    cNode.setNext(null); // completely removes duplicate from list
                    cNode = prevNode.getNext(); // Move to the next node
                    size --; // reduces size by 1
                } else { // Case 2: different Node
                    prevNode = cNode; // iterates to next node
                    cNode = cNode.getNext(); //iterates to next node
                }
            }
            curNode = curNode.getNext(); // Move to the next unique node
        }

        checkSorted(); // checks to see if lists is sorted
        for(Node<T> cNode = this.head; cNode != null; cNode = cNode.getNext()) { // finds tail
            if (cNode.getNext() == null) {
                tail = cNode; //sets tail
            }
        }
    }


    public void intersect(List<T> otherList) {
        if (!otherList.isEmpty()) { // Case 1: Other lists is not empty
            LinkedList<T> other = (LinkedList)otherList;
            this.sort();
            other.sort();
            Node<T> dummy = new Node((Comparable)null);
            Node<T> t = dummy;
            Node<T> cNode = this.head;
            Node<T> otherCNode = other.head;

            while(cNode != null && otherCNode != null) {
                int comparison = cNode.getData().compareTo(otherCNode.getData());
                if (comparison != 0) {
                    if (comparison < 0) {
                        cNode = cNode.getNext();
                    } else {
                        otherCNode = otherCNode.getNext();
                    }
                } else {
                    if (t == dummy || !t.getData().equals(cNode.getData())) {
                        t.setNext(new Node(cNode.getData()));
                        t = t.getNext();
                    }

                    cNode = cNode.getNext();
                    otherCNode = otherCNode.getNext();
                }
            }

            t.setNext((Node)null);
            this.head = dummy.getNext();
            this.tail = t;
            this.isSorted = true;
        } else {
            this.clear();
        }
    }

    public void merge(List<T> otherlist) {
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
        String s = "";
        Node<T> cNode = this.head;

        for(int i = 0; i < this.size; ++i) {
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
