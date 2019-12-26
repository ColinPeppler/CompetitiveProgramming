import java.util.*;
public class ProblemA{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
	
		int n = in.nextInt();
		boolean[] values = new boolean[n];

		in.nextLine();
		String s = in.nextLine();
		String[] sArr = s.split(" ");
		for(int i = 0; i < n; i++){
			values[i] = sArr[i].equals("T") ? true : false;
		}

		String exp = in.nextLine();
		String[] expArr = exp.split(" ");

		Stack<Boolean> stack = new Stack<>();

		for(int i = 0; i < expArr.length; i++){
			char curr = expArr[i].charAt(0);
			if(curr != '*' && curr != '-' && curr != '+'){
				int val = curr - 'A';
				stack.push(values[val]);
			}
			if(curr == '-'){
				boolean last = stack.pop();
				stack.push(!last);
			}
			else if(curr == '*'){
				boolean op1 = stack.pop();
				boolean op2 = stack.pop();
				stack.push(op1 && op2);
			}
			else if(curr == '+'){
				boolean op1 = stack.pop();
				boolean op2 = stack.pop();
				stack.push(op1 || op2);
			}
		}

		boolean x = stack.pop();
		System.out.println(x ? 'T' : 'F');
	}
}
