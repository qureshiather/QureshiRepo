import java.util.Scanner;

//This class returns the fibonnaci sequence to a given sequence number
//The two methods below show different ways of computing the number
public class fib {

	public static void main(String[] args) {
		int inputInt;
		System.out.println("Enter Integer: ");
		Scanner scan = new Scanner(System.in);
		inputInt = scan.nextInt();
		scan.close();
		System.out.println(recfib(inputInt));
		System.out.println(iterfib(inputInt));
	}

	// 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
	// RECURSIVE VERSION
	private static int recfib(int n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else {
			return recfib(n - 2) + recfib(n - 1);
		}
	}
	
	//ITERATIVE VERSION
	private static int iterfib(int n) {
		int x = 0, y = 1, z = 1;
		for (int i = 0; i < n; i++) {
			x = y;
			y = z;
			z = x + y;
		}
		return x;
	}

}
