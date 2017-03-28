public interface Bag<Item> extends Iterable<Item>{

    void add(Item item);

    boolean isEmpty();

    int size();
}

public interface Queue<Item> extends Iterable<Item>{

    void enqueue(Item item);

    Item dequeue();

    boolean isEmpty();

    int size();
}

public interface Stack<Item> extends Iterable<Item>{

    void push(Item item);

    Item pop();

    boolean isEmpty();

    int size();
}public interface Stack<Item> extends Iterable<Item>{

    void push(Item item);

    Item pop();

    boolean isEmpty();

    int size();
}