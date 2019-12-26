package Warmup;
import java.util.Scanner;
import java.util.*;
import java.lang.StringBuilder;

public class ProblemB{
    static int i = 0;
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        if(n < 6){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < n; i++)
                sb.append("-");
            System.out.println(0);
            System.out.println(sb);
            System.out.println(sb);
            System.out.println(sb);
        } else if (n == 8) {
            System.out.println("3");
            System.out.println("WWWFFC--");
            System.out.println("WWWFFC--");
            System.out.println("WWWFFC--");
        } else if (n == 9) {
            System.out.println("4");
            System.out.println("WWWWWWFF-");
            System.out.println("WWWFFFFC-");
            System.out.println("WWWFFCC-C");
        } else {
            int washes, fills, close;
            washes = fills = close = n / 2;
            int idles = (3 * n) - (washes * 3) - (fills * 2) - close;

            StringBuilder[] grandChildren = new StringBuilder[3];
            for (int i = 0; i < 3; i++) {
                grandChildren[i] = new StringBuilder();
            }
            addAction("Wash", washes, grandChildren);
            addAction("Fill", fills, grandChildren);
            addAction("Close", close, grandChildren);
            addAction("Idle", idles, grandChildren);

            System.out.println(n / 2);
            System.out.println(grandChildren[0]);
            System.out.println(grandChildren[1]);
            System.out.println(grandChildren[2]);
        }
    }

    static void addAction(String actionType, int action, StringBuilder[] grandChildren){
        String actionOutput = "";
        switch(actionType){
            case "Wash": actionOutput = "WWW"; break;
            case "Fill": actionOutput = "FF"; break;
            case "Close": actionOutput = "C"; break;
            default: actionOutput = "-";
        }

        while (action != 0){
            int diff1 =  grandChildren[i].length() - grandChildren[(i+1)%3].length();
            int diff2 =  grandChildren[i].length() - grandChildren[(i+2)%3].length();
            if(diff1 > 0 || diff2 > 0) {
                i = (i + 1) % 3;
                continue;
            }

            grandChildren[i].append(actionOutput);
            i = (i+1) % 3;
            action--;
        }
    }
}