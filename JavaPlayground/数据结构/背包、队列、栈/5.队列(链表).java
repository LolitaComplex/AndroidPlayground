import java.util.Iterator;

/**
 * 基于链表实现的队列结构
 */ 
class Queue<T> implements Iterable<T>{

    private Node first;
    private Node last;
    private int N;
    private class Node{
        T item;
        Node next;
    }

    public boolean isEmpty(){ return first == null; }

    public int size(){ return N; }

    public Queue enqueue(T item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()){
            first = last;//记住存入的第一个节点
        } else {
            oldLast.next = last;
        }
        N++;
        return this;
    }

    public T dequeue(){
        T item = first.item;
        first = first.next;//删除第一个节点
        if (isEmpty()){
            last = null;
        }
        N--;
        return item;
    }

    public Iterator<T> iterator(){
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T>{
        private Node current = first;

        public boolean hasNext(){ return current != null; }
        public T next(){
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.enqueue("秋静叶")
            .enqueue("秋穰子")
            .enqueue("键山雏")
            .enqueue("犬走椛")
            .enqueue("河城荷取")
            .enqueue("射命丸文")
            .enqueue("东方谷早苗")
            .enqueue("八坂神奈子")
            .enqueue("泄矢诹访子");

        
        for(String project : queue){
            System.out.print(project + "\t");
        }

        queue.dequeue();
        queue.dequeue();

        System.out.println();
        for(String project : queue){
            System.out.print(project + "\t");
        }
    }
}