import java.util.Arrays;
import java.util.Iterator;

/**
 * 其实就是在定容栈基础上添加了3个功能
 * 1. 对pop的元素在数组中进行制null操作，防止内存浪费
 * 2. 对超出或者剩余空间太多时数组的自动增长或者缩短的操作
 * 3. 实现Iterable，使站具有可遍历性
 */
class ResizingArrayStack<T> implements Iterable<T> {

    private T[] stack;
    private int N;

    public ResizingArrayStack(){
        stack = (T[]) new Object[2];
    }

    public ResizingArrayStack(int size){
        stack = (T[])new Object[size];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public ResizingArrayStack push(T item){
        if (N == stack.length){
            resize(stack.length * 3 / 2);
        }
        // System.out.println("当前角标"+N + "数组长度" + stack.length);
        stack[N++] = item;
        return this;
    }

    public T pop(){
        if(N == 0){
            return null;
        }
        T item = stack[--N];
        stack[N] = null;//对象游离，制为null为了是垃圾回收机制回收该对象
        if (N > 0 && N == stack.length * 4 / 9){
            resize(stack.length * 2 / 3);
        }
        return item;
    }

    private void resize(int max){
        T[] temp = (T[]) new Object[max];
        System.arraycopy(stack, 0, temp, 0, N);
        stack = temp;
    }

    public Iterator<T> iterator(){
        return new ReverseArrayIterator();
    }

    public String toString(){
        return Arrays.toString(stack);
    }

    private class ReverseArrayIterator implements Iterator<T>{
        private int length = N;
        public boolean hasNext(){
            return length > 0;
        }

        public T next(){
            if(length == 0){
                // throw new NoSuchElementException();
            }
            return stack[--length];
        }

        public void remove(){
            // throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
        ResizingArrayStack<String> stack =  new ResizingArrayStack<String>();
        stack.push("东方谷早苗")
            .push("八坂神奈子")
            .push("河城荷取")
            .push("秋静叶")
            .push("秋穰子")
            .push("键山雏")
            .push("射命丸文")
            .push("犬走椛")
            .push("泄矢诹访子");

        // System.out.println(stack);
        // stack.pop();
        // System.out.println(stack);
        // stack.pop();
        // System.out.println(stack);
        
        // stack.pop();
        // stack.pop();
        // System.out.println(stack.pop());
        // System.out.println(stack);
        // System.out.println(stack.push("博丽灵梦").push("雾雨魔理沙").push("泄矢诹访子"));

        // for (String item : stack){
        //     System.out.print(item + "\t");
        // }
        System.out.println(stack);
        swap(stack);
        System.out.println(stack);
    }

    public static <T> void swap(ResizingArrayStack<T> stack){
        for(int x = 0; x < stack.size() ; x++){
            T t = stack.pop();
            get(stack, t, t, x);
        }           
    }

    private static <T> void get(ResizingArrayStack<T> stack, T pop ,T top, int index){
        if (stack.size() != index){
            get(stack, stack.pop(), top, index);
        } else{
            // System.out.println("top" + top);
            stack.push(top);
        }   
        
        if(!top.equals(pop)){
            // System.out.println("pop" + pop)
            stack.push(pop);
        }
    }
}