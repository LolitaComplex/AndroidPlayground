import java.util.ArrayList;

class MaxPQ<T extends Comparable<T>>{

    public static void main(String[] args) {
        final int M = 20;
        //创建长度为M + 1的优先队列
        MaxPQ<Integer> pq = new MaxPQ<>(M);
        for (int x = 0; x < 20; x++){
            pq.insert(x + 1);
        }
        
        ArrayList<Integer> list = new ArrayList<>();
        while (!pq.isEmpty())
            list.add(pq.delMax());
    
        System.out.println(list);
    }

    private T[] pq;//基于堆的完全二叉树
    private int N = 0;

    public MaxPQ(int maxN){
        pq = (T[]) new Comparable[maxN + 1];//存储于 1-N区间中，0没有使用
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public void insert(T value){
        pq[++N] = value;
        swim(N);//上浮到正确位置
    }

    public T delMax(){
        T max = pq[1];
        exch(1, N);//交换1与堆尾元素
        pq[N--] = null;//删除元素
        sink(1);//下沉到正确位置
        return max;
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        T t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    private void swim(int k){
        while (k > 1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;        
        }
    }

    private void sink(int k){
        while (2*k <= N){
            int j = 2*k;
            if (j < N && less(j, j + 1))
                j++;//判断子节点谁更大
            if (!less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

}