import java.util.*;
public class Email{
    public static void main(String[] args){
        String[] arr = new String[]{"leet.code@gmail.com", "leetcode@gmail.com", "leet+code@gmail.com", "l33t.c0de@gmail.com", "l.3.3.t..c0de@gmail.com", "l33tc0de+@gmail.com", "l33tc0de@gm.ail.com"};
        System.out.println(solution(arr));
    }

    public static int solution(String[] L){
        Map<String, Integer> map = new HashMap<String, Integer>();

        for(String str : L){
            StringBuilder builder = new StringBuilder();
            int indexOfAt = str.indexOf('@');

            for(int i = 0; i < indexOfAt; i++){
                char curr = str.charAt(i);
                if(curr == '.')
                    continue;
                if(curr == '+')
                    break;
                builder.append(curr);
            }
            String name = builder.toString();
            String domain = str.substring(indexOfAt);
            String email = name+domain;

            map.put(email, map.getOrDefault(email,0)+1);
        }

        int localMax = 0;
        int globalMax = 0;

        for(Map.Entry<String, Integer> e : map.entrySet()){
            localMax = e.getValue();
            globalMax = Math.max(globalMax, localMax);
        }

        return globalMax;
    }
}
