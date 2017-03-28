import java.util.Stack;

class Evaluate{

    public static void main(String[] args) {
        System.out.println(eval("((1 + (3 / 4)) + 1)"));
    }
    
    /**
     * 表达式解释器：还有很多地方需要完善
     * 问题：
     *      1.只能计算各位加减乘除法
     */
    public static double eval(String expression){
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        StringBuilder sbExpresstion = new StringBuilder(expression);
        
        a:for (int x = 0; x < sbExpresstion.length(); x++){
            char operat = sbExpresstion.charAt(x);
            System.out.println("符号栈" + ops);
            System.out.println("值栈" + vals);
            switch (operat) {
                case ' ':
                    continue a;
                case '(':
                    continue a;
                case '+':
                    ops.push(operat + "");
                    break;
                case '-':
                    ops.push(operat + "");
                    break;
                case '*':
                    ops.push(operat + "");
                    break;
                case '/':
                    ops.push(operat + "");
                    break;
                case ')':{
                    String op = ops.pop();
                    double value = vals.pop();
                    switch (op) {
                        case "+":
                            value += vals.pop();
                            break;
                        case "-":
                            value = vals.pop() - value;
                            break;
                        case "*":
                            value *= vals.pop();
                            break;
                        case "/":
                            value = vals.pop() / value;
                            break;
                    }
                    vals.push(value);
                    break;
                }
                default :
                    vals.push(Double.parseDouble(operat + ""));
                    break;
            }
        }
        return vals.pop();
    }

}