import java.util.*;

class Reverse{

    public static void main(String[] args){
        int[] arr = {1,2,3,4,5,6};
        reverse(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 容器翻转
     */
    public static <T> void reverse(T[] arr){
        int length = arr.length;
        for (int x = 0; x < length / 2; x++){
            T temp = arr[x];
            arr[x] = arr[length - 1 - x];
            arr[length - 1 -x] = temp;
        }
    }
}