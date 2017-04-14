import static utils.SelectionUtils.*;

class Selection{

    public static void main(String[] args) {
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        slection(arr);
        show(arr);
    }

    /**
     * 选择排序
     */
    private static void slection(Comparable[] arr){
        final int N = arr.length;
        for (int x = 0; x < N; x++){
            int min = x;
            for (int y = x + 1; y < N; y++){
                if(less(arr[y], arr[min])){
                    min = y ;
                }
            }
            exch(arr, x, min);
        }
    }
}