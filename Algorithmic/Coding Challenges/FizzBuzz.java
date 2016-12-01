
public class FizzBuzz {
	
	public static void main(String args[]){
		//print out numbers 1-50
		//any multiple of 3, print fizz
		//any multiple of 5, print buzz
		//any multiple of 3 and 5, print fizzbuzz
		for(int i = 1; i <= 50; i++){
			if(i% (3*5) == 0)
				System.out.print("fizzbuzz ");
			else if (i%3 == 0)
				System.out.print("fizz ");
			else if (i%5 == 0)
				System.out.print("buzz ");
			else
				System.out.print(i + " ");
		}
	}

}
