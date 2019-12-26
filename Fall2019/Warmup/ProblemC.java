package Warmup;
import java.util.Scanner;

public class ProblemC {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int numOfCoeff = in.nextInt();
        int[] coeff = new int[numOfCoeff];

        for(int i = 0; i < numOfCoeff; i++)
            coeff[i] = in.nextInt();

        for(int i = 2; i < Integer.MAX_VALUE; i++){
            for (int coefficient : coeff){
                if(i % coefficient == 0){
                    continue;
                }
                System.out.println(i);
                System.exit(0);
            }
        }
    }
}
