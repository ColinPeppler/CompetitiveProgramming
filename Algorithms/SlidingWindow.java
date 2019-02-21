import java.util.*;
public class SlidingWindow{
    public static void main(String[] args){
        int[] arr = new int[]{5, 3, 1, 4, 1, 2, 3, 4, 5, 7};
        System.out.println(solution(arr)); 
    }

    static int solution(int[] coupons){
        Set<Integer> set = new HashSet<Integer>();
        int i = 0;  
        int min = Integer.MAX_VALUE; // min number of coupons
        int count = 1; // local counter

        for(int j = 1; j < coupons.length; j++){
            int num = coupons[j];
            count++; 

            // if 
            if(set.contains(num)){
                // move i up
                while(i < j){
                    int curr = coupons[i];
                    if(curr == num)
                        break;
                    count--;
                    set.remove(curr);
                    i++;
                }
                min = Math.min(count, min);
            }
            else
                set.add(num);

        }

        // if no possible ans return -1
        if(min == Integer.MAX_VALUE)
            min = -1;

        return min;
    }
}
