package puzzles.basic;

public class OddNumberTesting {

	/** This function computes incorrect result
	 * for negative odd numbers.
	 */
	public static boolean isOdd1(int num) {
		return num % 2 == 1;
	}
	
	/** This function computes correct result for
	 * all numbers (negative and positive) numbers.
	 */
	public static boolean isOdd2(int num) {
		return num % 2 != 0;
	}
	
	/** 
	 * Correct and most optimized functions.
	 */
	public static boolean isOdd3(int num) {
		return (num & 1) != 0;
	}

	public static void testOdd(int num) {
		System.out.println("\nodd1 function: is number " + num + " odd? " + isOdd1(num));
		System.out.println("odd2 function: is number " + num + " odd? " + isOdd2(num));
		System.out.println("odd3 function: is number " + num + " odd? " + isOdd3(num));
	}

	public static void main(String[] args) {
		testOdd(11);
		testOdd(0);
		testOdd(1);
		testOdd(101);
		testOdd(-10);
		testOdd(-23);
		testOdd(-33);
		testOdd(-1);
	}
}
