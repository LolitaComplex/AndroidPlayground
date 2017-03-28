package utils;

import java.util.Arrays;

public class SelectionUtils{


    private SelectionUtils(){}

    public static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    public static <T> void exch(T[] arr, int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <T> void show(T[] arr){
        System.out.println(Arrays.toString(arr));
    }

    public static <T extends Comparable> boolean isSorted(T[] arr){
        for(int x = 1; x < arr.length; x++){
            if(less(arr[x], arr[x - 1])){
                return false;
            }
        }
        return true;
    }
}