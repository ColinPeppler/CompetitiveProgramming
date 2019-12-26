// Coin Problem
// If you ever get a stack overflow with a recursive method, think if you can do it iteratively
package Warmup;
import java.util.Scanner;

public class ProblemA{
    static int a, b, c;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();
        int n = in.nextInt();

        if(!iterative(n)){
            System.out.println("IMPOSSIBLE");
        }
    }

    public static boolean iterative(int n){
        for(int pC = 0; pC <= n/c; pC++){
            for(int pB = 0; pB <= (n - pC*c)/b; pB++){
                for(int pA = 0; pA <= (n - (pC*c + pB*b))/a; pA++){
                    if(pC*c + pB*b + pA*a == n) {
                        System.out.println(pA + " " + pB + " " + pC);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean recurse(int n, int pA, int pB, int pC){
        boolean flag = false;
        if(n < 0)
            return false;
        if(n == 0) {
            System.out.println(pA + " " + pB + " " + pC);
            return true;
        }
            flag = recurse(n-c, pA, pB, pC+1);

        if(!flag)
            flag = recurse(n-b, pA, pB+1, pC);

        if(!flag)
            flag = recurse(n-a, pA+1, pB, pC);

        return flag;
    }
}