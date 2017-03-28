import java.util.*;

class Prime{

    public static void main(String[] args){
        List<Integer> list = new ArrayList<>();
        for(int x = 0; x< 101; x++){
            if(isPrime(x)){
                list.add(x);
            }
        }
        System.out.println(list);
    }

    /**
     * 质数（素数）：只要x到根号number之间任意数字都不能被number整除，那么这个number就是质数
     */
    public static boolean isPrime(int number){
        if (number < 2){
            return false;
        }
        for (int x = 2; x * x <= number; x++){
            if( number % x == 0){
                return false;
            }
        }
        return true;
    }
}