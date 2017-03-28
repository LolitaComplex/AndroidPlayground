class Sqrt{

    public static void main(String[] args) {

        System.out.println(sqrt(4));
    }

    /**
     * 计算平方根：（牛顿迭代法）
     */
    public static double sqrt(double num) {
        if(num < 0){
            return Double.NaN;
        }
        double err = 1e-15;
        double t = num;
        while(Math.abs(t - num/t) > err * t){
            t = (num/t + t) / 2.0;
        }

        return t;   
    }
}