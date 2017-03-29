import static utils.SelectionUtils.*;

class MergeBU{

    private static Comparable[] aux;

    public static void main(String[] args){
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        sort(arr);
        show(arr);
    }

    public static void sort(Comparable[] arr){
        final int N = arr.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(arr, lo, lo + sz -1, Math.min(lo + sz + sz - 1, N - 1));
    }

    private static void merge(Comparable[] arr, int lo, int mid, int hi){
        int i = lo, j = mid + 1;
        for (int x = lo; x <= hi; x++){
            aux[x] = arr[x];
        }

        for (int x = lo; x <= hi; x++){
            if (i > mid)
                arr[x] = aux[j++];
            else if (j > hi)
                arr[x] = aux[i++];
            else if (less(aux[i], aux[j]))
                arr[x] = aux[i++];
            else 
                arr[x] = aux[j++];
        }
    }
}