import java.util.Scanner;
public class ProblemA{
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		boolean flag = true;
		String line = "";
		do{
			line = in.nextLine();
			String[] split = line.split(" ");
			int a = Integer.parseInt(split[0]);
			int b = Integer.parseInt(split[1]);
			int c = a+b;
			if(c != 0)
				System.out.println(c);
		}while(!line.equals("0 0"));

		in.close();
	}
}

