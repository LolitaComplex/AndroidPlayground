import static utils.SelectionUtils.*;

class Shell{

    public static void main(String[] args) {
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        sort(arr);
        show(arr);
    }

    /**
     * 思路：首先明确递增序列，比如变化值为[13, 4, 1]。
     * 1. 13表示会对 [13, 0]、[14, 1]、[15, 2]能达到的项进行排序
     * 2. 4表示会对[4, 0]、[5, 1]、[6, 2]、[7, 3]、[8, 4, 0]、[9, 5, 1]....[15, 11, 7, 3]进行依次排序
     * 3. 1表示接下来就是进行依次普通的插入排序。前边两次其实已经把数列调整为适合插入排序的序列，所以整体效率很好
     */
    public static void sort(Comparable[] arr){
        final int N = arr.length;
        int h = 1;
        while (h < N/3){
            h = 3 * h + 1;//递增序列
        }
        while (h >= 1){
            for (int x = h; x < N; x++){
                for (int y = x; y >= h && less(arr[y], arr[y-h]); y -=h){
                    exch(arr, y, y-h);
                }
            }
            h = h / 3;
        }
    }
}