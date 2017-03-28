public class gcd{

    public static void main(String[] args){
        int p = 1314;
        int q = 51;
        System.out.println(q + "与" + p + "的最大公约数是" + gcd(p, q));
    }

    /**
     * 欧几里得算法：求最大公约数
     */
    public static int gcd(int p, int q){
        if (q == 0){
            return p;
        }
        int r = p % q;
        //111 % 63 --> 63 % 48 --> 48 % 15 --> 15 % 3 -- > return 3
        return gcd(q, r);
    }
}