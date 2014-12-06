package mainPackage;

import java.util.Scanner;

/**
 * The Class Main.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since December 5th, 2014
 */
public class Main {

	/**
	 * The main method. Finds the inverse of a matrix provided by the user.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		int n = UserInput
				.promptInteger("What is the dimension of the matrix A?");

		RowVector[] rowInput = new RowVector[n];

		System.out
				.println("Now enter the rows of A, with entries delimited by commas.");

		int i = 0;

		while (i < n) {

			System.out.println("Row " + i + ": ");
			String input = scanner.next();

			String coordsAsString[] = input.split(",");
			double coordsAsValues[] = new double[n];

			int j = 0;

			while (j < n) {

				try {

					coordsAsValues[j] = Double.parseDouble(coordsAsString[j]);

				} catch (NumberFormatException e) {

					System.out.println("Invalid matrix entries.");
					System.exit(1);
				}

				j++;
			}

			rowInput[i] = new RowVector(coordsAsValues);
			i++;
		}

		scanner.close();

		SquareMatrix userMatrix = new SquareMatrix(rowInput);

		System.out.println("Original Matrix: " + "\n" + userMatrix);

		SquareMatrix userInverse = userMatrix.getInverse();

		System.out.println("Inverse Matrix: " + "\n" + userInverse);

	}
}
