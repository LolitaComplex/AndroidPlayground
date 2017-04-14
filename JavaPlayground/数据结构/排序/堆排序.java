import static utils.SelectionUtils.*;

class PQSort{

    public static void main(String[] args) {
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        sort(arr);
        show(arr);
    }

    public static void sort(Comparable[] arr){
        int N = arr.length - 1;
        System.out.println("N=" + N + "\t");
        for (int k = N/2; k >= 1; k--){
            sink(arr, k, N);
            while (N > 1){
                exch(arr, 1, N--);
                sink(arr, 1, N);
            }
        }
    }

    private static void sink(Comparable[] arr, int k, int N){
         while (2*k <= N){
            int j = 2*k;
            System.out.print("k=" + k + "\t");
            if (j < N && less(arr[j], arr[j + 1]))
                j++;//判断子节点谁更大
            System.out.println("j=" + j);
            if (!less(arr[k], arr[j]))
                break;
            exch(arr, k, j);
            k = j;
        }
    }
}