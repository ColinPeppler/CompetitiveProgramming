import java.util.*;
public class ProblemA{
    /*
        Sequential Search
        find 1st number 1
        mark it 0
        find 2nd number 1
        mark it 0
        find 1st number 2
        mark it 0
        find 2nd number 2 
        mark it 0

        iterate through 1-n 2 times
        once number i has been seen
        mark it 0 so second iteration
        doesn't repeate it
        * hard because how can i tell when to move forward or backwards

        another array that maps to the index of the

        a_i = tier @ ith house

        PROBLEM:
        *When to go forward or backwards?
        *How to avoid mapping two entries to the same digit; make it 1 to 1

        Sequential Search Approach
        - generally goes left -> right through the array
        O(n^2)

        Table for the closest following digit
        - closest following digit could be the same for 2 entries 
        O(n)

        HashMap Approach
        - map each number to an int[]
        - the int[] stores 2 numbers int[0] = 1st occurence of digit, int[1] = 2nd occurence of digit
        - travel through each number, add up all distances between int[0] then add
        all distances between int[1]
    */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        Map<Integer, int[]> map = new HashMap<Integer, int[]>();

        for(int i = 1; i <= n; i++){
            int[] arr = new int[2];
            Arrays.fill(arr, -1);
            map.put(i, arr);
        }


        int[] arr = new int[n];
        for(int i = 0; i < 2*n; i++){
            int curr = in.nextInt();   
            if(map.get(curr)[0] == -1){
                map.get(curr)[0] = i; 
            } else {
                map.get(curr)[1] = i;
            }
        }

        long summation = 0;
        int x_prev = 0;
        int y_prev = 0;

        // O(N)
        for(int i = 1; i <=n; i++){
            int x_curr = map.get(i)[0];
            int y_curr = map.get(i)[1];
            int distance1 = Math.abs(x_prev-x_curr) + Math.abs(y_prev-y_curr);
            int distance2 = Math.abs(x_prev-y_curr) + Math.abs(y_prev-x_curr);

            summation += Math.min(distance1, distance2);

            x_prev = x_curr;
            y_prev = y_curr;
        }

        // O(2N)
        /*for(int k = 0; k < 2; k++){
            int prevIndex = 0;
            for(int i = 1; i <= n; i++){
                int curr = map.get(i)[k];
                sum += Math.abs(prevIndex - curr);
                prevIndex = curr;
            }
        }*/

        System.out.println(summation);
    }
}
