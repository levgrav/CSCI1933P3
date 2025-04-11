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
        this.isSorted = false;
        this.a = a;
    }
    
    public boolean add(T element) {
        if (size >= maxSize) {
            grow();
        }

        this.a[size] = element;
        
        if (size != 0 && this.a[size].compareTo(this.a[size - 1]) < 0) {
            this.isSorted = false;
        }
        
        size ++;
        return true;
    }
    
    public boolean add(int index, T element) {
        if (size >= maxSize) {
            grow();
        }

        if (index < 0 || index > this.size)
            return false;

        for (int i = size; i > index; i--) {
            this.a[i] = this.a[i-1];
        }

        this.a[index] = element;

        if (size != 0 && (
            this.a[index].compareTo(this.a[index - 1]) < 0 
            || (index != this.size 
            && this.a[index].compareTo(this.a[index + 1]) > 0))) {
            this.isSorted = false;
        }
        
        size ++;
        return true;

    }

    public void grow() {
        this.maxSize *= 2;
        T[] a = (T[]) new Comparable[maxSize];
        for (int i = 0; i < size; i++) {
            a[i] = this.a[i];
        }
        this.a = a;
    }
    
    public void clear() {
        this.maxSize = 2;
        this.size = 0;
        this.isSorted = true;
        this.a = (T[]) new Comparable[maxSize];
    }
    
    public T get(int index) {
        return this.a[index];
    }
    
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (this.a[i] == element)
                return i;
        }
        return -1;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
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
    
    public void reverse() { // Note: You must reverse the list IN-PLACE (no intermediate data structures). 
        for (int i = 0; i < size / 2; i++) {
            T temp = this.a[i];
            this.a[i] = this.a[size - 1 - i];
            this.a[size - 1 - i] = temp;
        }
        this.isSorted = false;
    }

    public void removeDuplicates() {
        for (int i = 0; i < this.size - 1; i++) {
            for (int j = i + 1; j < this.size; j++) {
                if (this.a[i] == this.a[j])
                    this.remove(j);
            }
        }
    }
    
    public void intersect(List<T> otherList) {
        this.removeDuplicates();
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
    
    public void merge(List<T> list) {
        while (list.size() + this.size > maxSize) {
            grow();
        }
        for (int i = 0; i < list.size(); i++) {
            this.a[i + this.size] = list.get(i);
        }

        if (list.size() != 0 || list.get(0).compareTo(this.a[this.size - 1]) < 0 || !list.isSorted(true))
            this.isSorted = false;

        this.size += list.size();
    }
    
    public T getMin() {
        if (this.isSorted) {
            return this.a[0];
        } 

        T min = this.a[0];
        for (int i = 1; i < this.size; i++) {
            if (min.compareTo(this.a[i]) > 0)
                min = this.a[i];
        }
        return min;
    }
    
    public T getMax() {
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
    
    public String toString() {
        String out = "[";
        for (int i = 0; i < this.size; i++) {
            out += this.a[i] + ", ";
        }
        out += "\b\b]";
        return out;
    }

    public boolean isSorted() {
        return isSorted;
    }
    
    public boolean isSorted(boolean check) {
        if(this.isSorted)
            return true;
        
        else if(check) {
            for (int i = 0; i < this.size - 1; i++) {
                if(this.a[i].compareTo(this.a[i + 1]) > 0)
                    return false;
            }

            this.isSorted = true;
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        // ArrayList tests. working properly
        // Integer[] data = {1, 2, 3, 4};
        // ArrayList<Integer> a = new ArrayList<Integer>(data);
        // System.out.println(a);
        // System.out.println(a.isSorted());
        // System.out.println(a.isSorted(true));
        // a.reverse();
        // System.out.println(a);
        // System.out.println(a.isSorted(true));
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
