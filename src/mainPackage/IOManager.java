package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;

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
	 * @param button
	 *            the button
	 * @return the square matrix
	 */

	public static SquareMatrix LoadFile(JButton button) {

		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(button);
		SquareMatrix fileMatrix = null;
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			try {
				fileMatrix = convertFileToMatrix(file.getPath());
				return fileMatrix;
			} catch (IllegalMatrixException e) {
				return null;
			}
		}

		return fileMatrix;
	}

	/**
	 * Export inverse as csv.
	 *
	 * @param matrix
	 *            the matrix
	 * @param filename
	 *            the filename
	 */
	public static void exportInverseAsCSV(SquareMatrix matrix, String filename) {

		SquareMatrix matrixInverse = matrix.getInverse();

		if (matrixInverse.equals(SquareMatrix
				.createIdentityMatrix(matrixInverse.getRows().length))) {

			RowVector[] rows = matrixInverse.getRows();
			String[] rowsText = new String[rows.length];
			String fileContents = "";

			int i = 0;
			while (i < rows.length) {

				rowsText[i] = rows[i].toString();
				i++;
			}

			int j = 0;
			while (j < rows.length) {

				fileContents += rowsText[j];

				if (j < rows.length - 1) {

					fileContents += "\n";
				}

				j++;
			}

			Writer writer = null;

			try {
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(filename + ".csv"), "utf-8"));
				writer.write(fileContents);
			} catch (IOException ex) {
				// report
			} finally {
				try {
					writer.close();
				} catch (Exception ex) {
				}
			}
		}

		else {

			System.out.println("This matrix is not invertible. Export failed.");
		}
	}

	/**
	 * Export as wolfram input.
	 *
	 * @param matrix
	 *            the matrix
	 * @param filename
	 *            the filename
	 */
	public static void ExportAsWolframInput(SquareMatrix matrix, String filename) {

		String fileContents = "";
		RowVector[] rows = matrix.getRows();

		fileContents += "{";

		int i = 0;
		while (i < rows.length) {

			fileContents += "{" + rows[i].toString() + "}";

			if (i < rows.length - 1) {

				fileContents += ",";
			}

			i++;
		}

		fileContents += "}";

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename + ".txt"), "utf-8"));
			writer.write(fileContents);
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * Convert file to matrix.
	 *
	 * @param filename
	 *            the filename
	 * @return the square matrix
	 * @throws IllegalMatrixException
	 *             the illegal matrix exception
	 */
	private static SquareMatrix convertFileToMatrix(String filename)
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

			return null;
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
