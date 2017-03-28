import java.util.Iterator;

/**
 * 基于链表实现的栈结构
 */
class Stack<T> implements Iterable<T>{

    private Node first;
    private int N;
    private class Node{
        T item;
        Node next;
    }

    public boolean isEmpty(){ return first == null; }
    public int size(){ return N; }
    public Stack push(T item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
        return this;
    }
    public T pop(){
        if(N == 0){
            return null;
        }

        T item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Iterator<T> iterator(){
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T>{

        private Node current = first;
        public boolean hasNext(){ return current.next != null; }
        public T next(){ 
            T item = current.item;
            current = current.next;
            return item;
        }
        public void remove(){}
    }
}