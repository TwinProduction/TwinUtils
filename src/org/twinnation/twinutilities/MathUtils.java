package org.twinnation.twinutilities;

import org.twinnation.twinutilities.exceptions.InvalidPrimePositionException;

/**
 * Math-related utility class
 */
public final class MathUtils {
	
	/** Prevents instantiation of this utility class */
	private MathUtils() {}

	
	/**
	 * Checks if the number passed as argument is prime
	 * @param n Number to check
	 * @return Whether the number is prime or not
	 */
	public static boolean isPrime(int n) {
		if (n <= 1) {
			return false;
		} else if (n == 2) {
			return true;
		}
		int limit = (int)Math.sqrt(n);
		for (int i = 2; i <= limit; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Finds the value of the nth prime
	 * @param nthPrime Position of the prime to find
	 * @return value of the nth prime
	 * @throws PrimeCannotBeNegativeException 
	 */
	public static int getPrimeAtPosition(int nthPrime) throws InvalidPrimePositionException { 
		if (nthPrime < 1) {
			throw new InvalidPrimePositionException();
		}
		// TODO: throw exception if pass invalid number
		int primeCounter = 1, result = 0;
		for (int i = 0; primeCounter <= nthPrime; i++) {
			if (isPrime(i)) { 
				if (primeCounter == nthPrime) {
					result = i;
					break;
				}
				primeCounter++;
			}
		}
		return result;
	}
	
	
	/**
	 * Checks if the number n is odd
	 * @param n Number to check
	 * @return Whether the number is odd
	 */
	public static boolean isOdd(int n) {
		return ((n & 1) == 1);
	}
	
	
	/**
	 * Checks if the number n is even
	 * @param n Number to check
	 * @return Whether the number is even
	 */
	public static boolean isEven(int n) {
		return !isOdd(n);
	}
}
