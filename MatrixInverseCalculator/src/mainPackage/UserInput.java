package mainPackage;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Class UserInput. Provides static methods for user input.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since December 5th, 2014
 */
public class UserInput {

	/**
	 * Prompts the user for an integer.
	 *
	 * @param message
	 *            the message to display with the prompt
	 * @return the int
	 */
	public static int promptInteger(String message) {

		Scanner scanner = new Scanner(System.in);

		System.out.println(message);

		int n = 0;

		try {

			n = scanner.nextInt();

		} catch (InputMismatchException e) {

			System.out.println("Invalid input. Must enter an integer.");
			promptInteger(message);
		}

		return n;
	}
}
