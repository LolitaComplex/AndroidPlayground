import static utils.SelectionUtils.*;

class Insertion{

    private static int lessIndex = 0;

    public static void main(String[] args) {
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        sort(arr);
        show(arr);
    }

    public static void sort(Comparable[] arr){
        final int N = arr.length;
        for (int x = 1; x < N; x++){
            for (int y = x; isLess(arr, y) ; y--){
                exch(arr, y, y-1);
            }
        }
    }

    private static boolean isLess(Comparable[] arr, int y){
        if (y <= 0){
            return false;
        }
        System.out.println(++lessIndex + "\t["+ arr[y] + ", "+ arr[y-1] +"]");
        return less(arr[y], arr[y-1]);
    }
}


-348796917