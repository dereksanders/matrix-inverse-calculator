package mainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Class IOManager.
 * 
 * @author Derek Sanders
 * @version 1.0
 * @since January 29th, 2015
 */
public class IOManager {

	private static int MAX_ROWS = 200000;

	/**
	 * Convert a file of comma-separated values (with rows separated by newline
	 * characters) to a SquareMatrix.
	 *
	 * @param filename
	 *            the filename
	 * @return the square matrix
	 * @throws IllegalMatrixException
	 *             the illegal matrix exception
	 */
	public static SquareMatrix convertFileToMatrix(String filename)
			throws IllegalMatrixException {

		String line;
		int lineCount = 0;
		String[] textArray = new String[MAX_ROWS];

		Charset charset = Charset.forName("US-ASCII");
		Path file = Paths.get(filename);

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {

			do {

				line = reader.readLine();

				if (line != null) {

					textArray[lineCount] = line;
					lineCount++;
				}

			} while (line != null);

		} catch (IOException err) {

			System.out.println(err);
			System.out.println("Unable to read file " + filename);
			System.exit(0);
		}

		RowVector[] rows = new RowVector[lineCount];
		double[] curRowCoordinates = new double[lineCount];

		int i = 0;

		while (i < lineCount) {

			String[] curRow = textArray[i].split(",");

			if (curRow.length != lineCount) {

				throw new IllegalMatrixException("Error: Matrix is not square.");
			}

			int j = 0;

			for (String s : curRow) {

				curRowCoordinates[j] = Double.parseDouble(s);
				j++;
			}

			rows[i] = new RowVector(curRowCoordinates);
			i++;
		}

		SquareMatrix fileMatrix = new SquareMatrix(rows);

		return fileMatrix;
	}
}
