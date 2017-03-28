import java.util.Arrays;

class FixedCapacityStack<T>{

    private T[] stack;
    private int N;

    public FixedCapacityStack(int size){
        stack = (T[])new Object[size];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public FixedCapacityStack push(T item){
        stack[N++] = item;
        return this;
    }

    public T pop(){
        return stack[--N];
    }



    public static void main(String[] args){
        FixedCapacityStack<String> stack =  new FixedCapacityStack<String>(10);
        stack.push("东方谷早苗")
            .push("八坂神奈子")
            .push("河城荷取")
            .push("秋静叶")
            .push("秋穰子")
            .push("键山雏")
            .push("射命丸文")
            .push("犬走椛")
            .push("泄矢诹访子");

        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.push("博丽灵梦").push("雾雨魔理沙").push("泄矢诹访子"));
    }
}