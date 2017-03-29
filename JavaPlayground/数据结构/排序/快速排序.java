import static utils.SelectionUtils.*;

class Quick{
    public static void main(String[] args) {
        Integer[] arr = {3,6,1,41,5,32,52,63,63,3};
        show(arr);
        quick(arr);
        show(arr);
    }

    public static void quick(Comparable[] arr){
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(Comparable[] arr, int lo, int hi){
        if (hi <= lo)
            return;
        int j = partition(arr, lo, hi);
        sort(arr, lo, j-1);
        sort(arr, j+1, hi);
    }

    private static int partition(Comparable[] arr, int lo, int hi){
        int i = lo, j = hi + 1;
        Comparable v = arr[lo];
        while (true){
            //当判断到左边某元素比第一个元素大时
            while (less(arr[++i], v))
                //已经到达边界
                if (i == hi)
                    break;
            //当判断到右边某元素比第一个元素小时
            while (less(v, arr[--j]))
                if(j == lo)
                    break;
            //当i与j相遇时跳出循环
            if (i >= j)
                break;
            //交换左边区间中比第一个元素大的元素与右边区间中比第一个元素小的元素
            exch(arr, i, j);
        }
        //把第一个元素放到切分元素这个位置
        exch(arr, lo, j);
        return j;
    }
}